package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;




public class Database {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Database base = new Database();
//		base.insertFunction(1);
//		base.insertFunction(2);
//		base.deleteFunction(1);
//		base.deleteFunction(2);
//		
	}
	
	Connection connection = null;
	private static final String[] CITY = { "NanJing", "Ningbo", "Suzhou", "Hangzhou", "Melbourne", "Sydney", "Brisbane", "Adelaide" };
	private static final String[] LOCATION = { "Jiangsu", "Zhejiang", "Jiangsu", "Zhejiang", "Victoria", "NSW", "Queensland", "SA" };
	private static final String[] HOTEL = { "HILTON", "Chevron", "Regent"};
	
	public Database(){
		getconnection();
	}
	
	public void getconnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(
                  "jdbc:mysql://localhost/id26346702", "fit5183a2", "");
		} catch (Exception e) {
			System.out.println("Fail to Connect");
		}
	}
	
	public boolean createDatabase(){
		if(connection == null){
			System.out.println("Connection is not established!");
			return false;
		}else{
			String createDatabase = "CREATE DATABASE id26346702";
			try{
				Statement stat = connection.createStatement();
				stat.executeUpdate(createDatabase);
				return true;
			}catch(SQLException e){
				e.printStackTrace();
				return false;
			}
		}
		
	}
	
	public boolean insertFunction(int k){
		if(connection == null){
			System.out.println("Connection is not established!");
			return false;
		}else{
				String insertstr1 = "INSERT INTO hotel"+k+" values(?,?,?,?,10,?,10)";
				String insertstr2 = "INSERT INTO room"+k+" values(?,?,?,?)";
				PreparedStatement statement1;
				PreparedStatement statement2;
				try{
					statement1 = connection.prepareStatement(insertstr1);
					statement2 = connection.prepareStatement(insertstr2);
					
					for(int i = 1; i <= 30; ++i){
						statement1.setInt(1, i);
						statement1.setString(2, CITY[i%8]);
						statement1.setString(3, LOCATION[i%8]);
						if(i%3 == 1){
							statement1.setString(4, "hilton");
						}else if (i%3 == 2)
						{
							statement1.setString(4, "chevron");
						}else if (i%3 == 0)
						{
							statement1.setString(4, "regent");
						}
						statement1.setDouble(5, 105.06+i);
						statement1.executeUpdate();
						for(int j =0;j<10;++j){
							statement2.setInt(1, (i)*10+j);
							statement2.setString(2, "2015-05-01");
							statement2.setString(3, "2015-05-01");
							statement2.setInt(4, i);
							statement2.executeUpdate();
						}
					}
					return true;
				}catch(SQLException e){
					//e.printStackTrace();
				}
			
			}
		return false;
		
	}
	public boolean deleteFunction(int num){
		if(connection == null){
			System.out.println("Connection is not established!");
			return false;
		}else{
			String delstr1 = "TRUNCATE TABLE transaction"+num;
			String delstr2 = "TRUNCATE TABLE booker";
			String delstr3 = "delete from room"+num+" where hotelID = ?";
			String delstr4 = "delete from hotel"+num+" where hotelID = ?";
			try{
			PreparedStatement statement1 = connection.prepareStatement(delstr1);
			statement1.executeUpdate();
			PreparedStatement statement2 = connection.prepareStatement(delstr2);
			statement2.executeUpdate();
			PreparedStatement statement3 = connection.prepareStatement(delstr3);
			PreparedStatement statement4 = connection.prepareStatement(delstr4);
			for(int i=0;i < 8; ++i){
				statement3.setInt(1, i+1);
				statement3.executeUpdate();
				statement4.setInt(1, i+1);
				statement4.executeUpdate();
			}
			return true;
			}catch(SQLException e){
				e.printStackTrace();
				return false;
			}			
		}
		
	}
}
