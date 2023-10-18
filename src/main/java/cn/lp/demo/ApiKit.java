//需要修改包路径
package cn.lp.demo;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

public class ApiKit {

    private static final Logger logger = LoggerFactory.getLogger(ApiKit.class);

    public static String hmacSha1(String data, String key) {
        String result = "";
        if (StringUtils.isBlank(data) || StringUtils.isBlank(key)) {
            return result;
        }
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data.getBytes());
            result = (new BASE64Encoder()).encode(rawHmac);
        } catch (Exception e) {
            logger.error("Failed to generate HMAC : " + e.getMessage(), e);
        }
        return result;
    }

    public static String getSign(String appKey, String appSecret, String timestamp) {
        String sign = "";
        if (StringUtils.isBlank(appKey) || StringUtils.isBlank(appSecret) || StringUtils.isBlank(timestamp)) {
            return sign;
        }
        sign = hmacSha1(appKey + timestamp, appSecret);
        return sign;
    }
}
