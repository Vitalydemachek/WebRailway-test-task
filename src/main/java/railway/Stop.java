package railway;

import java.time.LocalDateTime;

/**
 * Created by pro-27 on 26.02.2018.
 */
public class Stop {
	private Long id;
	private LocalDateTime date;
	private Trip trip;
	private City city;

	public Stop(LocalDateTime d_, Trip t_, City c_, Long id) {
		this.date = d_;
		this.trip = t_;
		this.city = c_;
		this.id = id;
	}

	public boolean matches(City from, City to, LocalDateTime requiredDate) {
		boolean matchesByCity = this.city.equals(from) || this.city.equals(to);
		boolean matchesByDate = !date.isBefore(requiredDate);
		return matchesByCity && matchesByDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;


		Stop stop = (Stop) o;

		if (id != null ? !id.equals(stop.id) : stop.id != null) return false;
		if (date != null ? !date.equals(stop.date) : stop.date != null) return false;
		if (trip != null ? !trip.equals(stop.trip) : stop.trip != null) return false;
		return city != null ? city.equals(stop.city) : stop.city == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (date != null ? date.hashCode() : 0);
		result = 31 * result + (trip != null ? trip.hashCode() : 0);
		result = 31 * result + (city != null ? city.hashCode() : 0);
		return result;
	}


	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
}
