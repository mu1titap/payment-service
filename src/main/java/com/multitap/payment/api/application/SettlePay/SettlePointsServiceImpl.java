package com.multitap.payment.api.application.SettlePay;

import com.multitap.payment.api.application.AuthServiceClient;
import com.multitap.payment.api.application.UserServiceClient;
import com.multitap.payment.api.dto.in.ExchangePointsDto;
import com.multitap.payment.common.Exception.BaseException;
import com.multitap.payment.common.entity.BaseResponse;
import com.multitap.payment.common.entity.BaseResponseStatus;
import com.multitap.payment.common.utils.MailConfigurer;
import com.multitap.payment.common.utils.RandomNumGenerator;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ListOperations;
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
    public Boolean settlePoints(ExchangePointsDto exchangePointsDto) {
        BaseResponse<Boolean> response =
            userServiceClient.usePoints(exchangePointsDto.getMentorUuid(),
                exchangePointsDto.getPoints());

        return response.result();
    }

    @Override
    public void sendRandomNumber(String userUuid) {

        // generate random number
        Integer numberLength = 6;
        String randomNumber = RandomNumGenerator.generateRandomNum(numberLength);

        // config content
        String toEmail = authServiceClient.getUserEmail(userUuid);  // auth service에서 Email 가져오기
        if (toEmail == null) {
            throw new BaseException(BaseResponseStatus.NO_EXIST_USER);
        }
        MimeMessage message = javaMailSender.createMimeMessage();
        message = MailConfigurer.configMimeMessage(message, senderEmail, toEmail, randomNumber);

        // send
        javaMailSender.send(message);

        // save redis
        String authKey = userUuid + "-authNum";
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(authKey, randomNumber, 180, java.util.concurrent.TimeUnit.SECONDS);

    }

    @Override
    public Boolean checkRandomNumber(String userUuid, String insertedNumber) {

        String authKey = userUuid + "-authNum";
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String value = valueOperations.get(authKey);

        ListOperations<String, String> valueOperationList = redisTemplate.opsForList();
        if (value.equals(insertedNumber)) {
            // 맞을 시 redis에서 key 삭제
            valueOperations.getOperations().delete(authKey);
            // verfied 된 유저 list에 추가하기
            valueOperationList.rightPush("verified", userUuid);
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void deleteRandomNumber(String userUuid) {
        String authKey = userUuid + "-authNum";
        redisTemplate.delete(authKey);
    }

    @Override
    public void deleteVerifiedUser(String userUuid) {
        ListOperations<String, String> valueOperationList = redisTemplate.opsForList();
        valueOperationList.remove("verified", 0, userUuid);
    }
}


