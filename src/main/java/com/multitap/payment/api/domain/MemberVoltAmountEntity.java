package com.multitap.payment.api.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

@Table(name = "member_volt_amount")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberVoltAmountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Comment("회원 uuid")
    private String uuid;
    @ColumnDefault("0")
    private Integer amount;

}
