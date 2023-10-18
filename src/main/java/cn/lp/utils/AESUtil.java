package cn.lp.utils;


import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author lipeng
 * @since 1.0
 */
public class AESUtil {
    public static final String algorithm = "AES";

    public static final String transformation = "AES/CBC/PKCS5Padding";
    public final static byte[] aes_key = {9, 10, 11, 12, 13, 14, 15, 16, 1, 2, 3, 4, 5, 6, 7, 8};

    public static String encryptByAES(String original) throws Exception {
        Cipher cipher = Cipher.getInstance(transformation);
        SecretKeySpec keySpec = new SecretKeySpec(aes_key, algorithm);
        IvParameterSpec iv = new IvParameterSpec(aes_key);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
        // cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] bytes = cipher.doFinal(original.getBytes());
        return Base64.encodeBase64String(bytes);
    }

    public static String decryptByAES(String encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance(transformation);
        SecretKeySpec keySpec = new SecretKeySpec(aes_key, algorithm);
        IvParameterSpec iv = new IvParameterSpec(aes_key);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
        byte[] bytes = cipher.doFinal(Base64.decodeBase64(encrypted));
        return new String(bytes);
    }

    public static String encrypt(String original, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(transformation);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), algorithm);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
        // cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] bytes = cipher.doFinal(original.getBytes());
        return Base64.encodeBase64String(bytes);
    }

    public static String decryptByAES(String encrypted, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(transformation);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), algorithm);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
        byte[] bytes = cipher.doFinal(Base64.decodeBase64(encrypted));
        return new String(bytes);
    }


    public static String desEncrypt(String data, String key) {
        try {
            byte[] encrypted1 = Base64.decodeBase64(data);

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(key.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            byte[] original = cipher.doFinal(encrypted1);
            return new String(original);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(encrypt("user1", "26EJHI568KMG3V1B"));
        System.out.println(decryptByAES("1SFIHzn+zE2ehfTTOKcnnQ==", "26EJHI568KMG3V1B"));
    }

}