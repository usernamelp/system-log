package cn.lp;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.lp.demo.AesUtils;
import cn.lp.entity.SysUserDTO;
import cn.lp.test.IpUtils;
import cn.lp.utils.PlaceholderUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import jdk.nashorn.internal.scripts.JS;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author lipeng
 * @since 1.0
 */
public class TestDemo {
    public static void main(String[] args) throws Exception {


        String format = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        System.out.println(format);
        String time = DateUtil.format(new Date(), "yyyyMMddHHmmss");
        String accountId = "N00000061856";
        String APISecret = "fcde5f20-67d9-11ee-8d1c-0f3699089b96";
        String auth = Base64.encodeBase64String((accountId + ":" + time).getBytes());
        String sig = DigestUtils.md5Hex(accountId + APISecret + time).toUpperCase();
        String passWord = DigestUtils.md5Hex("7moor" + accountId + "8000" + "nhl0cQx8000" + time).toLowerCase();
        System.out.println("time===" + time);
        System.out.println("sig===" + sig);
        System.out.println("passWord===" + passWord);
        System.out.println("auth===" + auth);


        String appSecret = "5b1571352cc6112a791b25d43d81d8d1";

        String encrypt = AesUtils.cbcEncrypt(appSecret, time + "#" + appSecret);
        System.out.println("x-ciphertext==" + encrypt);

/*        ArrayList<SysUserDTO> list = new ArrayList<>();

        SysUserDTO sysUserDTO = new SysUserDTO();
        sysUserDTO.setAge(10);
        sysUserDTO.setUsername("张三");

        SysUserDTO sysUserDTO2 = new SysUserDTO();
        sysUserDTO2.setAge(20);
        sysUserDTO2.setUsername("里斯");

        list.add(sysUserDTO);
//        list.add(sysUserDTO2);
        SysUserDTO xjUser = new SysUserDTO();
        if (list.size() == 1) {
            xjUser.setAge(list.get(0).getAge() + 10);
        } else {
            xjUser = list.stream().peek(x -> x.setId(1)).reduce((x, y) -> {
                int age = x.getAge() + y.getAge();
                return SysUserDTO.builder().age(age).build();
            }).orElse(new SysUserDTO());
        }
        xjUser.setUsername("小计");
        list.add(xjUser);
        System.out.println(xjUser);
        System.out.println(list);


        System.out.println(DateUtil.between(DateUtil.parseDate("2023-9-10"), DateUtil.parseDate("2023-9-19"), DateUnit.DAY));*/

        /*Map<String, String> param = new HashMap<String, String>();
        param.put("code","1236");
        sendMsg("SMS_271590503",param,"17638263401");

        HashMap<String, String> map = new HashMap<>();
        map.put("stah", "stah");
        map.put("aa", "aa");
        MapUtil.removeAny(map, "aa");
        System.out.println(map);
        System.out.println(DateUtil.format(DateUtil.offsetDay(new Date(), 15), "yyyy年MM月dd日"));
        Pattern pattern = Pattern.compile("\\{(.*?)\\}");
        Matcher matcher = pattern.matcher("111{sss}sdfa");
        while (matcher.find()) {
            String replaceAll = matcher.replaceAll("*");
            System.out.println(replaceAll);
        }
        System.out.println(IpUtils.getLocation("113.246.93.146"));*/

    }

    public static boolean sendMsg(String templateCode, Map<String, String> param, String mobile) {
        DefaultProfile profile = DefaultProfile.getProfile("default", "LTAI5tPqhNj4EYUPN8C2TvCL", "0ESTPQaMBeATYzM59q5ZrEUvFtG4Al");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        request.putQueryParameter("PhoneNumbers", mobile);   //手机号
        request.putQueryParameter("SignName", "佛山市知识产权保护中心");    //签名名称
        request.putQueryParameter("TemplateCode", templateCode);  //模板名称

        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));  //验证码转换json数据
        try {
            CommonResponse response = client.getCommonResponse(request);
            String data = response.getData();

            JSONObject jsonObject = JSONObject.parseObject(data);

            String code = jsonObject.getString("Code");
            if (Objects.equals(code, "OK")) {
                return true;
            }
        } catch (ClientException e) {
            System.out.println(e);
        }
        return false;
    }
}
