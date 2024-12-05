package com.multitap.payment.api.domain;

import com.multitap.payment.api.domain.enum_file.BankCode;
import com.multitap.payment.api.domain.enum_file.ExchangeStatus;
import com.multitap.payment.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Exchange extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mentorUuid;
    private Integer volt;
    private String account;
    @Enumerated(EnumType.STRING)
    private ExchangeStatus status;
    @Enumerated(EnumType.STRING)
    private BankCode bankCode;


}
