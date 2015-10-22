package classes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import util.Constants;

public class QueryRoomImple implements QueryRoom {
	Connection connection;
	private Logger logger;
	private String path;
	public QueryRoomImple(String path){
		 logger = Logger.getLogger(QueryHotelImple.class);
	     PropertyConfigurator.configure(path + Constants.DB_PRO_STRING);
	     this.path = path;
		 getconnection();
	}
	
	public void getconnection(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(Constants.CONNECTION_STRING,Constants.USERNAME,"");
			
		}catch(Exception e){
			logger.debug("Fail to Connect");
		}
	}

	@Override
	public List<String> queryCityList(int serverNO) {
		// TODO Auto-generated method stub
		if(connection ==null){
			logger.error("Connection is not established");
			return null;
		}else{
			List<String> string = new ArrayList<String>();
			String querystring = "select distinct citystr from hotel" + serverNO;
			try{
				PreparedStatement statement= connection.prepareStatement(querystring);
				ResultSet results =statement.executeQuery();
				while(results.next()){
					string.add(results.getString(1));
				}
				return string;
			}catch(SQLException e){
				e.printStackTrace();
				return null;
			}
		}
		
}

	@Override
	public List<String> queryByCityName(String cityname, int serverNO) {
		// TODO Auto-generated method stub
		if(connection == null){
			logger.error("Connection is not established");
			return null;
		}else{
			List<String> string = new ArrayList<String>();
			String querystring = "select distinct bandstr from hotel"+serverNO + " where citystr=?";
			try{
				PreparedStatement statement = connection.prepareStatement(querystring);
				statement.setString(1, cityname);
				ResultSet results = statement.executeQuery();
				while(results.next()){
					string.add(results.getString(1));
				}
				return string;
			}catch(SQLException e){
				e.printStackTrace();
				return null;
			}
		
		}
	}

	@Override
	public List<Room> queryByHotel(String hotelID,
			String checkindate, String checkoutdate, int serverNO) {
		int id = Integer.parseInt(hotelID);
		if(connection == null){
			logger.error("Connection is not established");
			return null;
		}else{
			List<Room> rooms = new ArrayList<Room>();
			TransactionInterImple tii = new TransactionInterImple(this.path);
//			String querystring = "select * from room" +serverNO + " where hotelID = ? and (isavail=0 or (TO_DAYS(checkinDate) >= TO_DAYS(?) or "
//					+ "TO_DAYS(checkoutDate) <= TO_DAYS(?)))"; 
			String querystring = "select * from room" +serverNO + " where hotelID = ? and ((TO_DAYS(checkinDate) >= TO_DAYS(?) or "
					+ "TO_DAYS(checkoutDate) <= TO_DAYS(?)))"; 
			
			try{
				PreparedStatement statement = connection.prepareStatement(querystring);
				statement.setInt(1, id);
				statement.setString(2, checkoutdate);
				statement.setString(3,checkindate);
				ResultSet results = statement.executeQuery();
				while(results.next()){
					Room room = new Room();
//					if(results.getInt(5)==0){
//					room.setRoomID(results.getString(1));
//					room.setCheckindate(results.getString(2));
//					room.setCheckoutdate(results.getString(3));
//					room.setHotelID(results.getString(4));
//					//room.setIsavail(0);
//					rooms.add(room);
//					}else{
						boolean bool = tii.queryTrans(hotelID, results.getString(1), checkindate, checkoutdate, serverNO);
						if(bool==true){
							continue;
						}else{
							room.setRoomID(results.getString(1));
							room.setCheckindate(results.getString(2));
							room.setCheckoutdate(results.getString(3));
							room.setHotelID(results.getString(4));
							//room.setIsavail(0);
							rooms.add(room);
						}
					//}
					
			}
				return rooms;
			}catch(SQLException e){
				e.printStackTrace();
				return null;
			}
			
			
		}
		// TODO Auto-generated method stub
	}


	@Override
	public String queryByHotel(String hotelID, int serverNO) {
		// TODO Auto-generated method stub
	     int id =Integer.parseInt(hotelID);     
	     if(connection == null){
			logger.error("Connection is not established");
			return null;
		}else{
			String string = "";
			String querystring = "select roomrate from hotel"+serverNO + " where hotelID = ? ";
			try{
				PreparedStatement statement = connection.prepareStatement(querystring);
				statement.setInt(1, id);
				ResultSet results =statement.executeQuery();
				boolean flag=results.next();
				if(flag){
				string = results.getString(1);
				}
				return string;	
			}catch(SQLException e){
				e.printStackTrace();
				return null;
			}
			
			
		}
	}
	



	@Override
	public  boolean queryByID(String hotelID, String roomID, String checkinDate, String checkoutDate, int serverNO) {
		// TODO Auto-generated method stub
		int rid = Integer.parseInt(roomID);
		int hid = Integer.parseInt(hotelID);
		if(connection == null){
			logger.error("Connection is not established");
			return false;
		}else{	
			TransactionInterImple tii = new TransactionInterImple(this.path);
			String querystring = "select roomID from room"+serverNO + " where roomID = ? and hotelID = ?";
			PreparedStatement statement;
			try{
				statement = connection.prepareStatement(querystring);
				statement.setInt(1, rid);
				statement.setInt(2, hid);
				ResultSet results = statement.executeQuery();
				boolean flag =results.next();
				if(flag){
					boolean bool = tii.queryTrans(hotelID, roomID, checkinDate, checkoutDate, serverNO);
					if(bool){
					return false;
					}else{
						return true;
					}
				}else{
					return false;
				}	
			}catch(SQLException e){
				e.printStackTrace();
				return false;
			}
		}
	}
	
	@Override
	public boolean queryHR(String hotelID, String roomID, int serverNO) {
		// TODO Auto-generated method stub
		int rid = Integer.parseInt(roomID);
		int hid = Integer.parseInt(hotelID);
		if(connection == null){
			logger.error("Connection is not established");
			return false;
		}else{	
			String querystring = "select roomID from room"+serverNO + " where roomID = ? and hotelID = ?";
			PreparedStatement statement;
			try{
				statement = connection.prepareStatement(querystring);
				statement.setInt(1, rid);
				statement.setInt(2, hid);
				ResultSet results = statement.executeQuery();
				boolean flag =results.next();
				if(flag){
					return true;
				}else{
					return false;
				}	
			}catch(SQLException e){
				e.printStackTrace();
				return false;
			}
		}
	}
	
	@Override
	public List<Room> queryByServerno(String hotelID, int serverNO) {
		// TODO Auto-generated method stub
		int hid = Integer.parseInt(hotelID);
		if(connection == null){
			logger.error("Connection is not established!");
			return null;
		}else{
			List<Room> rooms = new ArrayList<Room>();
			String querystring = "select * from room"+ serverNO +" where hotelID = ?";
			PreparedStatement statement;
			try{
				statement = connection.prepareStatement(querystring);
				statement.setInt(1, hid); 
				ResultSet results = statement.executeQuery();
				while(results.next()){
					Room room = new Room();
					room.setRoomID(results.getString(1));
					room.setCheckindate(results.getString(2));
					room.setCheckoutdate(results.getString(3));
					room.setHotelID(results.getString(4));
					//room.setIsavail(results.getInt(5));
					rooms.add(room);
				}
				return rooms;
			}catch(SQLException e){
				e.printStackTrace();
				return null;
			}
			
		}
		
	}
	public void close() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		QueryRoomImple a = new QueryRoomImple("/home/hugh/Javahwk/src2");
		List<Room> rooms = a.queryByHotel("1", "2015-01-11" , "2015-01-1", 2);
		for (Room room:rooms) {
			System.out.println(room.getRoomID());
		}
//		List<Room> rooms = new ArrayList<Room>();
//		rooms = a.queryByHotel("2", "2014-03-25", "2014-05-22", 1);
//		System.out.println(rooms.get(0).getHotelID());
//		String room = a.queryByHotel("3", 1);
//		System.out.println(room);
//		List<String> cities = a.queryCityList(1);
//		for (String city:cities){
//			System.out.println(city);
//		}
//		List<String> cities2 = a.queryByCityName("suzhou", 2);
//		for (String city:cities2){
//			System.out.println(city);
//		}
//		boolean isfalse = a.queryByID("1", "12", "2015-01-11", "2015-01-12", 1);
//		System.out.println(isfalse);
//		
//		isfalse = a.queryHR("3", "22", 1);
//		System.out.println(isfalse);
//		List<Room> rooms2 = new ArrayList<Room>();
//		rooms2 = a.queryByServerno("2", 1);
//		for (Room room1:rooms2) {
//			System.out.println(room1.getRoomID());
//		}
	}
}
