package classes;

import java.sql.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import util.Constants;
public class TransactionInterImple implements TransactionInter {
	
	private Connection connection;
	private Logger logger;
	private String path;
	public TransactionInterImple(String path){
	    logger = Logger.getLogger(TransactionInterImple.class);
        PropertyConfigurator.configure(path + Constants.DB_PRO_STRING);
        this.path = path;
		getConnection();
	}
	
	public void getConnection(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.USERNAME,"");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Fail to Connect");
	}
}
	
public boolean insertTransaction(String transactionID, String roomID, String bookDT, String bookerID,
			String creditNO, String hotelID, String brand, int serverNO){
		int rid=Integer.parseInt(roomID);
		int hid=Integer.parseInt(hotelID);
		if(connection==null){
				System.out.println("Connection is not established");
				return false;
			}else{
				if(this.queryTransaction(transactionID, serverNO))
					return false;
				else{
				int flag = 0;
				String updateString = "insert into transaction"+serverNO + " values (?,?,?,?,?,?,?)";
				PreparedStatement statement;
				try{
					statement = connection.prepareStatement(updateString);
					statement.setString(1, transactionID);
					statement.setInt(2, rid);
					statement.setString(3, bookDT);
					statement.setString(4, bookerID);
					statement.setString(5, creditNO);
					statement.setInt(6, hid);
					statement.setString(7, brand);
					flag = statement.executeUpdate();
					if(flag > 0)
						return true;
					else
						return false;
				}catch(SQLException e){
					e.printStackTrace();
				}	
				}
			}
			return false;
	}



@Override
public boolean queryTransaction(String transactionID, int serverNO) {
	if(connection==null){
		System.out.println("Connection is not established!!");
		return false;
	}else{
		String queryString = "select * from transaction" +serverNO + " where tranID = ?";
		PreparedStatement statement;
		try{
			statement = connection.prepareStatement(queryString);
			statement.setString(1, transactionID);
			ResultSet result = statement.executeQuery();
			boolean flag = result.next();
			if(flag){
				return true;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	return false;
}


@Override
public Transaction queryTran(String transactionID, int serverNO) {
	// TODO Auto-generated method stub
	if(connection==null){
		System.out.println("Connection is not established!!");
		return null;
	}else{
		Transaction trans = new Transaction();
		String queryString = "select * from transaction" +serverNO + " where tranID = ?";
		PreparedStatement statement;
		try{
			statement = connection.prepareStatement(queryString);
			statement.setString(1, transactionID);
			ResultSet result = statement.executeQuery();
			boolean flag = result.next();
			if(flag){
				trans.setTranID(result.getString(1));
				trans.setRoomID(result.getString(2));
				trans.setBookDT(result.getString(3));
				trans.setBookerID(result.getString(4));
				trans.setCreditNO(result.getString(5));
				trans.setHotelID(result.getString(6));
				trans.setBrand(result.getString(7));
  			}
			return trans;
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
}

@Override
public boolean queryBooker(String bookerID) {
	// TODO Auto-generated method stub
	if(connection==null){
		System.out.println("Connection is not established!!");
		return false;
	}else{
		String queryString = "select * from booker where bookerID = ?";
		PreparedStatement statement;
		try{
			statement = connection.prepareStatement(queryString);
			statement.setString(1, bookerID);
			ResultSet result = statement.executeQuery();
			boolean flag = result.next();
			if(flag){
				return true;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	return false;
}

@Override
public boolean insertBooker(String bookerID, String bookerName,
		String telePhone, String email,String passwd) {
	// TODO Auto-generated method stub
	 if(connection==null){
		 System.out.println("Connection is not established!!");
		 return false;
	 }else{
		 if(this.queryBooker(bookerID)){
			 return false;
		 }else{
		 int flag=0;
		 String updateString ="insert into booker values(?, ?, ?, ?, ?)";
		 PreparedStatement statement;
		 try{
			 statement = connection.prepareStatement(updateString);
			 statement.setString(1, bookerID);
			 statement.setString(2, bookerName);
			 statement.setString(3, telePhone);
			 statement.setString(4, email);
			 statement.setString(5, passwd);
			 flag=statement.executeUpdate();
			 if(flag>0){
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
}

@Override
public String bookingRoom(String checkinDate, String checkoutDate, String hotelID, String transactionID,
		String roomID, String bookerID,String creditNO, String brand, int serverNO) {
	// TODO Auto-generated method stub
	if(connection==null){
		 System.out.println("Connection is not established!!");
		 return Constants.NOCONNECTION;
	}else{
		QueryRoomImple qri = new QueryRoomImple(this.path);
		boolean hr = qri.queryHR(hotelID, roomID, serverNO);
		if(!hr){
			return Constants.NOTHAVE;
		}
		boolean bool=qri.queryByID(hotelID,roomID, checkinDate, checkoutDate, serverNO);
		boolean ha = queryTrans(hotelID, roomID, checkinDate, checkoutDate,serverNO);
		if(ha){
			return Constants.OCCUPIEDROOM;
		}
		if(!bool){
			return Constants.NOQUALIFIEDROOM;
		}else{		
		int hid=Integer.parseInt(hotelID);
		int rid=Integer.parseInt(roomID);		
		int flag1=0;
		int flag2=0;
		String updatestring1="update hotel"+serverNO +" set vacantrooms = vacantrooms - 1 where hotelID= ? ";
		//String updatestring2="update room"+serverNO +" set checkinDate = ?, checkoutDate = ?, isavail = 1 where roomID = ? ";
		String updatestring2="update room"+serverNO +" set checkinDate = ?, checkoutDate = ? where roomID = ? ";
		PreparedStatement statement;
		try{
			statement = connection.prepareStatement(updatestring1);
			statement.setInt(1, hid);
			flag1 = statement.executeUpdate();
			statement = connection.prepareStatement(updatestring2);
			statement.setString(1, checkinDate);
			statement.setString(2, checkoutDate);
			statement.setInt(3, rid);
			flag2 = statement.executeUpdate();
			if(flag1==1&&flag2==1){
				this.insertTransaction(transactionID, roomID, new Date(System.currentTimeMillis()).toString(), bookerID, creditNO, hotelID, brand, serverNO);
				return Constants.BOOKSUCCESSFUL;
			}else{
				return Constants.BOOKFAILED;
			}
			
		}catch(SQLException e){
			e.printStackTrace();
			return "Error Occured";
		}
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

public boolean queryTrans(String hotelID, String roomID, String checkinDate, String checkoutDate,int serverNO){
	if(connection==null){
		System.out.println("Connection is not established!!");
		return false;
	}else{
		int hid = Integer.parseInt(hotelID);
		int rid = Integer.parseInt(roomID);
		String queryString = "select tranID from transaction" +serverNO + " where hotelID = ? and roomID = ?";
		PreparedStatement statement;
		try{
			String temp1 = "";
			String temp2 = "";
			statement = connection.prepareStatement(queryString);
			statement.setInt(1, hid);
			statement.setInt(2, rid); 
			ResultSet result = statement.executeQuery();
			while(result.next()){
				temp1 = result.getString(1).substring(0, 10);
				temp2 = result.getString(1).substring(10, 20);
				int i = temp1.compareTo(checkoutDate);
				int j = temp2.compareTo(checkinDate);
				if(!(i >= 0||j <= 0)){
					return true;
				}
			}
			return false;
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	}
}

public static void main(String[] args) {
	   TransactionInterImple a = new TransactionInterImple("/home/hugh/Javahwk/src2");
	   
    // boolean c = a.insertBooker("12222", "dfasf", "dfadsf", "dfadsf", "dfasdf");
//	   System.out.println(a.bookingRoom("2015-01-11", "2015-02-11", "2", "1121111111", "22", "12324", "132131", "hilton", 1));
//	   System.out.println(a.queryTran("111111111", 1).getBookDT());   
//     System.out.println(c);	

}
}
