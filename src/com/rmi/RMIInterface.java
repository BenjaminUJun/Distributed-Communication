/**
 * 
 */
package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import classes.Booker;
import classes.Hotel;
import classes.Room;
import classes.Transaction;

/**
 * @author Yu Jun 2015/5/7.
 *
 */
public interface RMIInterface extends Remote {
	public List<String> queryCity() throws RemoteException;
	
	public List<String> queryByCityName(String cityname) throws RemoteException;
	
	public String queryRoomrates(String hotelID) throws RemoteException;
	
	public List<Room> queryVacantrooms(String hotelID, String checkindate,
			String checkoutdate) throws RemoteException;
	
//	public String insertBookers(String bookerID, String bookerName, String telePhone, 
//			String email, String passwd) throws RemoteException;
	
	public String booking(String checkinDate, String checkoutDate, String hotelID, 
			String roomID, String bookerID,String creditNO, String brand) throws RemoteException;
	
	public List<Hotel> queryHotel(String cityName) throws RemoteException;
	
//	public List<Booker> queryBookerinfo(String bookerID) throws RemoteException;
	
	public Transaction queryTransaction(String transactionID) throws RemoteException;
}
