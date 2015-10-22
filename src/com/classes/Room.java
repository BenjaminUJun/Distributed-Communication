package classes;

import java.io.Serializable;
import java.sql.Date;



public class Room implements Serializable{
	private String roomID;
	private String checkindate;
	private String checkoutdate;
	private String hotelID;
	//private int isavail;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public String getRoomID() {
		return roomID;
	}

	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}

	public String getCheckindate() {
		return checkindate;
	}

	public void setCheckindate(String checkindate) {
		this.checkindate = checkindate;
	}

	public String getCheckoutdate() {
		return checkoutdate;
	}

	public void setCheckoutdate(String checkoutdate) {
		this.checkoutdate = checkoutdate;
	}

	public String getHotelID() {
		return hotelID;
	}

	public void setHotelID(String hotelID) {
		this.hotelID = hotelID;
	}

//	public int getIsavail() {
//		return isavail;
//	}
//
//	public void setIsavail(int isavail) {
//		this.isavail = isavail;
//	}

}
