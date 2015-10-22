package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


import util.Constants;

public class QueryHotelImple implements QueryHotel {
	
	Connection connection;
	private Logger logger;
	private String path;
	
	public QueryHotelImple(String path){
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
	public List<Hotel> queryHotel(String cityName, int serverNO) {
		// TODO Auto-generated method stub
		if(connection == null){
			logger.error("Connection is not established!");	
			return null;
		}else{
			List<Hotel> hotels = new ArrayList<Hotel>();
			List<Room> rooms = new ArrayList<Room>();
			String querystring = "select * from hotel"+serverNO+" where citystr = ?"; 
			try{
				QueryRoomImple qri = new QueryRoomImple(this.path);
				PreparedStatement statement = connection.prepareStatement(querystring);
				statement.setString(1, cityName);
				ResultSet results = statement.executeQuery();
				while(results.next()){
					Hotel temp = new Hotel();
					temp.setHotelID(results.getString(1));
					temp.setCitystr(results.getString(2));
					temp.setLocation(results.getString(3));
					temp.setBandstr(results.getString(4));
					temp.setVacantrooms(results.getString(5));
					temp.setRoomrate(results.getString(6));
					temp.setRoomNO(results.getString(7));
					rooms = new ArrayList<Room>();
					rooms = qri.queryByServerno(results.getString(1), serverNO);
					temp.setRoom(rooms);
					hotels.add(temp);		
				}
				return hotels;
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
//		QueryHotelImple qhi = new QueryHotelImple (args[0]);
//		List<Hotel> hotels = new ArrayList<Hotel> ();
//		hotels = qhi.queryHotel("hangzhou", 1);
//		for (Hotel hotel:hotels) {
//			System.out.println(hotel.getLocation());
//		}
	}

}
