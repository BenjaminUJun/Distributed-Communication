package corba.hotelServer;


/**
* corba/hotelServer/ServerHelper.java .
* 由IDL-to-Java 编译器 (可移植), 版本 "3.2"生成
* 从HotelServant.idl
* 2015年5月5日 星期二 下午07时45分27秒 CST
*/

abstract public class ServerHelper
{
  private static String  _id = "IDL:corba/hotelServer/Server:1.0";

  public static void insert (org.omg.CORBA.Any a, corba.hotelServer.Server that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static corba.hotelServer.Server extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (corba.hotelServer.ServerHelper.id (), "Server");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static corba.hotelServer.Server read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_ServerStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, corba.hotelServer.Server value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static corba.hotelServer.Server narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof corba.hotelServer.Server)
      return (corba.hotelServer.Server)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      corba.hotelServer._ServerStub stub = new corba.hotelServer._ServerStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static corba.hotelServer.Server unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof corba.hotelServer.Server)
      return (corba.hotelServer.Server)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      corba.hotelServer._ServerStub stub = new corba.hotelServer._ServerStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
