/**
 * 
 */
package rmi;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import util.Constants;
import broker.BrokerCORBAClient;
import broker.BrokerRMIClient;
import classes.Booker;
import classes.BookerinfoImple;
import classes.Hotel;
import classes.Room;
import classes.Transaction;
import classes.TransactionInterImple;

/**
 * @author Yu Jun 2015/5/6.
 *
 */
public class RMIBrokerInterfaceImple implements RMIBrokerInterface {
	
	private BrokerRMIClient brokerRMIClient;
	private BrokerCORBAClient brokerCORBAClient;
	private Logger logger;
	private BookerinfoImple bookerinfoImple = null;
	private TransactionInterImple transactionInterImple = null;

	public RMIBrokerInterfaceImple (String path) {
		brokerRMIClient = new BrokerRMIClient(path);
		brokerCORBAClient = new BrokerCORBAClient(path);
		bookerinfoImple = new BookerinfoImple(path);
		transactionInterImple = new TransactionInterImple(path);
		logger = Logger.getLogger(RMIBrokerInterfaceImple.class);
		PropertyConfigurator.configure(path + Constants.BROKER_PRO_STRING);
	}

	@Override
	public List<String> queryCity(int serverNO) throws RemoteException {
		// TODO Auto-generated method stub
		if (serverNO == 1) {
			logger.debug("request queryCity on server 1");
			return brokerRMIClient.queryCity();
		}else if (serverNO == 2) {
			logger.debug("request queryCity on server 2");
			return brokerCORBAClient.queryCity();
		}
		return null;
	}

	@Override
	public List<String> queryByCityName(String cityname, int serverNO) throws RemoteException {
		// TODO Auto-generated method stub
		if (serverNO == 1) {
			logger.debug("request queryByCityName on server 1");
			return brokerRMIClient.queryByCityName(cityname);
		}else if (serverNO == 2) {
			logger.debug("request queryByCityName on server 2");
			return brokerCORBAClient.queryByCityName(cityname);
		}
		return null;
	}

	@Override
	public String queryRoomrates(String hotelID, int serverNO)
			throws RemoteException {
		// TODO Auto-generated method stub
		if (serverNO == 1) {
			logger.debug("request queryRoomrates on server 1");
			return brokerRMIClient.queryRoomrates(hotelID);
		}else if (serverNO == 2) {
			logger.debug("request queryRoomrates on server 2");
			return brokerCORBAClient.queryRoomrates(hotelID);
		}
		return null;
	}

	@Override
	public List<Room> queryVacantrooms(String hotelID, String checkindate,
			String checkoutdate, int serverNO) throws RemoteException {
		// TODO Auto-generated method stub
		if (serverNO == 1) {
			logger.debug("request queryVacantrooms on server 1");
			return brokerRMIClient.queryVacantrooms(hotelID, checkindate, checkoutdate);
		}else if (serverNO == 2) {
			logger.debug("request queryVacantrooms on server 2");
			return brokerCORBAClient.queryVacantrooms(hotelID, checkindate, checkoutdate);
		}
		return null;
	}

	@Override
	public String booking(String checkinDate, String checkoutDate,
			String hotelID, String roomID, String bookerID, String creditNO,
			String brand, int serverNO) throws RemoteException {
		// TODO Auto-generated method stub
		if (serverNO == 1) {
			logger.debug("request booking on server 1");
			return brokerRMIClient.booking(checkinDate, checkoutDate, hotelID, roomID, bookerID, creditNO, brand);
		}else if (serverNO == 2) {
			logger.debug("request booking on server 2");
			return brokerCORBAClient.booking(checkinDate, checkoutDate, hotelID, roomID, bookerID, creditNO, brand);
		}
		return null;
	}

	@Override
	public List<Hotel> queryHotel(String cityName, int serverNO)
			throws RemoteException {
		// TODO Auto-generated method stub
		if (serverNO == 1) {
			logger.debug("request queryHotel on server 1");
			return brokerRMIClient.queryHotel(cityName);
		}else if (serverNO == 2) {
			logger.debug("request queryHotel on server 2");
			return brokerCORBAClient.queryHotel(cityName);
		}
		return null;
	}

	@Override
	public Transaction queryTransaction(String transactionID, int serverNO)
			throws RemoteException {
		// TODO Auto-generated method stub
		if (serverNO == 1) {
			logger.debug("request queryTransaction on server 1");
			return brokerRMIClient.queryTransaction(transactionID);
		}else if (serverNO == 2) {
			logger.debug("request queryTransaction on server 2");
			return brokerCORBAClient.queryTransaction(transactionID);
		}
		return null;
	}
	
	@Override
	public List<Booker> queryBookerinfo(String bookerID)
			throws RemoteException {
		// TODO Auto-generated method stub
		logger.debug("request queryBookerinfo by Booker Number: " + bookerID);
		List<Booker> bookers = new ArrayList<Booker> ();
		bookers = bookerinfoImple.queryBooker(bookerID, 1);
		bookers.addAll(bookerinfoImple.queryBooker(bookerID, 2));
		return bookers;
	}
	
	@Override
	public boolean insertBookers(String bookerID, String bookerName,
			String telePhone, String email, String passwd)
			throws RemoteException {
		// TODO Auto-generated method stub
		logger.debug("request insertBookers");
		boolean exit = transactionInterImple.insertBooker(bookerID, bookerName, telePhone, email, passwd);
		return exit;
	}

}
