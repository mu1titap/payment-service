package com.multitap.payment.api.application;

import com.multitap.payment.api.dto.in.SessionPaymentDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SessionPaymentServiceImpl implements SessionPaymentService {

    private final UserServiceClient userServiceClient;


    @Override
    public void paySession(SessionPaymentDto sessionPaymentDto) {
        try {
            userServiceClient.usePoints(sessionPaymentDto.getUserUuid(),
                sessionPaymentDto.getPointPrice());
        } catch (Exception e) {
            throw new RuntimeException("Failed to use points for session", e);
        }


    }


}
