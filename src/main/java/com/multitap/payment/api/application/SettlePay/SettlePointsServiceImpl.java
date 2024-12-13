package com.multitap.payment.api.application.SettlePay;


import com.multitap.payment.api.application.AuthServiceClient;
import com.multitap.payment.api.application.UserServiceClient;
import com.multitap.payment.api.common.PointConstants;
import com.multitap.payment.api.domain.Exchange;
import com.multitap.payment.api.domain.VoltHistory;
import com.multitap.payment.api.dto.in.ExchangePointsDto;
import com.multitap.payment.api.dto.out.ExchangeDto;
import com.multitap.payment.api.dto.out.ExchangeDtoList;
import com.multitap.payment.api.dto.out.VoltHistoryDto;
import com.multitap.payment.api.infrastructure.ExchangeRepository;
import com.multitap.payment.api.infrastructure.VoltHistoryRepository;
import com.multitap.payment.api.vo.out.VoltResponse;
import com.multitap.payment.common.Exception.BaseException;
import com.multitap.payment.common.entity.BaseResponse;
import com.multitap.payment.common.entity.BaseResponseStatus;
import com.multitap.payment.common.utils.MailConfigurer;
import com.multitap.payment.common.utils.RandomNumGenerator;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class SettlePointsServiceImpl implements SettlePointsService {

    private final UserServiceClient userServiceClient;
    private final VoltHistoryRepository voltHistoryRepository;
    private final ExchangeRepository exchangeRepository;
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
        if (!response.result()) {
            log.error("Failed to settle points. mentorUuid: {}, points: {}",
                exchangePointsDto.getMentorUuid(), exchangePointsDto.getPoints());
            // Rollback
            // todo rollback client 다시 만들기
            // usePoints 의 내부 로직 모르는 상태에서 너무 막연한 방법
            userServiceClient.usePoints(exchangePointsDto.getMentorUuid(),
                exchangePointsDto.getPoints() * (-1));
            return false;
        }

        exchangeRepository.save(exchangePointsDto.toEntity());

        return true;
    }

    @Override
    public ExchangeDto getExchange(String startDate, String endDate, String mentorUuid) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate startLocalDate;
        LocalDate endLocalDate;
        // 값이 안 들어올 경우 처리
        if (startDate == null || endDate == null) {
            // 오늘 부터 한 달 전으로
            endLocalDate = LocalDate.now();
            startLocalDate = LocalDate.now().minusMonths(1);

        } else {
            startLocalDate = LocalDate.parse(startDate, formatter);
            endLocalDate = LocalDate.parse(endDate, formatter);
        }

        LocalDateTime startDateTime = startLocalDate.atStartOfDay();
        LocalDateTime endDateTime = endLocalDate.atTime(23, 59, 59);

        log.info("startDateTime {}", startDateTime);
        log.info("endDateTime {}", endDateTime);

        List<Exchange> exchangeList =
            exchangeRepository.findByMentorUuidAndCreatedAtBetween(mentorUuid, startDateTime,
                endDateTime);

        log.info("exchangeList {}", exchangeList.toString());

        Integer exchangeAmount = exchangeList.stream().map(Exchange::getVolt)
            .mapToInt(Integer::intValue).sum();

        List<ExchangeDtoList> exchangePointsDtoList = exchangeList.stream()
            .map(exchange -> ExchangeDtoList.builder()
                .id(exchange.getId())
                .volt(exchange.getVolt())
                .date(exchange.getCreatedAt().toLocalDate())
                .money((int) (exchange.getVolt() * PointConstants.PRICE_PER_POINT
                    * PointConstants.COMMISION_RATE))
                .status(exchange.getStatus())
                .build())
            .toList();

        return ExchangeDto.builder()
            .totalExchange(exchangeAmount)
            .exchangeList(exchangePointsDtoList)
            .build();


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
        // 유저에 해당하는 인증번호용 redis에 저장
        String authKey = userUuid + "-authNum";
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(authKey, randomNumber, 180, java.util.concurrent.TimeUnit.SECONDS);

    }

    @Override
    public Boolean checkRandomNumber(String userUuid, String insertedNumber) {
        log.info("userUuid: {}, insertedNumber: {}", userUuid, insertedNumber);
        String authKey = userUuid + "-authNum";
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String value = valueOperations.get(authKey);

        if (value == null) { // 여러 번 요청해서 redis에서 value가 사라진 case.. 고려
            return false;
        }

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

    @Transactional(readOnly = true)
    @Override
    public VoltHistoryDto getVoltHistory(String mentorUuid) {

        Integer voltAmount = 0;

        // 회원 볼트 결제 내역을 통해 멘토가 받은 볼트 총량을 계산
        List<VoltHistory> voltHistoryList = voltHistoryRepository.findByMentorUuid(
            mentorUuid);
        if (voltHistoryList != null) {
            for (VoltHistory voltHistory : voltHistoryList) {
                voltAmount += voltHistory.getVolt();
            }
        }

        // todo logic 잘못됨.. member service에서 amount를 다 가지고 있어야 함
        List<Exchange> exchangeList = exchangeRepository.findByMentorUuid(mentorUuid);

        // 환전 한 만큼 총량 마이너스
        if (exchangeList != null) {
            for (Exchange exchange : exchangeList) {
                voltAmount -= exchange.getVolt();
            }
        }

        List<VoltResponse> voltResponseList =
            new ArrayList<>(voltHistoryList.stream().map(voltHistory ->
                    VoltResponse.builder()
                        .id(voltHistory.getId())
                        .volt(voltHistory.getVolt())
                        .date(voltHistory.getCreatedAt())
                        .sender(voltHistory.getMenteeUuid())
                        .build())
                .toList());

        // 어떤 기준으로 정렬하는지 협업하는 입장에서 찾아들어가봐야 할 수 있다
//        Collections.reverse(voltResponseList);

        VoltHistoryDto voltHistoryDto = new VoltHistoryDto();
        voltHistoryDto.setVoltResponse(voltAmount, voltResponseList);

        return voltHistoryDto;

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

