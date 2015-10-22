package classes;

import java.sql.Date;
import java.util.List;

public interface QueryRoom {
	public List<String> queryCityList(int serverNO);
	public List<String> queryByCityName(String cityname, int serverNO);
	public String queryByHotel(String hotelID,int serverNO);
	public List<Room> queryByHotel(String hotelID, String checkinDate, 
			String checkoutDate, int serverNO);
	public boolean queryByID(String hotelID, String roomID, String checkinDate, String checkoutDate,int serverNO);
	public List<Room> queryByServerno(String hotelID, int serverNO);
	public boolean queryHR(String hotelID, String roomID, int serverNO);
}
