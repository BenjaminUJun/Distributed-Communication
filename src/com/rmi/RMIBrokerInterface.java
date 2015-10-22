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
 * @author Yu Jun 2015/5/6.
 *
 */
public interface RMIBrokerInterface extends Remote {
	public List<String> queryCity(int serverNO) throws RemoteException;
	
	public List<String> queryByCityName(String cityname, int serverNO) throws RemoteException;
	
	public String queryRoomrates(String hotelID, int serverNO) throws RemoteException;
	
	public List<Room> queryVacantrooms(String hotelID, String checkindate, 
			String checkoutdate, int serverNO) throws RemoteException;
	
	public boolean insertBookers(String bookerID, String bookerName, String telePhone,
			String email, String passwd) throws RemoteException;
	
	public String booking(String checkinDate, String checkoutDate, String hotelID, 
			String roomID, String bookerID,String creditNO, String brand, int serverNO) throws RemoteException;
	
	public List<Hotel> queryHotel(String cityName, int serverNO) throws RemoteException;
	
	public List<Booker> queryBookerinfo(String bookerID) throws RemoteException;
	
	public Transaction queryTransaction(String transactionID, int serverNO) throws RemoteException;
}
