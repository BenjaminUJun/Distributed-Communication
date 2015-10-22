/**
 * 
 */
package rmi;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import classes.Booker;
import classes.BookerinfoImple;
import classes.Hotel;
import classes.QueryHotelImple;
import classes.QueryRoomImple;
import classes.Room;
import classes.Transaction;
import classes.TransactionInterImple;
import util.Constants;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * @author Yu Jun 2015/5/7.
 *
 */

public class RMIInterfaceImple implements RMIInterface {
	
	private BookerinfoImple bookerinfo;
	private QueryHotelImple queryhotelinfo;
	private QueryRoomImple queryroominfo;
	private TransactionInterImple transactioninfo;
	private Logger logger;

	public RMIInterfaceImple(String path) {
		// TODO Auto-generated constructor stub
		bookerinfo = new BookerinfoImple(path);
		queryhotelinfo = new QueryHotelImple(path);
		queryroominfo = new QueryRoomImple(path);
		transactioninfo = new TransactionInterImple(path);
		
		logger = Logger.getLogger(RMIInterfaceImple.class);
        PropertyConfigurator.configure(path + Constants.RMISER_PRO_STRING);
	}
	
	@Override
	public List<String> queryCity() throws RemoteException {
		// TODO Auto-generated method stub
        logger.debug("RMIInterfaceImple.queryCity() is running");
        logger.debug("Request: queryCity");
        logger.debug("result is: "); 
        List<String> cities = queryroominfo.queryCityList(1);
        for (int i = 0; i < cities.size(); i++) {
            logger.debug(cities.get(i));
        }
		return cities;
	}


	@Override
	public List<String> queryByCityName(String cityname) throws RemoteException {
		// TODO Auto-generated method stub
        logger.debug("RMIInterfaceImple.queryByCityName() is running");
        logger.debug("Request: queryByCityName");
        logger.debug("City Name:" + cityname);
        List<String> cities = queryroominfo.queryByCityName(cityname, 1);
        for (int i = 0; i < cities.size(); i++) {
            logger.debug(cities.get(i));
        }
		return cities;
	}


	@Override
	public String queryRoomrates(String hotelID)
			throws RemoteException {
		// TODO Auto-generated method stub
        logger.debug("RMIInterfaceImple.queryRoomrates() is running");
        logger.debug("Request: queryRoomrates");
        logger.debug("Hotel Number:" + hotelID); 
        logger.debug("Roomrate is: ");
        String roomrate = queryroominfo.queryByHotel(hotelID, 1);
        logger.debug(roomrate);
		return roomrate;
	}


	@Override
	public List<Room> queryVacantrooms(String hotelID, String checkindate,
			String checkoutdate) throws RemoteException {
		// TODO Auto-generated method stub
		 logger.debug("RMIInterfaceImple.queryVacantrooms() is running");
	     logger.debug("Request: queryVacantrooms");
	     logger.debug("Hotel Number: " + hotelID);
	     logger.debug("Check-in Date: " + checkindate);
	     logger.debug("Check-out Date: " + checkoutdate);
	     List<Room> rooms = queryroominfo.queryByHotel(hotelID, checkindate, checkoutdate, 1);
	     if (rooms.size() == 0) {
	         logger.debug(" this hotel have no vacanct rooms as you required!");
	         return null;
	     }
	     logger.debug("The number of vacant rooms is: " + rooms.size()); 
	     for (int i = 0; i < rooms.size(); i++) {
	        logger.debug("Room number: " + rooms.get(i).getRoomID());
	     }
		 return rooms;
	}


//	@Override
//	public String insertBookers(String bookerID, String bookerName,
//			String telePhone, String email, String passwd)
//			throws RemoteException {
//		// TODO Auto-generated method stub
//		 logger.debug("RMIInterfaceImple.insertBookers is running");
//	     logger.debug("Request: queryVacantrooms");
//	     logger.debug("Booker Number: " + bookerID);
//	     logger.debug("Booker Name: " + bookerName);
//	     logger.debug("Telphone NO.: " + telePhone);
//	     logger.debug("E-mail: " + email);
//	     logger.debug("Password: " + passwd);
//	     boolean bk = transactioninfo.insertBooker(bookerID, bookerName, telePhone, email, passwd);
//	     if (bk == false) {
//	         logger.debug(" Insert booker successfully!");
//	         return Constants.BOOKFAILED;
//	     }else {
//	    	 logger.debug(" Insert booker failed!");
//	    	 return Constants.BOOKERINSERT;
//	     }
//	
//	}


	@Override
	public synchronized String booking(String checkinDate, String checkoutDate,
			String hotelID, String roomID, String bookerID, String creditNO,
			String brand) throws RemoteException {
		// TODO Auto-generated method stub
		 logger.debug("RMIInterfaceImple.booking is running");
	     logger.debug("Request: queryVacantrooms");
	     logger.debug("Check-in Date: " + checkinDate);
	     logger.debug("Check-out Date: " + checkoutDate);
	     logger.debug("Hotel Number: " + hotelID);
	     logger.debug("Room Number: " + roomID);
	     logger.debug("Booker Number: " + bookerID);
	     logger.debug("Credit Number: "+ creditNO);
	     logger.debug("Brand: "+ brand); 
		 int result=checkinDate.compareTo(checkoutDate);
		 if(result>0){
			logger.debug("The date is illegal!");
			return null;
		}else{
			String transacID = checkinDate + checkoutDate + hotelID + roomID + bookerID;
			boolean exit = transactioninfo.queryTransaction(transacID, 1);
			if(!exit){
				logger.debug("You are ready to book the room now!");
				String str = transactioninfo.bookingRoom(checkinDate, checkoutDate, hotelID, transacID, roomID, bookerID, creditNO, brand,1);
				logger.debug(str);
				return str;
			}else{
				logger.debug("You have already booked this room!");
				return Constants.DUPLICATEBOOKING;
			}	
		}
	}


	@Override
	public List<Hotel> queryHotel(String cityName)
			throws RemoteException {
		// TODO Auto-generated method stub
		logger.debug("RMIInterfaceImple.queryHotel is running");
	    logger.debug("Request: queryHotel");
	    logger.debug("City Name: " + cityName);
		List<Hotel> hotels = new ArrayList<Hotel>(); 
		hotels = queryhotelinfo.queryHotel(cityName, 1);
		if(hotels.size()==0){
			logger.debug("This city doesn't have the hotels you need!");
		}
		
	    logger.debug("The number of vacant rooms is: " + hotels.size());
		for(int i = 0;i < hotels.size();++i){
			 logger.debug("Hotel number: " + hotels.get(i).getHotelID());
		}
		return hotels;
	}


//	@Override
//	public List<Booker> queryBookerinfo(String bookerID)
//			throws RemoteException {
//		// TODO Auto-generated method stub
//		logger.debug("RMIInterfaceImple.queryBookerinfo is running");
//	    logger.debug("Request: queryBookerinfo");
//	    logger.debug("Booker Number: " + bookerID);	
//		List<Booker> bookers = new ArrayList<Booker>(); 
//		bookers = bookerinfo.queryBooker(bookerID, 1);
//		if(bookers.size()==0){
//			logger.debug("NO Booker information!");
//		}
//	    logger.debug("Booker Information: " + bookers.size());
//		for(int i = 0;i < bookers.size();++i){
//			 logger.debug("Hotel number: " + bookers.get(i).getBookerID());
//		}
//		return bookers;
//	}


	@Override
	public Transaction queryTransaction(String transactionID)
			throws RemoteException {
		// TODO Auto-generated method stub
		
		logger.debug("RMIInterfaceImple.queryTransaction is running");
	    logger.debug("Request: queryTransactioninfo");
	    logger.debug("Transaction Number: " + transactionID);			
	    Transaction trans = new Transaction();
		trans = transactioninfo.queryTran(transactionID, 1);
		if(trans==null){
			logger.debug("NO transaction information!");
		}
		logger.debug("Transaction:" + trans);
		return trans;
	}

}
