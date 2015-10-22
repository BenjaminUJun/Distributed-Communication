package classes;

import java.io.Serializable;

public class Booker implements Serializable {
	private String bookerID;
	private String bookerName;
	private String passwd="";
	private String telePhone;
	private String email;
	private String bookinfo1 = "NO BOOKING INFORMATION";
	private String bookinfo2 = "";
	
	public String getBookerID() {
		return bookerID;
	}

	public void setBookerID(String bookerID) {
		this.bookerID = bookerID;
	}

	public String getBookerName() {
		return bookerName;
	}

	public void setBookerName(String bookerName) {
		this.bookerName = bookerName;
	}

	public String getTelePhone() {
		return telePhone;
	}

	public void setTelePhone(String telePhone) {
		this.telePhone = telePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBookinfo1() {
		return bookinfo1;
	}

	public void setBookinfo1(String bookinfo1) {
		this.bookinfo1 = bookinfo1;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public String getBookinfo2() {
		return bookinfo2;
	}

	public void setBookinfo2(String bookinfo2) {
		this.bookinfo2 = bookinfo2;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

}
