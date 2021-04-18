package railway;

import java.time.LocalDateTime;

/**
 * Created by pro-27 on 26.02.2018.
 */
//train
public class Trip {
	private Long id;
	private String number;
	private City from;
	private City to;
	private LocalDateTime date;

	public Trip(String number, City from, City to, LocalDateTime date, Long id) {
		this.number = number;
		this.from = from;
		this.to = to;
		this.date = date;
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {

		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (number != null ? number.hashCode() : 0);
		result = 31 * result + (from != null ? from.hashCode() : 0);
		result = 31 * result + (to != null ? to.hashCode() : 0);
		result = 31 * result + (date != null ? date.hashCode() : 0);
		return result;

	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Trip trip = (Trip) o;

		if (id != null ? !id.equals(trip.id) : trip.id != null) return false;
		if (number != null ? !number.equals(trip.number) : trip.number != null) return false;
		if (from != null ? !from.equals(trip.from) : trip.from != null) return false;
		if (to != null ? !to.equals(trip.to) : trip.to != null) return false;
		return date != null ? date.equals(trip.date) : trip.date == null;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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

	public void setTo(City to) {
		this.to = to;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
}
