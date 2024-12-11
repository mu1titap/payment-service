package com.multitap.payment.common.utils;

import jakarta.mail.internet.MimeMessage;
import org.springframework.messaging.MessagingException;

public class MailConfigurer {

    public static MimeMessage configMimeMessage(MimeMessage message, String senderEmail,
        String toEmail, String randomNumber) {

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, toEmail);
            message.setSubject("이메일 인증");
            String body = "";
            body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
            body += "<h1>" + randomNumber + "</h1>";
            body += "<h3>" + "3분 안에 입력해주세요 </h3>";
            body += "<h3>" + "감사합니다." + "</h3>";
            message.setText(body, "UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
        return message;
    }

}
