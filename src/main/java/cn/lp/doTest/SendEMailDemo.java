package cn.lp.doTest;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

public class SendEMailDemo {
    public static void main(String[] args) {
        JavaMailSenderImpl jms = new JavaMailSenderImpl();
        jms.setHost("smtp.qq.com");
        jms.setUsername("409122753@qq.com");
        jms.setPassword("uyzjlrevxfbubijh");
        jms.setDefaultEncoding("utf-8");
//        Properties p = new Properties();
//        p.setProperty("mail.smtp.auth",  Boolean.toString(false));
//        jms.setJavaMailProperties(p);
        MimeMessage mimeMessage = jms.createMimeMessage();
        try {
            String content = "测试短信";
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            //收件人

            messageHelper.setTo("17638263401@163.com");
            //标题
            messageHelper.setSubject("测试");
            //内容
            messageHelper.setText(content, true);
            //发件人
            messageHelper.setFrom("409122753@qq.com");
            jms.send(mimeMessage);
        } catch (MessagingException e) {

        }
    }
}