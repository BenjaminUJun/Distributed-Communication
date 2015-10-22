
package client;

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
import rmi.RMIBrokerInterface;
import rmi.RMITLSClientSocketFactory;
import util.Constants;

/**
 * @author Yu Jun 2015/5/15.
 *
 */
public class RMIClient {

	private Registry registry;
	private RMIBrokerInterface stub;
	private Logger logger;	
	public boolean connectionValue = true;
	
	public RMIClient(String path) {
		try {
			logger = Logger.getLogger(RMIClient.class);
			PropertyConfigurator.configure(path + Constants.CLIENT_PRO_STRING);
			registry = LocateRegistry.getRegistry("localhost", 1100, new RMITLSClientSocketFactory(path + Constants.SERVER_KEYSTORE_STRING, path + Constants.CLIENT_KEYSTORE_STRING, Constants.KEYSTOREPASSWORD));
			stub = (RMIBrokerInterface) registry.lookup(Constants.RMIBROKER_STRING);
			
			//logger.debug("get the stub from BrokerRMIServer");
			
		} catch (RemoteException e){
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug("Connection missing!");
			connectionValue = false;
		}
	}
	
	/*----------------------------------------------------------------------*/
	
	public List<String> queryCity(int serverNO) {
		//logger.debug("request queryCity on server " + serverNO);
		try {
			return stub.queryCity(serverNO);
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}
	public List<String> queryByCityName(String cityname, int serverNO) {
		//logger.debug("request queryByCityName on server " + serverNO);
		try {
			return stub.queryByCityName(cityname, serverNO);
		} catch (RemoteException e){
			e.printStackTrace();
			return null;
		}
	} 
	public String queryRoomrates(String hotelID, int serverNO) {
		//logger.debug("request queryRoomrates on server " + serverNO);
		try {
			return stub.queryRoomrates(hotelID,serverNO);
		} catch (RemoteException e){
			e.printStackTrace();
			return Constants.NOROOMRATEINFO;
		}
	}
	public List<Room> queryVacantrooms(String hotelID, String checkindate, 
			String checkoutdate, int serverNO) {
		//logger.debug("request queryVacantrooms on server " + serverNO);
		try {
			return stub.queryVacantrooms(hotelID, checkindate, checkoutdate, serverNO);
		} catch (RemoteException e){
			//e.printStackTrace();
			return null;
		}
	}
	public String booking(String checkinDate, String checkoutDate, String hotelID, 
			String roomID, String bookerID,String creditNO, String brand, int serverNO) {
		
		//logger.debug("request booking on server " + serverNO);
		try {
			return stub.booking(checkinDate, checkoutDate, hotelID, roomID, bookerID, creditNO, brand, serverNO);
		} catch (RemoteException e){
			e.printStackTrace();
			return Constants.BOOKFAILED;
		}
	}
	
	public List<Hotel> queryHotel(String cityName, int serverNO) {
		//logger.debug("request queryHotel on server " + serverNO);
		try {
			return stub.queryHotel(cityName, serverNO);
		} catch (RemoteException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public Transaction queryTransaction(String transactionID, int serverNO) {
		//logger.debug("request queryTransaction on server " + serverNO);
		try {
			return stub.queryTransaction(transactionID, serverNO);
		} catch (RemoteException e){
			e.printStackTrace();
			return null;
		}
	}
	
	
    public void sendMSGclose() {
    	
      logger.info("RMIClient is shutting down the connection...");

    }
    
	public List<Booker> queryBookerinfo(String bookerID) {
		//logger.debug("request queryBookerinfo by Booker Number " + bookerID);
		try {
			return stub.queryBookerinfo(bookerID);
		} catch (RemoteException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean insertBookers(String bookerID, String bookerName, String telePhone,
			String email, String passwd) {
		//logger.debug("request insertBookers");
		try {
			return stub.insertBookers(bookerID, bookerName, telePhone, email, passwd);
		} catch (RemoteException e){
			e.printStackTrace();
			return false;
		}
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		RMIClient rmiClient = new RMIClient("/home/hugh/Javahwk/src2");
//		boolean a = rmiClient.insertBookers("124578", "124578", "124578", "124578", MD5.getMD5("123456"));
//		if(a){
//			System.out.println("ad");
//		}
//		System.out.println(rmiClient.queryCity(2).get(0)); 
	
	}

}
