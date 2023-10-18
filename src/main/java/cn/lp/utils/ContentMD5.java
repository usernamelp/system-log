package cn.lp.utils;

import cn.hutool.core.codec.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ContentMD5 {

    private static Logger logger = LoggerFactory.getLogger(ContentMD5.class);

    /***
     * Encode 'Content-MD5' with base64
     * @param filePath
     * @return
     */
    public static String getStringContentMD5(String filePath) throws Exception {
        byte[] bytes = getFileMD5Bytes128(filePath);
        return Base64.encode(bytes);
    }

    /***
     * Get Content-MD5 byte array（128）
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static byte[] getFileMD5Bytes128(String filePath) throws IOException, NoSuchAlgorithmException {
        FileInputStream fis = null;
        byte[] md5Bytes = null;
        try {
            File file = new File(filePath);
            fis = new FileInputStream(file);
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = fis.read(buffer, 0, 1024)) != -1) {
                md5.update(buffer, 0, length);
            }
            md5Bytes = md5.digest();
            fis.close();
        } catch (NoSuchAlgorithmException | IOException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        return md5Bytes;
    }

}
