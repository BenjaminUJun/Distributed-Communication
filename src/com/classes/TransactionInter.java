package classes;

import java.sql.Date;


public interface TransactionInter {
	public boolean insertBooker(String bookerID,String bookerName, String telePhone, String email, String passwd);
	public boolean insertTransaction(String transactionID, String roomID, String bookDT, String bookerID,
			String creditNO, String hotelID, String brand, int serverNO);
	public boolean queryTransaction(String transactionID, int serverNO);
	public boolean queryBooker(String bookerID);
	public String bookingRoom(String checkinDate, String checkoutDate, String hotelID,String transactionID, String roomID, 
			String bookerID,String creditNO, String brand, int serverNO);
	public Transaction queryTran(String transactionID, int serverNO);

}
