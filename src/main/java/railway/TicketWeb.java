package railway;

import java.util.Comparator;

/**
 * @author user
 */
public class TicketWeb {
	public static Comparator<TicketWeb> TripNumberComparator = new Comparator<TicketWeb>() {

		@Override
		public int compare(TicketWeb t1, TicketWeb t2) {
			return (Integer.parseInt(t1.tripeNumber.substring(t1.tripeNumber.lastIndexOf('-') + 1)) -
					Integer.parseInt(t2.tripeNumber.substring(t2.tripeNumber.lastIndexOf('-') + 1)));
		}
	};
	public static Comparator<TicketWeb> carrNumberComparator = new Comparator<TicketWeb>() {

		@Override
		public int compare(TicketWeb t1, TicketWeb t2) {
			return (Integer.parseInt(t1.carrNumber) -
					Integer.parseInt(t2.carrNumber));
		}
	};
	public static Comparator<TicketWeb> seatNumberComparator = new Comparator<TicketWeb>() {

		@Override
		public int compare(TicketWeb t1, TicketWeb t2) {
			return (Integer.parseInt(t1.seatNumber) -
					Integer.parseInt(t2.seatNumber));
		}
	};
	final private String tripeNumber;
	final private String carrNumber;
	final private String seatType;
	final private String seatNumber;
	final private String depDate;

	public TicketWeb(String trNum, String carrNam, String sType, String sNum, String dDate) {

		this.tripeNumber = trNum;
		this.carrNumber = carrNam;
		this.seatType = sType;
		this.seatNumber = sNum;
		this.depDate = dDate;

	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TicketWeb ticketWeb = (TicketWeb) o;

		if (tripeNumber != null ? !tripeNumber.equals(ticketWeb.tripeNumber) : ticketWeb.tripeNumber != null)
			return false;
		if (carrNumber != null ? !carrNumber.equals(ticketWeb.carrNumber) : ticketWeb.carrNumber != null) return false;
		if (seatType != null ? !seatType.equals(ticketWeb.seatType) : ticketWeb.seatType != null) return false;
		if (seatNumber != null ? !seatNumber.equals(ticketWeb.seatNumber) : ticketWeb.seatNumber != null) return false;
		return depDate != null ? depDate.equals(ticketWeb.depDate) : ticketWeb.depDate == null;
	}

	@Override
	public int hashCode() {
		int result = tripeNumber != null ? tripeNumber.hashCode() : 0;
		result = 13 * result + (carrNumber != null ? carrNumber.hashCode() : 0);
		result = 13 * result + (seatType != null ? seatType.hashCode() : 0);
		result = 13 * result + (seatNumber != null ? seatNumber.hashCode() : 0);
		result = 13 * result + (depDate != null ? depDate.hashCode() : 0);
		return result;
	}


	public String getTripeNumber() {
		return tripeNumber;
	}

	public String getCarrNumber() {
		return carrNumber;

	}

	public String getSeatType() {
		return seatType;

	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public String getDepDate() {
		return depDate;
	}


}
