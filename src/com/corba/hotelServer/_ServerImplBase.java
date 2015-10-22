package corba.hotelServer;


/**
* corba/hotelServer/_ServerImplBase.java .
* 由IDL-to-Java 编译器 (可移植), 版本 "3.2"生成
* 从HotelServant.idl
* 2015年5月5日 星期二 下午07时45分27秒 CST
*/

public abstract class _ServerImplBase extends org.omg.CORBA.portable.ObjectImpl
                implements corba.hotelServer.Server, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors
  public _ServerImplBase ()
  {
  }

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("queryCity2", new java.lang.Integer (0));
    _methods.put ("queryByCityName2", new java.lang.Integer (1));
    _methods.put ("queryRoomrates2", new java.lang.Integer (2));
    _methods.put ("queryVacantrooms2", new java.lang.Integer (3));
    _methods.put ("booking2", new java.lang.Integer (4));
    _methods.put ("queryHotel2", new java.lang.Integer (5));
    _methods.put ("queryTransaction2", new java.lang.Integer (6));
    _methods.put ("getKey", new java.lang.Integer (7));
    _methods.put ("getSignature", new java.lang.Integer (8));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // corba/hotelServer/Server/queryCity2
       {
         String $result = null;
         $result = this.queryCity2 ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 1:  // corba/hotelServer/Server/queryByCityName2
       {
         String cityname = in.read_string ();
         String $result = null;
         $result = this.queryByCityName2 (cityname);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 2:  // corba/hotelServer/Server/queryRoomrates2
       {
         String hotelID = in.read_string ();
         String $result = null;
         $result = this.queryRoomrates2 (hotelID);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 3:  // corba/hotelServer/Server/queryVacantrooms2
       {
         String hotelID = in.read_string ();
         String checkindate = in.read_string ();
         String checkoutdate = in.read_string ();
         String $result = null;
         $result = this.queryVacantrooms2 (hotelID, checkindate, checkoutdate);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }


  // string insertBookers(in string bookerID, in string bookerName, in string telePhone, in string email, in string passwd);
       case 4:  // corba/hotelServer/Server/booking2
       {
         String checkinDate = in.read_string ();
         String checkoutDate = in.read_string ();
         String hotelID = in.read_string ();
         String roomID = in.read_string ();
         String bookerID = in.read_string ();
         String creditNO = in.read_string ();
         String brand = in.read_string ();
         String $result = null;
         $result = this.booking2 (checkinDate, checkoutDate, hotelID, roomID, bookerID, creditNO, brand);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 5:  // corba/hotelServer/Server/queryHotel2
       {
         String cityName = in.read_string ();
         String $result = null;
         $result = this.queryHotel2 (cityName);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }


  // string queryBookerinfo(in string bookerID);
       case 6:  // corba/hotelServer/Server/queryTransaction2
       {
         String transactionID = in.read_string ();
         String $result = null;
         $result = this.queryTransaction2 (transactionID);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 7:  // corba/hotelServer/Server/getKey
       {
         String path = in.read_string ();
         String $result = null;
         $result = this.getKey (path);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 8:  // corba/hotelServer/Server/getSignature
       {
         String data = in.read_string ();
         String $result = null;
         $result = this.getSignature (data);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:corba/hotelServer/Server:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }


} // class _ServerImplBase
