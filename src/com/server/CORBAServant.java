/**
 * 
 */
package server;

import java.util.ArrayList;
import java.util.List;
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
import java.io.Serializable;  
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import util.Constants;
import util.DES;
import util.RSA;
import classes.BookerinfoImple;
import classes.Hotel;
import classes.QueryHotelImple;
import classes.QueryRoomImple;
import classes.Room;
import classes.Transaction;
import classes.TransactionInterImple;
import corba.hotelServer.*;
import util.Transformer;
/**
 * @author Yu Jun 2015/5/16.
 *
 */
public class CORBAServant extends _ServerImplBase {

	private BookerinfoImple bookerinfo = null;
	private QueryHotelImple queryhotelinfo = null;
	private QueryRoomImple queryroominfo = null;
	private TransactionInterImple transactioninfo = null;
	
	Logger logger = Logger.getLogger(CORBAServant.class);
	private byte[] symKey = Constants.REALKEY;
    private PublicKey publicKey = null;
    private PrivateKey privateKey = null;
    
	public CORBAServant(String path) {
    	PropertyConfigurator.configure(path + Constants.CORBASER_PRO_STRING);
    	bookerinfo =  new BookerinfoImple(path);
    	queryhotelinfo = new QueryHotelImple(path);
    	queryroominfo = new QueryRoomImple(path);
    	transactioninfo = new TransactionInterImple(path);
	}
	
    public static void main(String[] args) throws Exception {
//  		CORBAServant corbaservant = new CORBAServant("/home/hugh/Javahwk/src2");
//    	String a = corbaservant.queryByCityName2("s");
//    	byte[] syms1 = Constants.RAWKEY_BYTES;
//    	if(a==null) {
//    		System.out.println("dfds");
//    	}
    		//System.out.println(Transformer.s2o(new String(DES.decrypt(syms1, a))));  
    		
    	//	System.out.println(Transformer.objectToByte(a)); 
//    		System.out.println(a.getBytes());
//    		System.out.println(Transformer.objectToByte(a)); 
//    		byte[] syms1 = Constants.RAWKEY_BYTES;
//    		byte[] syms2 = Constants.RAWKEY_BYTES;
//    		syms1 = DES.decrypt(syms2, a);
//    		
//    		String [] aa= new String [8];  
//    		aa = (String[]) Transformer.s2o(new String(syms1));
//    		System.out.println(aa); 
    		//System.out.println("1: "+ RSA.decryptBASE64(a));
    		//syms1 = DES.decrypt(syms2, a);
    	//	System.out.println("2: "+RSA.decryptBASE64());
    	//	System.out.println(syms1); 
    		//System.out.println(syms1);
    		//Transformer.byteToObject(syms1);
    }
	
	
	@Override
	public String queryCity2() {
		// TODO Auto-generated method stub
		List<String> cities = queryroominfo.queryCityList(2);
		logger.debug("Request queryCity on Server 2");
		logger.debug("City Number: " +cities.size());
		String[] cityStrings = new String[cities.size()];
		for(int i = 0; i < cities.size(); i++) {
			cityStrings[i] = cities.get(i);
		}
		try {
			String results = RSA.encryptBASE64(DES.encrypt(symKey, Transformer.o2s(cityStrings)));
			logger.debug("Results: "+ results); 
			return results; 
		} catch(GeneralSecurityException e){
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String queryByCityName2(String cityname) {
		// TODO Auto-generated method stub
		List<String> cities = null;
		logger.debug("Request queryByCityName on Server 2");
		try {
			String _cityname = new String(DES.decrypt(symKey, cityname)); 
			cities =  queryroominfo.queryByCityName(_cityname, 2);
			logger.debug("City Name: " + cityname);
		} catch(GeneralSecurityException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(cities.size() == 0) {
			logger.debug("No this City!");
			try {
				return "NO";			
			} catch (Exception e){
				e.printStackTrace();
			} 
		}
		logger.debug("City Number: "+ cities.size());
		String[] cityString = new String[cities.size()];
		for(int i = 0;i < cities.size(); ++i) {
			cityString[i] = cities.get(i);
		}
		try {
			String results = RSA.encryptBASE64(DES.encrypt(symKey, Transformer.o2s(cityString)));
			logger.debug("Results: " + results);
			return results;
		} catch(GeneralSecurityException e){
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String queryRoomrates2(String hotelID) {
		// TODO Auto-generated method stub
		String roomrate = null;
		logger.debug("Request queryRoom");
		try {
			String _hotelID = new String(DES.decrypt(symKey, hotelID));
			roomrate = queryroominfo.queryByHotel(_hotelID, 2);
			logger.debug("Hotel Number: " + hotelID); 
			String results = RSA.encryptBASE64(DES.encrypt(symKey, roomrate));
			logger.debug("Results: " + results);
			return results;
		} catch(GeneralSecurityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public String queryVacantrooms2(String hotelID, String checkindate,
			String checkoutdate) {
		// TODO Auto-generated method stub
		logger.debug("request queryVacantrooms on server 2");
		List<Room> rooms = null;
		try {
			String _hotelID = new String(DES.decrypt(symKey, hotelID));
			String _checkindate = new String(DES.decrypt(symKey, checkindate));
			String _checkoutdate = new String(DES.decrypt(symKey, checkoutdate));
			rooms = queryroominfo.queryByHotel(_hotelID, _checkindate, _checkoutdate, 2);
			logger.debug("Hotel Number: "+ hotelID);
			logger.debug("Check-in Date: " +checkindate);
			logger.debug("Check-out Date: " +checkoutdate);
			
		} catch(GeneralSecurityException e){
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
		if(rooms.size() == 0){
			logger.debug("NO vacant Rooms!");
			try {
				return RSA.encryptBASE64(DES.encrypt(symKey, Transformer.o2s(new ArrayList<Room>())));
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		logger.debug("The Vacant Rooms:" + rooms.size());
		try {
			String results =  RSA.encryptBASE64(DES.encrypt(symKey, Transformer.o2s(rooms)));
			logger.debug("Results: "+results);
			return results;
		} catch(GeneralSecurityException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String booking2(String checkinDate, String checkoutDate,
			String hotelID, String roomID, String bookerID, String creditNO,
			String brand) {
		// TODO Auto-generated method stub
		logger.debug("request booking on server 2");
		String transacID = null;
		try {
			 String _checkinDate = new String(DES.decrypt(symKey, checkinDate));
			 String _checkoutDate = new String(DES.decrypt(symKey, checkoutDate));
			 String _hotelID = new String(DES.decrypt(symKey, hotelID));
			 String _roomID = new String(DES.decrypt(symKey, roomID));
			 String _bookerID = new String(DES.decrypt(symKey, bookerID));
			 String _creditNO = new String(DES.decrypt(symKey, creditNO));
			 String _brand = new String(DES.decrypt(symKey, brand));
			 transacID = _checkinDate + _checkoutDate + _hotelID + _roomID + _bookerID;
			 logger.debug("Checking duplicated booking!");
			 boolean exit = transactioninfo.queryTransaction(transacID, 2);
			 if(!exit){
				logger.debug("booking...");
				String str = transactioninfo.bookingRoom(_checkinDate, _checkoutDate, _hotelID, transacID, _roomID, _bookerID, _creditNO, _brand,2);
				String results = RSA.encryptBASE64(DES.encrypt(symKey, str));
				logger.debug("Results: " + results);
				return results; 
			 }else{
				logger.debug("You have already booked this room!");
				return RSA.encryptBASE64(DES.encrypt(symKey, Constants.DUPLICATEBOOKING));
			 }
		} catch(GeneralSecurityException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return Constants.BOOKFAILED;
	}

	@Override
	public String queryHotel2(String cityName) {
		// TODO Auto-generated method stub
		logger.debug("request queryHotel on server 2");
		List<Hotel> hotels = null;
		try {
			String _cityName = new String(DES.decrypt(symKey, cityName));
			hotels = queryhotelinfo.queryHotel(_cityName, 2);
			logger.debug("City name:" + cityName); 
		} catch(GeneralSecurityException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(hotels.size() == 0) {
			logger.debug("No Hotels in this City!");
			try {
				return "NO"; 
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		logger.debug("The Hotels: " + hotels.size());
		try {
			String results = RSA.encryptBASE64(DES.encrypt(symKey, Transformer.o2s(hotels)));
			logger.debug("Results: " + results);
			return results;
		} catch(GeneralSecurityException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String queryTransaction2(String transactionID) {
		// TODO Auto-generated method stub
		logger.debug("request queryTransaction on server 2");
		Transaction trans = null;
		try {
			String _transactionID = new String(DES.decrypt(symKey, transactionID));
			trans =  transactioninfo.queryTran(_transactionID, 2);
			logger.debug("Transaction ID: "+ transactionID); 
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(trans == null) {
			logger.debug("NO this transaction...");
			try {
				return RSA.encryptBASE64(DES.encrypt(symKey, Constants.NOTRANSACTION));
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		logger.debug("Transaction information"); 
		try {
			String results = RSA.encryptBASE64(DES.encrypt(symKey, Transformer.o2s(trans)));
			logger.debug("Results: " + results); 
			return results; 
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getKey(String path) {
		// TODO Auto-generated method stub
    	try {
    		getKeystore(path);
    		
			String _symKey = RSA.encryptBASE64(RSA.encrypt(symKey, privateKey));
			logger.debug("sending private key encrypted symKey: " + _symKey);
			return _symKey;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getSignature(String data) {
		// TODO Auto-generated method stub
    	try {
			String _data = new String(DES.decrypt(symKey, data));
			return RSA.encryptBASE64(DES.encrypt(symKey, RSA.sign(RSA.decryptBASE64(_data), RSA.encryptBASE64(privateKey.getEncoded()))));
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
    public void getKeystore(String path) {
    	FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(path + Constants.RSA_KEYSTORE_STRING);
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
	
//	@Override
//	public String insertBookers(String bookerID, String bookerName,
//			String telePhone, String email, String passwd) {
//		// TODO Auto-generated method stub
//		
//		return null;
//	}
	
//	@Override
//	public String queryBookerinfo(String bookerID) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	

}
