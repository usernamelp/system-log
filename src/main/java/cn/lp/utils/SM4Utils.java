package cn.lp.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.engines.SM4Engine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class SM4Utils extends GMBaseUtil {
    /**
     * SM4算法目前只支持128位（即密钥16字节）
     */
    private static final int DEFAULT_KEY_SIZE = 128;

    private static final String ALGORITHM_NAME = "SM4";
    private static final int BLOCK_SIZE = 16;
    private static final String PKCS7_PADDING = "PKCS7Padding";

    public static void main(String[] args) throws Exception {
        String key = "1234567890123456";
        String ivkey = "26EJHI568KMG3V1B";
        String encrypt = sm4Encrypt(key,ivkey,"admin");

        System.out.println(encrypt);
        String decrypt = sm4Decrypt(key,ivkey,encrypt);
        System.out.println(decrypt);
    }

    public static String sm4Encrypt(String key, String iv, String data) throws Exception {
        PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new SM4Engine()), new org.bouncycastle.crypto.paddings.PKCS7Padding());
        CipherParameters params = new ParametersWithIV(new KeyParameter(key.getBytes(StandardCharsets.UTF_8)), iv.getBytes(StandardCharsets.UTF_8));
        cipher.init(true, params);
        byte[] output = new byte[cipher.getOutputSize(data.getBytes(StandardCharsets.UTF_8).length)];
        int processedBytes = cipher.processBytes(data.getBytes(StandardCharsets.UTF_8), 0, data.getBytes(StandardCharsets.UTF_8).length, output, 0);
        cipher.doFinal(output, processedBytes);
        return Hex.encodeHexString(output);
    }

    public static String sm4Decrypt(String key, String iv, String data) throws Exception {
        PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new SM4Engine()), new org.bouncycastle.crypto.paddings.PKCS7Padding());
        CipherParameters params = new ParametersWithIV(new KeyParameter(key.getBytes(StandardCharsets.UTF_8)), iv.getBytes(StandardCharsets.UTF_8));
        cipher.init(false, params);
        byte[] decodeHex = Hex.decodeHex(data);
        byte[] output = new byte[cipher.getOutputSize(decodeHex.length)];
        int processedBytes = cipher.processBytes(decodeHex, 0, decodeHex.length, output, 0);
        processedBytes += cipher.doFinal(output, processedBytes);
        byte[] bytes = Arrays.copyOfRange(output, 0, processedBytes);
        return new String(bytes);
    }
}