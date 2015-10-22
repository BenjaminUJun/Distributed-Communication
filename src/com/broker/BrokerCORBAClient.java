/**
 * 
 */
package broker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import classes.Hotel;
import classes.Room;
import classes.Transaction;
import corba.hotelServer.Server;
import corba.hotelServer.ServerHelper;
import util.Constants;
import util.DES;
import util.RSA;
import util.Transformer;

/**
 * @author Yu Jun 2015/5/14.
 *
 */
public class BrokerCORBAClient {
	private Server server;
	private Logger logger = Logger.getLogger(BrokerCORBAClient.class);
	private PublicKey publicKey = null;
	private PrivateKey privateKey = null;
	private byte[] symKey;
	public BrokerCORBAClient (String s) {
		PropertyConfigurator.configure(s + Constants.BROKER_PRO_STRING);
		getKeystore(s);
		
		Properties p = new java.util.Properties();
		p.setProperty("com.sun.CORBA.codeset.charsets", "0x05010001, 0x00010109");
		p.setProperty("com.sun.CORBA.codeset.wcharsets", "0x00010109, 0x05010001");
		String[] args = {"-ORBInitialPort","1235"};
		ORB orb = ORB.init(args, p);
		try {
            org.omg.CORBA.Object objectRef = orb.resolve_initial_references("NameService");
            NamingContext namingContext = NamingContextHelper.narrow(objectRef);
            NameComponent nameComponent = new NameComponent("corba.hotelServer.Server", "");
            NameComponent[] path = {nameComponent};
            objectRef = namingContext.resolve(path);
            server = ServerHelper.narrow(objectRef);
            logger.debug("get servant");
            String temp = server.getKey(s);
            symKey = RSA.decrypt(RSA.decryptBASE64(temp), publicKey);
            logger.debug("broker get the symKey as: "+new String(Constants.REALKEY));
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
    public String getSignature(String data) {
    	String _data;
		try {
			_data = RSA.encryptBASE64(DES.encrypt(symKey, data));
			return new String(DES.decrypt(symKey, server.getSignature(_data)));
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    	
    }
    
    public void getKeystore(String s) {
    	FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(s + Constants.RSA_KEYSTORE_STRING);
			KeyStore keyStore = KeyStore.getInstance("JKS");
	    	keyStore.load(fileInputStream, Constants.KEYSTOREPASSWORD.toCharArray());
	    	fileInputStream.close();
	    	
	    	String alias = "mykey";
	    	String password = Constants.KEYSTOREPASSWORD;
	    	Certificate certificate = keyStore.getCertificate(alias);
	    	publicKey = certificate.getPublicKey();
	    	privateKey = (PrivateKey) keyStore.getKey(alias, password.toCharArray());
	    	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    /********************************************************************/
    public List<String> queryCity(){
    	logger.debug("request queryCity on server 2");
    	List<String> cities = new ArrayList<String>();
    	String[] citys;
    	try {
    		//citys = (String []) Transformer.byteToObject(DES.decrypt(symKey, server.queryCity2()));
    		String data = server.queryCity2();
    		logger.debug("Data: " + data);
    		citys = (String []) Transformer.s2o(new String(DES.decrypt(symKey, data))); 
    		String signature = getSignature(citys[0]);
    		if (RSA.verify(RSA.decryptBASE64(citys[0]), RSA.encryptBASE64(publicKey.getEncoded()), signature)) {
    			logger.debug("get " + citys.length +" cities");
    			for (int i = 0; i < citys.length; ++i) {
    				cities.add(citys[i]);
    			}
    			return cities;
    		}
    	} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          	
    	return null;
    }
    
    public List<String> queryByCityName(String cityname){
    	logger.debug("request queryByCityName on server 2");
    	logger.debug("City Name: " + cityname); 
    	List<String> cities = new ArrayList<String>();
    	String[] citys;
    	try {
    		String _cityname = RSA.encryptBASE64(DES.encrypt(symKey, cityname));
    		//citys = (String []) Transformer.byteToObject(DES.decrypt(symKey, server.queryByCityName2(_cityname)));
    		//Object objects = Transformer.s2o(new String(DES.decrypt(symKey, encryptedData)))
    		String data = server.queryByCityName2(_cityname);
    		//System.out.println(data);
    		if (data.equals("NO")) { 
    			return new ArrayList<String>();
    		}
    		logger.debug("Data: " + data);
    		Object objects = Transformer.s2o(new String(DES.decrypt(symKey, data)));
    		//citys= (String []) Transformer.s2o(new String(DES.decrypt(symKey, data)));
    		citys = (String []) objects;
    		String signature = getSignature(citys[0]);
    		if (RSA.verify(RSA.decryptBASE64(citys[0]), RSA.encryptBASE64(publicKey.getEncoded()), signature)) {
    			logger.debug("get " + citys.length +" hotels");
    			for (int i = 0; i < citys.length; ++i) {
    				cities.add(citys[i]);
    			}
    			return cities;
    		}
    	} catch (GeneralSecurityException e) {
    		e.printStackTrace();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    public String queryRoomrates(String hotelID){
    	logger.debug("request queryRoomrates on server 2");
    	logger.debug("Hotel Number: " + hotelID); 
    	try {
    		String _hotelID = RSA.encryptBASE64(DES.encrypt(symKey, hotelID));
    		String data = server.queryRoomrates2(_hotelID);
    		logger.debug("Data: " + data);
    		String roomrate = new String(DES.decrypt(symKey, data));
    		String signature = getSignature(roomrate);
    		if (RSA.verify(RSA.decryptBASE64(roomrate), RSA.encryptBASE64(publicKey.getEncoded()), signature)) {
    			return roomrate;
    		}
    	} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    
    public List<Room> queryVacantrooms(String hotelID, String checkindate,
			String checkoutdate) {
    	logger.debug("request queryVacantrooms on server 2");
    	logger.debug("Hotel Number: " + hotelID); 
    	logger.debug("Check-in Date: " + checkindate); 
    	logger.debug("Check-out Date: " + checkoutdate);
			try {
				String _hotelID = RSA.encryptBASE64(DES.encrypt(symKey, hotelID));
				String _checkindate = RSA.encryptBASE64(DES.encrypt(symKey, checkindate));
				String _checkoutdate = RSA.encryptBASE64(DES.encrypt(symKey, checkoutdate));
				String data = server.queryVacantrooms2(_hotelID, _checkindate, _checkoutdate);
				logger.debug("Data: "+ data); 
				List<Room> rooms = (List<Room>) Transformer.s2o(new String (DES.decrypt(symKey, data)));
				if(rooms.size() == 0) {
					return null;
				}
				String signature = getSignature(Transformer.o2s(rooms));
				if (RSA.verify(RSA.decryptBASE64(Transformer.o2s(rooms)), RSA.encryptBASE64(publicKey.getEncoded()), signature)) {
					return rooms;
				}
    		} catch (GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	return null;
    }
    
    public String booking(String checkinDate, String checkoutDate, String hotelID, 
			String roomID, String bookerID,String creditNO, String brand) {
    	logger.debug("request booking on server 2");
    	logger.debug("Hotel Number: " + hotelID); 
    	logger.debug("Room Number: " +roomID);
    	logger.debug("Booker ID:" +bookerID);
    	logger.debug("Check-in Date: " + checkinDate); 
    	logger.debug("Check-out Date: " + checkoutDate);
    	logger.debug("Credit Number: "+ creditNO);
    	logger.debug("Brand: " + brand);
			try {
				String _checkinDate = RSA.encryptBASE64(DES.encrypt(symKey, checkinDate));
				String _checkoutDate = RSA.encryptBASE64(DES.encrypt(symKey, checkoutDate));
				String _hotelID = RSA.encryptBASE64(DES.encrypt(symKey, hotelID));
				String _roomID = RSA.encryptBASE64(DES.encrypt(symKey, roomID));
				String _bookerID = RSA.encryptBASE64(DES.encrypt(symKey, bookerID));
				String _creditNO = RSA.encryptBASE64(DES.encrypt(symKey, creditNO));
				String _brand = RSA.encryptBASE64(DES.encrypt(symKey, brand));
				String data = server.booking2(_checkinDate, _checkoutDate, _hotelID, _roomID, _bookerID, _creditNO, _brand);
				logger.debug("Data: " + data);
				String bookinfo = new String(DES.decrypt(symKey, data));
				String signature = getSignature(bookinfo);
				if (RSA.verify(RSA.decryptBASE64(bookinfo), RSA.encryptBASE64(publicKey.getEncoded()), signature)) {
					return bookinfo;
				}
    		} catch (GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	return null;
    }
    
    public List<Hotel> queryHotel(String cityName) {
    	logger.debug("request queryHotel on server 2");
    	logger.debug("City Name: " + cityName); 
			try {
				String _cityName = RSA.encryptBASE64(DES.encrypt(symKey, cityName));
				String data = server.queryHotel2(_cityName);
				if (data.equals("NO")) { 
	    			return new ArrayList<Hotel>();
				}
				logger.debug("Data: " + data);
				Object objects = Transformer.s2o(new String(DES.decrypt(symKey, data))); 
				List<Hotel> hotels = (List<Hotel>) objects;
				//List<Hotel> hotels = (List<Hotel>) Transformer.s2o(new String (DES.decrypt(symKey, server.queryHotel2(_cityName)))); 
				if (hotels.size() == 0) {
					return null;
				}
				String signature = getSignature(Transformer.o2s(hotels));
				if (RSA.verify(RSA.decryptBASE64(Transformer.o2s(hotels)), RSA.encryptBASE64(publicKey.getEncoded()), signature)) {
					return hotels;
				}
    		} catch (GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	return null;
    }
    
    public Transaction queryTransaction(String transactionID) {
    	logger.debug("request queryTransaction on server 2");
    	logger.debug("Transaction Number: " + transactionID); 
			try {
				String _transactionID = RSA.encryptBASE64(DES.encrypt(symKey, transactionID));
				Transaction transaction = (Transaction) Transformer.s2o(new String (DES.decrypt(symKey, server.queryTransaction2(_transactionID))));
				String signature = getSignature(Transformer.o2s(transaction));
				if(RSA.verify(RSA.decryptBASE64(Transformer.o2s(transaction)), RSA.encryptBASE64(publicKey.getEncoded()), signature)){
					return transaction;
				}
    		} catch (GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	return null;
    }
}
