package com.isp.project.model;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendNotication(String email, String subject, String password) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText("""
                <div>
                    <p>Mật khẩu của bạn là: %s</p>
                    <p>đăng nhập lại tại:.</p>
                </div>
                """.formatted(password), true);
        javaMailSender.send(mimeMessage);
    }

    public static String extractDomain(String urlString) {
        try {
            URL url = new URL(urlString);

            String protocol = url.getProtocol();

            String host = url.getHost();

            int port = url.getPort();

            // Kiểm tra xem cổng có tồn tại không
            if (port != -1) {
                return protocol + "://" + host + ":" + port + "/";
            } else {
                return protocol + "://" + host + "/";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}