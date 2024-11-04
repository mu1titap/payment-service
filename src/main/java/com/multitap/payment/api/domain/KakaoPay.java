package com.multitap.payment.api.domain;

import com.multitap.payment.api.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@Setter
public class KakaoPay extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String partnerOrderId;
    @Column(nullable = false)
    private String partnerUserId;
    @Column(nullable = false)
    private String cid;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private String itemName;
    @Column(nullable = false)
    private Integer totalAmount;
    @Column(nullable = false)
    private Integer taxFreeAmount;


}
