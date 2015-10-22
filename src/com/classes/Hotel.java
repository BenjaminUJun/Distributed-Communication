package classes;

import java.io.Serializable;
import java.util.List;

public class Hotel implements Serializable {
	private String hotelID;
	private String citystr;
	private String location;
	private String bandstr;
	private String vacantrooms;
	private String roomrate;
	private String roomNO;
	private List<Room> room;
	
	public String getRoomrate() {
		return roomrate;
	}
	public void setRoomrate(String roomrate) {
		this.roomrate = roomrate;
	}
	
	public String getHotelID() {
		return hotelID;
	}
	public void setHotelID(String hotelID) {
		this.hotelID = hotelID;
	}
	public String getCitystr() {
		return citystr;
	}
	public void setCitystr(String citystr) {
		this.citystr = citystr;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getBandstr() {
		return bandstr;
	}
	public void setBandstr(String bandstr) {
		this.bandstr = bandstr;
	}
	public String getVacantrooms() {
		return vacantrooms;
	}
	public void setVacantrooms(String vacantrooms) {
		this.vacantrooms = vacantrooms;
	}
	public String getRoomNO() {
		return roomNO;
	}
	public void setRoomNO(String roomNO) {
		this.roomNO = roomNO;
	}
	public List<Room> getRoom() {
		return room;
	}
	public void setRoom(List<Room> room) {
		this.room = room;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
