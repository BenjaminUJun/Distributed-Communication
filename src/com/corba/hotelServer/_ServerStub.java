package corba.hotelServer;


/**
* corba/hotelServer/_ServerStub.java .
* 由IDL-to-Java 编译器 (可移植), 版本 "3.2"生成
* 从HotelServant.idl
* 2015年5月5日 星期二 下午07时45分27秒 CST
*/

public class _ServerStub extends org.omg.CORBA.portable.ObjectImpl implements corba.hotelServer.Server
{

  public String queryCity2 ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("queryCity2", true);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return queryCity2 (        );
            } finally {
                _releaseReply ($in);
            }
  } // queryCity2

  public String queryByCityName2 (String cityname)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("queryByCityName2", true);
                $out.write_string (cityname);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return queryByCityName2 (cityname        );
            } finally {
                _releaseReply ($in);
            }
  } // queryByCityName2

  public String queryRoomrates2 (String hotelID)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("queryRoomrates2", true);
                $out.write_string (hotelID);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return queryRoomrates2 (hotelID        );
            } finally {
                _releaseReply ($in);
            }
  } // queryRoomrates2

  public String queryVacantrooms2 (String hotelID, String checkindate, String checkoutdate)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("queryVacantrooms2", true);
                $out.write_string (hotelID);
                $out.write_string (checkindate);
                $out.write_string (checkoutdate);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return queryVacantrooms2 (hotelID, checkindate, checkoutdate        );
            } finally {
                _releaseReply ($in);
            }
  } // queryVacantrooms2


  // string insertBookers(in string bookerID, in string bookerName, in string telePhone, in string email, in string passwd);
  public String booking2 (String checkinDate, String checkoutDate, String hotelID, String roomID, String bookerID, String creditNO, String brand)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("booking2", true);
                $out.write_string (checkinDate);
                $out.write_string (checkoutDate);
                $out.write_string (hotelID);
                $out.write_string (roomID);
                $out.write_string (bookerID);
                $out.write_string (creditNO);
                $out.write_string (brand);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return booking2 (checkinDate, checkoutDate, hotelID, roomID, bookerID, creditNO, brand        );
            } finally {
                _releaseReply ($in);
            }
  } // booking2

  public String queryHotel2 (String cityName)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("queryHotel2", true);
                $out.write_string (cityName);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return queryHotel2 (cityName        );
            } finally {
                _releaseReply ($in);
            }
  } // queryHotel2


  // string queryBookerinfo(in string bookerID);
  public String queryTransaction2 (String transactionID)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("queryTransaction2", true);
                $out.write_string (transactionID);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return queryTransaction2 (transactionID        );
            } finally {
                _releaseReply ($in);
            }
  } // queryTransaction2

  public String getKey (String path)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getKey", true);
                $out.write_string (path);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getKey (path        );
            } finally {
                _releaseReply ($in);
            }
  } // getKey

  public String getSignature (String data)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getSignature", true);
                $out.write_string (data);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getSignature (data        );
            } finally {
                _releaseReply ($in);
            }
  } // getSignature

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:corba/hotelServer/Server:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     org.omg.CORBA.Object obj = orb.string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
   } finally {
     orb.destroy() ;
   }
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _ServerStub
