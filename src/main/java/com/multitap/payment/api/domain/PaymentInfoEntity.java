package com.multitap.payment.api.domain;


import com.multitap.payment.api.common.enums.PaymentType;
import com.multitap.payment.api.dto.out.PaymentResponseDto;
import com.multitap.payment.common.entity.BaseEntity;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NamedNativeQuery;

// native query 사용(unionAll 지원) + dto mapping 위해
@NamedNativeQuery(
    name = "payment_info_dto",
    query = " SELECT p.volt, p.created_at AS createdAt, 'payment_info' AS sourceTable "
        + " FROM payment_info p "
        + " WHERE p.mentee_uuid = :menteeUuid "
        + " UNION ALL "
        + " SELECT v.volt, v.created_at AS createdAt, 'volt_history' AS sourceTable "
        + " FROM volt_history v "
        + " WHERE v.mentee_uuid = :menteeUuid"
        + " ORDER BY CASE WHEN :criteria = 'DESC' THEN createdAt END DESC," // order by > limit 순서
        + "          CASE WHEN :criteria = 'ASC' THEN  createdAt END ASC",  // case 별 다른 정렬 기준
    resultSetMapping = "payment_info_dto"
)
@SqlResultSetMapping(
    name = "payment_info_dto",
    classes = @ConstructorResult(
        targetClass = PaymentResponseDto.class,
        columns = {
            @ColumnResult(name = "volt", type = Integer.class),
            @ColumnResult(name = "createdAt", type = LocalDateTime.class),
            @ColumnResult(name = "sourceTable", type = String.class)
        }
    )
)
@NamedNativeQuery(
    name = "payment_info_count",
    query = "SELECT COUNT(*) FROM ( "
        + "SELECT 1 "
        + " FROM payment_info p "
        + " WHERE p.mentee_uuid = :menteeUuid "
        + " UNION ALL "
        + " SELECT 1 "
        + " FROM volt_history v "
        + " WHERE v.mentee_uuid = :menteeUuid ) AS combined_data"
)
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
