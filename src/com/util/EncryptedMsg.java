package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class EncryptedMsg {

    public static String getMD5(String string) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(string.getBytes());
            byte[] temp = messageDigest.digest();
            char[] chars = new char[16 * 2];
            int k = 0;
            for (int i = 0; i < 16; i++) {
                byte b = temp[i];
                chars[k++] = hexDigits[b >>> 4 & 0xf];
                chars[k++] = hexDigits[b & 0xf];
            }
            return new String(chars);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args) {
       // System.out.println(getMD5(Constants.PASSWORD));
    }
}
