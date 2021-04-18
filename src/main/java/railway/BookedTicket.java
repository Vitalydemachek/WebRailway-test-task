/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package railway;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author user
 */
public class BookedTicket {

	private Long id;
	private Ticket t;
	private Stop from;
	private Stop to;

	public BookedTicket(Ticket t, Stop from, Stop to, Long id) {
		this.id = id;
		this.t = t;
		this.from = from;
		this.to = to;
	}

	public Ticket getTicket() {
		return t;
	}

	public Stop getFrom() {
		return from;
	}

	public Stop getTo() {
		return to;
	}

	public boolean assignedTo(Carriage carriage) {
		return t.getCarriage().equals(carriage);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BookedTicket that = (BookedTicket) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(t, that.t) &&
				Objects.equals(from, that.from) &&
				Objects.equals(to, that.to);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id, t, from, to);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private boolean intersects(Stop from, Stop to) {
		LocalDateTime topA = this.from.getDate();
		LocalDateTime topB = this.to.getDate();
		LocalDateTime bottomA = from.getDate();
		LocalDateTime bottomB = to.getDate();

		return topB.isAfter(bottomA) && bottomB.isAfter(topA);
	}

	public boolean getResultCheckIntersectionStops(Stop from, Stop to) {
		return this.intersects(from, to);

	}

}
