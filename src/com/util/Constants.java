package util;

public class Constants {
	/*The constants of Assignment 1*/
	public static final String CONNECTION_STRING = "jdbc:mysql://localhost/id26346702";
	public static final String BOOKERINSERT = "ADD BOOKER SUCCESSFULLY";
	public static final String INSERTFAILED = "ADD BOOKER FAILED";
	public static final String USERNAME = "fit5183a2";
	public static final String NOQUALIFIEDROOM = "NO_QUALIFIEDROOM";
	public static final String OCCUPIEDROOM = "This Room is Occupied by Someone at the time Between Check-in Date and Check-out Date!";
	public static final String DUPLICATEBOOKING = "DUPLICATE_BOOKING";
	public static final String NOTHAVE = "WE DO NOT HAVE THIS ROOM NO. IN THIS HOTEL ";
	public static final String EOS = "EOS";
	public static final String SOS = "SOS";
	public static final String BYE = "bye";
	public static final int PORT = 26340;
	public static final String CITY = "C";
	public static final String USER = "U";
	public static final String HOTEL ="LH";
	public static final String ROOMRATE = "RR";
	public static final String VACANT = "V";
	public static final String BOOK = "B";
	public static final String LISTHOTEL = "HI";
	public static final String QUIT = "Q";
	public static final String CR_LF = "\r\n";
	public static final String NOCONNECTION = "NOCONNECTION";
	public static final String BOOKSUCCESSFUL = "BOOKSUCCESSFUL";
	public static final String BOOKFAILED = "BOOKFAILED";
	public static final String NOTRANSACTION = "NOSUCHTRANSACTION";
	public static final String NOROOMRATEINFO = "NO ROOM RATE INFORMATION OF THIS HOTEL";
	/***********************************************************/
	/*The constants of Assignment 2*/
	public static final String SERVDIR = "/WEB-INF/classes";
	public static final String RMI = "1";
	public static final String CORBA = "2";
    public final static String RMISERVER_STRING = "rmi://localhost:1099/server";
    public final static String RMIBROKER_STRING = "rmi://localhost:1099/broker";
    public static String SERVER_KEYSTORE_STRING = "/server.keystore";
    public static String CLIENT_KEYSTORE_STRING = "/client.keystore";
    public final static String RSA_KEYSTORE_STRING = "/rsa.keystore";
    public static String RMISER_PRO_STRING = "/RMIServer.properties";
    public static String CORBASER_PRO_STRING = "/CORBAServer.properties";
    public static String BROKER_PRO_STRING = "/Broker.properties";
    public static String CLIENT_PRO_STRING = "/Client.properties";
    public static String DB_PRO_STRING = "/DB.properties";
    public final static byte[] REALKEY = "yujun123".getBytes();
    public static String KEYSTOREPASSWORD = "yujun123";
}
