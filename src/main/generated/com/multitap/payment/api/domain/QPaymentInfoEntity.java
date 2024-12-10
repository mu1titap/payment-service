package com.multitap.payment.api.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPaymentInfoEntity is a Querydsl query type for PaymentInfoEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPaymentInfoEntity extends EntityPathBase<PaymentInfoEntity> {

    private static final long serialVersionUID = -216189888L;

    public static final QPaymentInfoEntity paymentInfoEntity = new QPaymentInfoEntity("paymentInfoEntity");

    public final com.multitap.payment.common.entity.QBaseEntity _super = new com.multitap.payment.common.entity.QBaseEntity(this);

    public final NumberPath<Integer> cash = createNumber("cash", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath menteeUuid = createString("menteeUuid");

    public final NumberPath<Long> refundId = createNumber("refundId", Long.class);

    public final EnumPath<com.multitap.payment.api.common.enums.PaymentType> type = createEnum("type", com.multitap.payment.api.common.enums.PaymentType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Integer> volt = createNumber("volt", Integer.class);

    public QPaymentInfoEntity(String variable) {
        super(PaymentInfoEntity.class, forVariable(variable));
    }

    public QPaymentInfoEntity(Path<? extends PaymentInfoEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPaymentInfoEntity(PathMetadata metadata) {
        super(PaymentInfoEntity.class, metadata);
    }

}

