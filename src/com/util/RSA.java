
package util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class RSA {
	
	public static Cipher cipher = null;

	
	public static byte[] decryptBASE64(String key) throws Exception{
		return (new BASE64Decoder().decodeBuffer(key));
	}
	
	public static String encryptBASE64(byte[] key)throws Exception{
		return (new BASE64Encoder().encode(key));
	}

	public static Map<String, Object> initKey() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(1024);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		
		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();
		
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put("RSAPublicKey", publicKey);
		keyMap.put("RSAPrivateKey", privateKey);
		
		return keyMap;
	}
	
	public static PublicKey getPublicKey(Map<String, Object> keyMap) throws Exception {
		PublicKey key = (PublicKey) keyMap.get("RSAPublicKey");
		return key;
	}
	
	public static PrivateKey getPrivateKey(Map<String, Object> keyMap) throws Exception {
		PrivateKey key = (PrivateKey) keyMap.get("RSAPrivateKey");
		return key;
	}
	
	
	public static byte[] encrypt(byte[] data,Key key)throws Exception{		
		cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		
		return cipher.doFinal(data);
	}
	

	public static byte[] decrypt(byte[] data,Key key)throws Exception{			
		cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, key);
		
		return cipher.doFinal(data);
	}

	public static String sign(byte[] data, String privateKey) throws Exception {
		byte[] keyBytes = decryptBASE64(privateKey);
		
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey2 = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		
		Signature signature = Signature.getInstance("MD5withRSA");
		signature.initSign(privateKey2);
		signature.update(data);
		
		return encryptBASE64(signature.sign());
	}
	
	public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
		byte[] keyBytes = decryptBASE64(publicKey);
		
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey2 = keyFactory.generatePublic(x509EncodedKeySpec);
		
		Signature signature = Signature.getInstance("MD5withRSA");
		signature.initVerify(publicKey2);
		signature.update(data);
		
		return signature.verify(decryptBASE64(sign));
	}
	
	
}
