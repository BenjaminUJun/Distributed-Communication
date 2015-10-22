package corba.hotelServer;


/**
* corba/hotelServer/ServerOperations.java .
* 由IDL-to-Java 编译器 (可移植), 版本 "3.2"生成
* 从HotelServant.idl
* 2015年5月5日 星期二 下午07时45分27秒 CST
*/

public interface ServerOperations 
{
  String queryCity2 ();
  String queryByCityName2 (String cityname);
  String queryRoomrates2 (String hotelID);
  String queryVacantrooms2 (String hotelID, String checkindate, String checkoutdate);

  // string insertBookers(in string bookerID, in string bookerName, in string telePhone, in string email, in string passwd);
  String booking2 (String checkinDate, String checkoutDate, String hotelID, String roomID, String bookerID, String creditNO, String brand);
  String queryHotel2 (String cityName);

  // string queryBookerinfo(in string bookerID);
  String queryTransaction2 (String transactionID);
  String getKey (String path);
  String getSignature (String data);
} // interface ServerOperations
