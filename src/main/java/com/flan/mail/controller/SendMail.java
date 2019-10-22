package com.flan.mail.controller;

import com.flan.mail.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMail {

    @Autowired
    private MailUtil mailUtil;

    @PostMapping("/sendmail")
    public String sendMail(@RequestParam String email) {
        String code = "验证码，可以使用 RandomStringUtils等工具设置";
        try {
            mailUtil.sendHtmlMail(email, code);
        } catch (Exception e) {
            e.printStackTrace();
            return "发送失败";
        }
        return "发送成功";
    }
}
