package railway.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import railway.*;
import railway.service.BackEndController;


@Controller
public class WebController {

	@Autowired
	private BackEndController backEndController;

	@GetMapping("/greeting")
	public String greet(@RequestParam(name = "str", required = false, defaultValue = "world") String name, Model model) {

		model.addAttribute("name", "railway");

		return "greeting";

	}

	@GetMapping("/mainPage")
	public String main_() {
		return "main";
	}

	@GetMapping("/search")
	public String process(@RequestParam(name = "city_from") String cityFrom,
						  @RequestParam(name = "city_to") String cityTo,
						  @RequestParam(name = "depurt_date") String depurtDate,
						  @RequestParam(name = "order", required = false, defaultValue = "ass") String order,
						  Model model) throws ClassNotFoundException {

		HashSet<TicketWeb> AppropriateTickets;
		City cityF = new City(cityFrom);
		City cityT = new City(cityTo);

		String externalPattern = "yyyy-MM-dd";
		String internalPattern = "HH:mm:ss dd.MM.yyyy";
		DateTimeFormatter externalF = DateTimeFormatter.ofPattern(externalPattern);
		DateTimeFormatter internalF = DateTimeFormatter.ofPattern(internalPattern);
		String internalDepurtDate = LocalDate.parse(depurtDate, externalF).atStartOfDay().format(internalF);
		AppropriateTickets = backEndController.saleTickets(cityF, cityT, internalDepurtDate);
		List<TicketWeb> orderedAppropriateTickets;

		switch (order) {
			case "tn":
				orderedAppropriateTickets = AppropriateTickets.stream().sorted(TicketWeb.TripNumberComparator).collect(Collectors.toList());
				break;
			case "cn":
				orderedAppropriateTickets = AppropriateTickets.stream().sorted(TicketWeb.carrNumberComparator).collect(Collectors.toList());
				break;
			case "sn":
				orderedAppropriateTickets = AppropriateTickets.stream().sorted(TicketWeb.seatNumberComparator).collect(Collectors.toList());
				break;
			default:
				orderedAppropriateTickets = AppropriateTickets.stream().sorted(TicketWeb.TripNumberComparator.
						thenComparing(TicketWeb.carrNumberComparator).
						thenComparing(TicketWeb.seatNumberComparator)).collect(Collectors.toList());
		}

		model.addAttribute("c_from", cityFrom);
		model.addAttribute("c_to", cityTo);
		model.addAttribute("d_date", depurtDate);
		model.addAttribute("emptySeats", orderedAppropriateTickets);

		return "result";
	}
}
