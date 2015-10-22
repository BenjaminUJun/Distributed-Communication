package classes;

import java.io.Serializable;
import java.sql.Date;

public class Transaction implements Serializable{

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	private String tranID;
	private String roomID;
	private String bookDT;
	private String bookerID;
	private String creditNO;
	private String hotelID;
	private String brand;
	public String getTranID() {
		return tranID;
	}
	public void setTranID(String tranID) {
		this.tranID = tranID;
	}
	public String getRoomID() {
		return roomID;
	}
	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}

	public String getCreditNO() {
		return creditNO;
	}
	public void setCreditNO(String creditNO) {
		this.creditNO = creditNO;
	}
	public String getHotelID() {
		return hotelID;
	}
	public void setHotelID(String hotelID) {
		this.hotelID = hotelID;
	}
	public String getBookDT() {
		return bookDT;
	}
	public void setBookDT(String bookDT) {
		this.bookDT = bookDT;
	}
	public String getBookerID() {
		return bookerID;
	}
	public void setBookerID(String bookerID) {
		this.bookerID = bookerID;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	


}
