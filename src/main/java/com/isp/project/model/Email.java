package com.isp.project.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;

public class Email {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    HttpSession session;
    @Autowired
    private TemplateEngine templateEngine;

    public void sendEmail(String to, String password) throws MessagingException {
        session.setMaxInactiveInterval(5 * 60);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Mật khẩu tài khoản của bạn từ Sarine");
        Context context = new Context();
        context.setVariable("password", password);
        String htmlMsg = templateEngine.process("sendEmail", context);
        helper.setText(htmlMsg, true);

        mailSender.send(message);

    }

}
