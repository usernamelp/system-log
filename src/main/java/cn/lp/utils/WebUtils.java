//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.lp.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class WebUtils {
    static final String[] ipHeaders = new String[]{"X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP"};

    public WebUtils() {
    }


    public static Map<String, String> queryStringToMap(String queryString, String charset) {
        try {
            Map<String, String> map = new HashMap();
            String[] decode = URLDecoder.decode(queryString, charset).split("&");
            String[] var4 = decode;
            int var5 = decode.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String keyValue = var4[var6];
                String[] kv = keyValue.split("[=]", 2);
                map.put(kv[0], kv.length > 1 ? kv[1] : "");
            }

            return map;
        } catch (UnsupportedEncodingException var9) {
            throw new UnsupportedOperationException(var9);
        }
    }

    public static HttpServletRequest getHttpServletRequest() {
        try {
            return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception var1) {
            return null;
        }
    }

    public static Map<String, String> getParameters(HttpServletRequest request) {
        Map<String, String> parameters = new HashMap();
        Enumeration enumeration = request.getParameterNames();

        while(enumeration.hasMoreElements()) {
            String name = String.valueOf(enumeration.nextElement());
            parameters.put(name, request.getParameter(name));
        }

        return parameters;
    }

    public static Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap();
        Enumeration enumeration = request.getHeaderNames();

        while(enumeration.hasMoreElements()) {
            String key = (String)enumeration.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }

    public static String getIpAddr(HttpServletRequest request) {
        String[] var1 = ipHeaders;
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            String ipHeader = var1[var3];
            String ip = request.getHeader(ipHeader);
            if (!StringUtils.isEmpty(ip) && !ip.contains("unknown")) {
                return ip;
            }
        }

        return request.getRemoteAddr();
    }

    public static String getBasePath(HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        return basePath;
    }
}
