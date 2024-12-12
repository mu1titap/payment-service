package com.multitap.payment.api.domain;

import com.multitap.payment.api.domain.enum_file.PaymentStatus;
import com.multitap.payment.api.kafka.messageIn.SessionConfirmedDto;
import com.multitap.payment.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@DynamicInsert
public class VoltHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String menteeUuid;
    @Column(length = 50)
    @Description("volt 받는 대상")
    private String mentorUuid;
    @Column(length = 50)
    private String sessionUuid;
    @Column(name = "volt")
    private Integer volt;
    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    @ColumnDefault("PENDING")
    private PaymentStatus paymentStatus;


    public void updatePaymentStatus(SessionConfirmedDto dto) {
        if (dto.getSessionIsConfirmed()) {
            this.paymentStatus = PaymentStatus.COMPLETED;
            this.mentorUuid = dto.getMentorUuid();  // null update
        }
    }
}
