package com.multitap.payment.api.domain;

import com.multitap.payment.api.common.enums.PaymentType;
import com.multitap.payment.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment_info")
@Entity
@Getter
public class PaymentInfoEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String menteeUuid;
    private Integer volt;
    @Enumerated(EnumType.STRING)
    private PaymentType type;
    private Integer cash;
    private Long refundId;


}
