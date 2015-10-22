/**
 * 
 */
package broker;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import classes.Booker;
import classes.Hotel;
import classes.Room;
import classes.Transaction;
import rmi.RMIInterface;
import rmi.RMITLSClientSocketFactory;
import util.Constants;
/**
 * @author Yu Jun 2015/5/7.
 *
 */
public class BrokerRMIClient {
		private Registry registry;
		private RMIInterface stub;
		private Logger logger;
		
		public BrokerRMIClient(String path) {
			try {
				logger = Logger.getLogger(BrokerRMIClient.class);
				PropertyConfigurator.configure(path + Constants.BROKER_PRO_STRING);
				
				registry = LocateRegistry.getRegistry("localhost", 1099, 
						new RMITLSClientSocketFactory(path + Constants.SERVER_KEYSTORE_STRING, path + Constants.CLIENT_KEYSTORE_STRING, Constants.KEYSTOREPASSWORD));
				
				stub = (RMIInterface) registry.lookup(Constants.RMISERVER_STRING);
				logger.debug("get the stub");				
			} catch(RemoteException e) {
				logger.debug("The RMIServer is not start"); 
			} catch(NotBoundException e) {
				e.printStackTrace();
			}
		}
		public List<String> queryCity() throws RemoteException{
			logger.debug("queryCity1 is invoked");
			if (stub == null) {
				return null;
			}
			return stub.queryCity();
		}
		
		public List<String> queryByCityName(String cityname) throws RemoteException {
			logger.debug("queryByCiytName1 is invoked");
			if (stub == null) {
				return null;
			}
			return stub.queryByCityName(cityname);
		}
		
		public String queryRoomrates(String hotelID) throws RemoteException{
			logger.debug("queryRoomrates1 is invoked");
			if (stub == null) {
				return null;
			}
			return stub.queryRoomrates(hotelID);
		}
		
		public List<Room> queryVacantrooms(String hotelID, String checkindate,
				String checkoutdate) throws RemoteException {
			logger.debug("queryVacantrooms1 is invoked");
			if (stub == null) {
				return null;
			}
			return stub.queryVacantrooms(hotelID, checkindate, checkoutdate);
			
		}
		
//		public String insertBookers(String bookerID, String bookerName, String telePhone, 
//				String email, String passwd) throws RemoteException {
//			logger.debug("insertBookers1 is invoked");
//			if (stub == null) {
//				return null;
//			}
//			return stub.insertBookers(bookerID, bookerName, telePhone, email, passwd);
//		}
		
		public String booking(String checkinDate, String checkoutDate, String hotelID, 
				String roomID, String bookerID,String creditNO, String brand) throws RemoteException {
			logger.debug("booking1 is invoked");
			if (stub == null) {
				return null;
			}
			return stub.booking(checkinDate, checkoutDate, hotelID, roomID, bookerID, creditNO, brand);
		}
		
		public List<Hotel> queryHotel(String cityName) throws RemoteException {
			logger.debug("queryHotel1 is invoked");
			if (stub == null) {
				return null;
			}
			return stub.queryHotel(cityName);
		}
		
//		public List<Booker> queryBookerinfo(String bookerID) throws RemoteException {
//			logger.debug("queryBookerinfo1 is invoked");
//			if (stub == null) {
//				return null;
//			}
//			return stub.queryBookerinfo(bookerID);
//		}
		
		public Transaction queryTransaction(String transactionID) throws RemoteException {
			logger.debug("queryTransaction1 is invoked");
			if (stub == null) {
				return null;
			}
			return stub.queryTransaction(transactionID);
		}
}
