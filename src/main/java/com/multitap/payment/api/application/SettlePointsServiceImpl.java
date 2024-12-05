package com.multitap.payment.api.application;

import com.multitap.payment.api.dto.in.SettlePointsDto;
import com.multitap.payment.common.entity.BaseResponse;
import com.multitap.payment.common.utils.MailConfigurer;
import com.multitap.payment.common.utils.RandomNumGenerator;
import jakarta.mail.internet.MimeMessage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class SettlePointsServiceImpl implements SettlePointsService {

    private final UserServiceClient userServiceClient;
    private final AuthServiceClient authServiceClient;
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
    public void sendRandomNumber(String userUuid) {

        // get Email
        // todo insert instead
//        String userEmail = authServiceClient.getUserEmail(userUuid);

        // generate random number
        Integer numberLength = 6;
        String randomNumber = RandomNumGenerator.generateRandomNum(numberLength);

        // config content
        String toEmail = "kdh39278@naver.com";
        MimeMessage message = javaMailSender.createMimeMessage();
        message = MailConfigurer.configMimeMessage(message, senderEmail, toEmail, randomNumber);

        // send
        javaMailSender.send(message);

        // save redis
        // todo make method
        String authKey = userUuid + "-authNum";
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(authKey, randomNumber, 180, java.util.concurrent.TimeUnit.SECONDS);

    }

    @Override
    public Boolean checkRandomNumber(String userUuid, String insertedNumber) {

        String authKey = userUuid + "-authNum";
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String value = valueOperations.get(authKey);

        ValueOperation<String, List<String>> valueOperationList = redisTemplate.opsForList();
        if (value.equals(insertedNumber)) {
            // 맞을 시 redis에서 key 삭제
            valueOperations.getOperations().delete(authKey);
            // verfied 된 유저 추가하기
            valueOperations.set("verified",  )
            return true;
        }
        else{
            return false;
            }
        }


    }

}
