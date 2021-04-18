package railway;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Created by pro-27 on 26.02.2018.
 */
public class Ticket {
	private Long id;
	private Trip trip;
	private City from;
	private City to;
	private LocalDateTime date;
	private Seat seat;
	private Carriage carriage;
	private String firstName;

	public Ticket(Trip tr_, City f_, City t_, LocalDateTime d_, Seat s_, Carriage c_, String fn_,Long id) {
		this.id = id;
		this.trip = tr_;
		this.from = f_;
		this.to = t_;
		this.date = d_;
		this.seat = s_;
		this.carriage = c_;
		this.firstName = fn_;

	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Ticket ticket = (Ticket) o;
		return Objects.equals(id, ticket.id) &&
				Objects.equals(trip, ticket.trip) &&
				Objects.equals(from, ticket.from) &&
				Objects.equals(to, ticket.to) &&
				Objects.equals(date, ticket.date) &&
				Objects.equals(seat, ticket.seat) &&
				Objects.equals(carriage, ticket.carriage) &&
				Objects.equals(firstName, ticket.firstName);
	}

	@Override
	public int hashCode() {


		return Objects.hash(id, trip, from, to, date, seat, carriage, firstName);
	}

	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	public City getFrom() {
		return from;
	}

	public void setFrom(City from) {
		this.from = from;
	}

	public City getTo() {
		return to;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public Seat getSeat() {
		return seat;
	}

	public Carriage getCarriage() {
		return carriage;
	}

	public String getFirstName() {
		return firstName;
	}

}
