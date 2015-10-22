package util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;


public class DES {

    public static byte[] encrypt(byte[] rawKeyData, String string) throws Exception {

        SecureRandom secureRandom = new SecureRandom();

        DESKeySpec desKeySpec = new DESKeySpec(rawKeyData);

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(desKeySpec);

        Cipher cipher = Cipher.getInstance("DES");

        cipher.init(Cipher.ENCRYPT_MODE, key, secureRandom);

        byte[] data = string.getBytes();
       // byte[] data = Transformer.objectToByte(string);
        byte[] encryptedData = cipher.doFinal(data);

        return encryptedData;
    }

    public static byte[] decrypt(byte[] rawKeyData, String encryptedData) throws Exception {

        SecureRandom secureRandom = new SecureRandom();

        DESKeySpec desKeySpec = new DESKeySpec(rawKeyData);

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(desKeySpec);

        Cipher cipher = Cipher.getInstance("DES");

        cipher.init(Cipher.DECRYPT_MODE, key, secureRandom);

        byte[] decryptedData = cipher.doFinal(RSA.decryptBASE64(encryptedData));

        return decryptedData;
    }
}
