package railway.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import railway.*;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by pro-27 on 16.04.2018.
 */
@Component
public class DataMapper {

	private final NamedParameterJdbcTemplate jdbcTemplate;
	private Connection cnn;
	/*@Autowired
	private DataSource ds;*/

	public DataMapper(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	/*@PostConstruct
	private void getConnection() throws SQLException {
		cnn = ds.getConnection();
	}*/


	public HashSet<City> loadCities() {

		HashSet<City> Cities = new HashSet<>();

		try {
			String query = "Select name From cities;";


			Cities.addAll(jdbcTemplate.queryForList(query, new HashMap<>(), City.class));

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());

		}

		return Cities;

	}

	public Map<Long, Trip> loadTrip(HashSet<City> ci) {

		Map<Long, Trip> trips = new HashMap<>();

		try {
			String query = "Select ID,number,city_from,city_to,departure_date From trips;";

			Function<String, City> cityLink = n -> ci.stream().filter(c -> (c.getName() == null ? n == null : c.getName().equals(n)))
					.findFirst()
					.orElse(new City(n));

			RowMapper<Trip> tripMapper =
					(rs, rowNum) -> new Trip(rs.getString("number")
							, cityLink.apply(rs.getString("city_from"))
							, cityLink.apply(rs.getString("city_from"))
							, LocalDateTime.of(rs.getDate("departure_date").toLocalDate(), rs.getTime("departure_date").toLocalTime())
							, rs.getLong("ID"));

			List<Trip> tripList = jdbcTemplate.query(query, tripMapper);
			trips.putAll(tripList.stream().collect(Collectors.toMap(Trip::getId, trip -> trip)));

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return trips;

	}

	public Map<Long, Stop> LoadStop(HashSet<City> ci, Map<Long, Trip> tr) {

		Map<Long, Stop> stops = new HashMap<>();

		try {
			String query = "Select ID,arrive_date,tripID,city From stops;";

			Function<String, City> cityLink = n -> ci.stream().filter(c -> (c.getName() == null ? n == null : c.getName().equals(n)))
					.findFirst()
					.orElse(new City(n));

			RowMapper<Stop> stopMapper =
					(rs, rowNum) -> new Stop(LocalDateTime.of(rs.getDate("arrive_date").toLocalDate()
							, rs.getTime("arrive_date").toLocalTime())
							, tr.get(rs.getLong("tripID"))
							, cityLink.apply(rs.getString("city"))
							, rs.getLong("ID"));

			List<Stop> stopList = jdbcTemplate.query(query, stopMapper);
			stops.putAll(stopList.stream().collect(Collectors.toMap(Stop::getId, stop -> stop)));


		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return stops;

	}

	public Map<Long, TypeCarriage> LoadTypeCarriage() {

		Map<Long, TypeCarriage> carriageTypes = new HashMap<>();

		try {
			String query = "Select ID,typeDescription From typeCarriage;";

			RowMapper<TypeCarriage> typeCarriageMapper =
					(rs, rowNum) -> new TypeCarriage(rs.getLong("ID")
							, rs.getString("typeDescription"));

			List<TypeCarriage> typeCarriageList = jdbcTemplate.query(query, typeCarriageMapper);
			carriageTypes.putAll(typeCarriageList.stream().collect(Collectors.toMap(TypeCarriage::getId, type -> type)));

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return carriageTypes;

	}

	public Map<Long, Seat> LoadSeats(Map<Long, TypeCarriage> tc) {

		HashMap<Long, Seat> seats = new HashMap<>();

		try {
			String query = "Select ID,typeCrr_ID,typeSeat,numberSeat From seats;";

			RowMapper<Seat> seatMapper =
					(rs, rowNum) -> new Seat(rs.getLong("ID")
							, tc.get(rs.getLong("typeCrr_ID"))
							, rs.getString("typeSeat")
							, rs.getInt("numberSeat")
					);

			List<Seat> seatList = jdbcTemplate.query(query, seatMapper);
			seats.putAll(seatList.stream().collect(Collectors.toMap(Seat::getId, type -> type)));


		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return seats;

	}

	public Map<Long, Carriage> LoadCarriage(Map<Long, Trip> tr, Map<Long, TypeCarriage> tc) throws ClassNotFoundException {

		Map<Long, Carriage> carriages = new HashMap<>();

		try {
			String query = "Select ID,tripID,typeCrr_ID,numberCarriage From carriage;";

			RowMapper<Carriage> carriageMapper =
					(rs, rowNum) -> new Carriage(tr.get(rs.getLong("tripID"))
							, tc.get(rs.getLong("typeCrr_ID"))
							, rs.getInt("numberCarriage")
							, rs.getLong("ID")
					);

			List<Carriage> carriageList = jdbcTemplate.query(query, carriageMapper);
			carriages.putAll(carriageList.stream().collect(Collectors.toMap(Carriage::getId, carriage -> carriage)));


		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());

		}

		return carriages;

	}

	public Map<Long, Ticket> LoadTicket(HashSet<City> ci, Map<Long, Trip> tr, Map<Long, Seat> se, Map<Long, Carriage> cr) throws ClassNotFoundException {

		Map<Long, Ticket> tickets = new HashMap<>();

		try {
			String query = "Select ID,tripID,city_from,city_to,departure_date,seatID,carrID,customerFirstName From ticket;";

			Function<String, City> cityLink = n -> ci.stream().filter(c -> (c.getName() == null ? n == null : c.getName().equals(n)))
					.findFirst()
					.orElse(new City(n));

			RowMapper<Ticket> ticketMapper =
					(rs, rowNum) -> new Ticket(tr.get(rs.getLong("tripID"))
							, cityLink.apply(rs.getString("city_from"))
							, cityLink.apply(rs.getString("city_from"))
							, LocalDateTime.of(rs.getDate("departure_date").toLocalDate(), rs.getTime("departure_date").toLocalTime())
							, se.get(rs.getLong("seatID"))
							, cr.get(rs.getLong("carrID"))
							, rs.getString("customerFirstName")
							, rs.getLong("ID"));

			List<Ticket> ticketList = jdbcTemplate.query(query, ticketMapper);
			tickets.putAll(ticketList.stream().collect(Collectors.toMap(Ticket::getId, trip -> trip)));


		} catch (Exception e) {

			e.printStackTrace();
			System.out.println(e.getMessage());

		}

		return tickets;

	}

	public List<BookedTicket> loadBookedTicket(Map<Long, Ticket> tck, Map<Long, Stop> st) throws ClassNotFoundException {

		List<BookedTicket> BookedTickets = new ArrayList<>();

		try {
			String query = "Select ID,ticketID,stopFromID,stopToID From bookedTicket;";

			RowMapper<BookedTicket> bookedTicketMapper =
					(rs, rowNum) -> new BookedTicket(tck.get(rs.getLong("ticketID"))
							, st.get(rs.getLong("stopFromID"))
							, st.get(rs.getLong("stopToID"))
							, rs.getLong("ID")
					);

			List<BookedTicket> bookedTicketList = jdbcTemplate.query(query, bookedTicketMapper);
			BookedTickets.addAll(bookedTicketList);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return BookedTickets;

	}
}
