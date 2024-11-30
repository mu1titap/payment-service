package com.multitap.payment.api.application;

import com.multitap.payment.api.dto.in.SettlePointsDto;
import com.multitap.payment.common.entity.BaseResponse;
import com.multitap.payment.common.utils.RandomNumGenerator;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class SettlePointsServiceImpl implements SettlePointsService {

    private final UserServiceClient userServiceClient;
    private final JavaMailSender javaMailSender;
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Override
    public Boolean settlePoints(SettlePointsDto settlePointsDto) {
        BaseResponse<Boolean> response =
            userServiceClient.usePoints(settlePointsDto.getMentorUuid(),
                settlePointsDto.getPoints());

        return response.result();
    }

    @Override
    public void sendRandomNumber(String phoneNumber) {
        Integer numberLength = 6;
        String randomNumber = RandomNumGenerator.generateRandomNum(numberLength);

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, "kdh39278@naver.com");
            message.setSubject("이메일 인증");
            String body = "";
            body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
            body += "<h1>" + randomNumber + "</h1>";
            body += "<h3>" + "감사합니다." + "</h3>";
            message.setText(body, "UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        }

        javaMailSender.send(message);

        // save redis
        // todo make method
        String authKey = phoneNumber + "-authNum";
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(authKey, randomNumber);

    }

    @Override
    public Boolean checkRandomNumber(String phoneNumber, String insertedNumber) {

        String authKey = phoneNumber + "-authNum";
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String value = valueOperations.get(authKey);

        // 맞을 시 redis에서 삭제하는 로직 필요
        return value.equals(insertedNumber);
    }

}
