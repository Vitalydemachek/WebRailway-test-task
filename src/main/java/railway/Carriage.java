package railway;

import java.util.Objects;

/**
 * Created by pro-27 on 26.02.2018.
 */
public class Carriage {
	private Long id;
	private Trip trip;
	private TypeCarriage type;
	private int number;

	public Carriage(Trip trip, TypeCarriage type, int number, Long id) {
		this.id = id;
		this.trip = trip;
		this.type = type;
		this.number = number;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Carriage carriage = (Carriage) o;
		return number == carriage.number &&
				Objects.equals(id, carriage.id) &&
				Objects.equals(trip, carriage.trip) &&
				Objects.equals(type, carriage.type);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id, trip, type, number);
	}

	public Long getId() {

		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	public TypeCarriage getType() {
		return type;
	}

	public void setType(TypeCarriage type) {
		this.type = type;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

}
