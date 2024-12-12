package com.multitap.payment.api.kafka;

import com.multitap.payment.api.kafka.messageIn.SessionConfirmedDto;

public interface KafkaConsumerService {

    void updateVoltHistoryStatus(SessionConfirmedDto sessionConfirmedDto);
}
