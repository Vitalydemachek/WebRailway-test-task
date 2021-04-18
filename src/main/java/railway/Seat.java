package railway;

import java.util.Objects;

/**
 * Created by pro-27 on 26.02.2018.
 */
public class Seat {
	private Long id;
	private TypeCarriage typeCrr;
	private String type;
	private Integer number;

	public Seat(Long id, TypeCarriage typeCrr, String type, Integer number) {
		this.id = id;
		this.typeCrr = typeCrr;
		this.type = type;
		this.number = number;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Seat seat = (Seat) o;
		return number == seat.number &&
				Objects.equals(id, seat.id) &&
				Objects.equals(typeCrr, seat.typeCrr) &&
				Objects.equals(type, seat.type);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id, typeCrr, type, number);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TypeCarriage getTypeCrr() {

		return typeCrr;
	}

	public void setTypeCrr(TypeCarriage typeCrr) {
		this.typeCrr = typeCrr;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public boolean isBookedBy(BookedTicket t) {
		return t.getTicket().getSeat().equals(this);
	}

}
