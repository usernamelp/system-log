package cn.lp.demo;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class AesUtils {

    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static final String ALGORITHM = "AES";

    /**
     * 加密处理
     *
     * @param content 加密内容
     */
    public static String encrypt(String content, String key) throws Exception {
        try {
            Key secretKeySpec = secretKeySpec(key);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);// 创建密码器
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);// 初始化
            byte[] result = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(result);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            throw new Exception(e);
        }
    }


    /**
     * 解密
     *
     * @param content 待解密内容
     * @param key     解密密钥
     */
    public static String decrypt(String content, String key) throws Exception {
        Key keySpec = secretKeySpec(key);
        //创建密码器
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] result = cipher.doFinal(Base64.getDecoder().decode(content));
            return new String(result, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            throw new Exception(e);
        }
    }

    /**
     * 加密处理
     *
     * @param content 加密内容
     */
    public static String cbcEncrypt(String content, String key) throws Exception {
        try {
            Key secretKeySpec = secretKeySpec(key);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);// 初始化
            byte[] result = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            return org.apache.commons.codec.binary.Base64.encodeBase64String(result);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private static Key secretKeySpec(String key) throws Exception {
        try {
            KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key.getBytes(StandardCharsets.UTF_8));
            generator.init(128, secureRandom);
            SecretKey secretKey = generator.generateKey();

            byte[] enCodeFormat = secretKey.getEncoded();
            return new SecretKeySpec(enCodeFormat, ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception(e);
        }
    }
}


