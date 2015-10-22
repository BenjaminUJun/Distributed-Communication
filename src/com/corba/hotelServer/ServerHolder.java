package corba.hotelServer;

/**
* corba/hotelServer/ServerHolder.java .
* 由IDL-to-Java 编译器 (可移植), 版本 "3.2"生成
* 从HotelServant.idl
* 2015年5月5日 星期二 下午07时45分27秒 CST
*/

public final class ServerHolder implements org.omg.CORBA.portable.Streamable
{
  public corba.hotelServer.Server value = null;

  public ServerHolder ()
  {
  }

  public ServerHolder (corba.hotelServer.Server initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = corba.hotelServer.ServerHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    corba.hotelServer.ServerHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return corba.hotelServer.ServerHelper.type ();
  }

}
