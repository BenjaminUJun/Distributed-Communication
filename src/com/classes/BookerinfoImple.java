package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.Constants;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class BookerinfoImple implements Bookerinfo {
	
	Connection connection;
	private Logger logger;
	
	public BookerinfoImple(String path){
        logger = Logger.getLogger(BookerinfoImple.class);
        PropertyConfigurator.configure(path + Constants.DB_PRO_STRING);
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
	public List<Booker> queryBooker(String bookerID, int serverNO) {
		// TODO Auto-generated method stub

		if(connection == null){
			logger.error("Connection is not established!");
			return null;
		}else{
			List<Booker> bookers = new ArrayList<Booker>();
			Booker booker = new Booker();
			Booker temp = new Booker();
			Transaction trans = new Transaction();
			String querybk = "select * from booker where bookerID = ?";
			String querytrans = "select * from transaction"+serverNO+" where bookerID = ?";
			PreparedStatement statement1;
			PreparedStatement statement2;
			try{
				statement1 = connection.prepareStatement(querybk);
				statement1.setString(1, bookerID);
				ResultSet results = statement1.executeQuery();
				boolean flag = results.next();
				if(flag){
					int count=0;
					temp.setBookerID(results.getString(1));
					temp.setBookerName(results.getString(2));
					temp.setTelePhone(results.getString(3));
					temp.setEmail(results.getString(4));
					temp.setPasswd(results.getString(5));
					statement2 = connection.prepareStatement(querytrans);
					statement2.setString(1, bookerID);
					results = statement2.executeQuery();
					while(results.next()){
						booker = new Booker();
						booker.setBookerID(temp.getBookerID());
						booker.setBookerName(temp.getBookerName());
						booker.setTelePhone(temp.getTelePhone());
						booker.setEmail(temp.getEmail());
						booker.setPasswd(temp.getPasswd());
						booker.setBookinfo1("Brand: "+results.getString(7)+"\tHotel NO.: "+results.getString(6)+"\tRoom NO.: "+results.getString(2)+
								"\tBook Date: "+ results.getString(3));
						booker.setBookinfo2("Check-in Date: "+results.getString(1).substring(0,10)+"\tCheck-out Date: "+
								results.getString(1).substring(10, 20)+"\tCredit Card Number: "+ "************"+results.getString(5).substring(12,16));
						
						bookers.add(booker);
						count++;
					}
					if(count==0){
						bookers.add(temp);
					}
				}
				return bookers;
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
	//	BookerinfoImple aa = new BookerinfoImple(args[0]);
	//	List<Booker> bookers = new ArrayList<Booker>();
	//		bookers= aa.queryBooker("26346702", 1);
	//		for(Booker booker:bookers){
	//		 System.out.println(booker.getBookinfo1());
	//		}
	//		
	//System.out.println(aa.queryBooker("26346702", 1).get(0).getBookerID());
		
	}

}
