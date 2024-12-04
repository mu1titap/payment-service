package com.multitap.payment.api.domain;

import com.multitap.payment.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
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
}
