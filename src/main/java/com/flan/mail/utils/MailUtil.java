package com.flan.mail.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;

public class MailUtil {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    // 主题
    private String subject;
    // 内容
    private String content;

    /**
     * HTML 文本邮件
     * @param to    收件人
     * @param code  验证码
     * @throws MessagingException
     */
    public void sendHtmlMail(String to, String code) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        try {
            File in = new File("src/main/resources/code.html");

            // 从 code.html 获取 主题
            Document doc = Jsoup.parse(in, "UTF-8");
            Elements title = doc.select("title");
            subject = title.text();

            // 将 验证码传递过来
            Element codeTemplate = doc.select("span[id=code]").first();
            codeTemplate.text(code);
            content = doc.html();

            // 发送信息
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            helper.setFrom(from);

            mailSender.send(message);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("code 文件不存在");
        }
    }
}
