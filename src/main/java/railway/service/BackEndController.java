package railway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import railway.*;
import railway.repository.DataMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by pro-27 on 26.02.2018.
 */
@Service
public class BackEndController {

	@Autowired
	private DataMapper onesMapper;

	//Pay attention, this is one of important approaches of project
	public HashSet<TicketWeb> saleTickets(City from, City to, String date) throws ClassNotFoundException {

		HashSet<TicketWeb> ticketsWeb = new HashSet<>();
		Set<Stop> setStops;
		Set<Carriage> setCarriage;
		Set<Seat> setSeat;
		List<BookedTicket> bookedTickets;

		//get data of sql tables
		try {
			HashSet<City> ci = onesMapper.loadCities();
			Map<Long, Trip> tr = onesMapper.loadTrip(ci);
			Map<Long, Stop> st = onesMapper.LoadStop(ci, tr);
			Map<Long, TypeCarriage> tc = onesMapper.LoadTypeCarriage();
			Map<Long, Seat> se = onesMapper.LoadSeats(tc);
			Map<Long, Carriage> cr = onesMapper.LoadCarriage(tr, tc);
			Map<Long, Ticket> tck = onesMapper.LoadTicket(ci, tr, se, cr);
			List<BookedTicket> btck = onesMapper.loadBookedTicket(tck, st);

			setStops = new HashSet<>(st.values());
			setCarriage= new HashSet<>(cr.values());
			bookedTickets = new ArrayList<>(btck);
			setSeat = new HashSet<>(se.values());

		} catch (Exception e) {

			e.printStackTrace();
			return ticketsWeb;

		}

		String pattern = "HH:mm:ss dd.MM.yyyy";
		DateTimeFormatter f = DateTimeFormatter.ofPattern(pattern);
		LocalDateTime requiredDate = LocalDateTime.parse(date, f);
		ComparatorStops OrderStopsByDate = new ComparatorStops();
		//HashSet<Ticket> tickets = new HashSet();

		//receive stops according to data request and turn list stops into map where key is trip and object is list stops
		Map<Trip, List<Stop>> stopsByTrip = setStops
				.stream()
				.filter(s -> s.matches(from, to, requiredDate))
				.collect((Collectors.groupingBy(Stop::getTrip)));

		//determine empty seat for each trip
		stopsByTrip.forEach((trip, stopList) -> {

			//check that there are both required stops on the trip(departure stop and arriving stop)
			if (stopList.size() > 1) {

				//order stops by date and determine what stop is (from or to)
				stopList.sort(OrderStopsByDate);
				Stop stopFrom = stopList.get(0);
				Stop stopTo = stopList.get(1);

				//find out carriages related with current trip
				List<Carriage> linkedCarr = setCarriage
						.stream()
						.filter(crr -> crr.getTrip().equals(trip))
						.collect(Collectors.toList());

				//define what tickets of current trip have been booked yet
				List<BookedTicket> linkedBookedTickets = bookedTickets.stream()
						.filter(ticket -> ticket.getTicket().getTrip().equals(trip))
						.collect(Collectors.toList());

				//define empty seats for each carriage of current trip
				linkedCarr.forEach(crr -> {

							//get set of seats related with current carriage
							////change structure classes TypeCarriage,Seat
							////List<Seat> carriageConsistOfSeats = crr.type.setSeats;
							List<Seat> carriageConsistOfSeats = setSeat.stream().filter(seat
									-> seat.getTypeCrr().equals(crr.getType())).collect(Collectors.toList());
							//change structure classes TypeCarriage,Seat

							//cut out booked seats from available seats of current carriage
							List<Seat> linkedEmptySeats = carriageConsistOfSeats.stream().filter(seat
									-> linkedBookedTickets.stream()
									.noneMatch(ticket
											//check of current carriage match the booked ticket's carriage
											-> ticket.assignedTo(crr)
											//check of seat of current carriage mentioned in booked ticket
											&& seat.isBookedBy(ticket)
											//check of intersection of required date's interval ("stopFrom' and "stopTo")
											// and date's interval already booked for current trip
											&& ticket.getResultCheckIntersectionStops(stopFrom, stopTo))).collect(Collectors.toList());

							linkedEmptySeats.forEach(emptySeat -> {

								TicketWeb tw = new TicketWeb(trip.getNumber(), String.valueOf(crr.getNumber()), emptySeat.getType(), String.valueOf(emptySeat.getNumber()), stopFrom.getDate().toString());

								ticketsWeb.add(tw);

							});

						}
				);

			}

		});

		return ticketsWeb;

	}

}
