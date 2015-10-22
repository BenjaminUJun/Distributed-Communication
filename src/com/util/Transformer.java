package util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.sun.net.ssl.SSLContext;

public class Transformer {
	
//	public static void main(String[] args) {
//		String aa = "abc";
//		
//		byte[] ss = Transformer.objectToByte(aa);
//		//String p = new String(ss);
//		//byte[] pp = Transformer.objectToByte(p);
//		String cc = (String) Transformer.byteToObject(ss);
//		System.out.println(cc);
//		
//	}
	
    public static Object byteToObject(byte[] bytes) {
    	Object object = null;
    	try {
    		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
			ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
			
			object = objectInputStream.readObject();
			
			
			byteArrayInputStream.close();
			objectInputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return object;
    }
    
    public static byte[] objectToByte(Object object) {
    	byte[] bytes = null;
    	
    	
    	try {
    		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			
			objectOutputStream.writeObject(object);
			objectOutputStream.flush();
			bytes = byteArrayOutputStream.toByteArray();
			
			byteArrayOutputStream.close();
			objectOutputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return bytes;
    }
    
    public static String o2s(Object object) {
    	ObjectOutputStream objectOutputStream = null;
    	ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    	ByteArrayInputStream byteArrayInputStream = null;
    	ObjectInputStream objectInputStream = null;
    	
    	try {
			objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(object);
			byte[] bytes = byteArrayOutputStream.toByteArray();
			String string = byteArrayOutputStream.toString("ISO-8859-1");
			string = URLEncoder.encode(string, "UTF-8");
			objectOutputStream.close();
			return string;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    	
    }
    
    public static Object s2o(String string) {
    	ObjectOutputStream objectOutputStream = null;
    	ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    	ByteArrayInputStream byteArrayInputStream = null;
    	ObjectInputStream objectInputStream = null;
    	
    	try {
			byte[] bytes = string.getBytes();
			String s = URLDecoder.decode(string, "UTF-8");
			byteArrayInputStream = new ByteArrayInputStream(s.getBytes("ISO-8859-1"));
			objectInputStream = new ObjectInputStream(byteArrayInputStream);
			Object object = objectInputStream.readObject();
			objectInputStream.close();
			return object;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    	
    }
}

