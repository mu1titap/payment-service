package com.multitap.payment.api.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QKakaoPay is a Querydsl query type for KakaoPay
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QKakaoPay extends EntityPathBase<KakaoPay> {

    private static final long serialVersionUID = -190828708L;

    public static final QKakaoPay kakaoPay = new QKakaoPay("kakaoPay");

    public final com.multitap.payment.common.entity.QBaseEntity _super = new com.multitap.payment.common.entity.QBaseEntity(this);

    public final StringPath cid = createString("cid");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath itemName = createString("itemName");

    public final StringPath partnerOrderId = createString("partnerOrderId");

    public final StringPath partnerUserId = createString("partnerUserId");

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public final NumberPath<Integer> taxFreeAmount = createNumber("taxFreeAmount", Integer.class);

    public final NumberPath<Integer> totalAmount = createNumber("totalAmount", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QKakaoPay(String variable) {
        super(KakaoPay.class, forVariable(variable));
    }

    public QKakaoPay(Path<? extends KakaoPay> path) {
        super(path.getType(), path.getMetadata());
    }

    public QKakaoPay(PathMetadata metadata) {
        super(KakaoPay.class, metadata);
    }

}

