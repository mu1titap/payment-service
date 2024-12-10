package com.multitap.payment.api.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QExchange is a Querydsl query type for Exchange
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExchange extends EntityPathBase<Exchange> {

    private static final long serialVersionUID = 952002714L;

    public static final QExchange exchange = new QExchange("exchange");

    public final com.multitap.payment.common.entity.QBaseEntity _super = new com.multitap.payment.common.entity.QBaseEntity(this);

    public final StringPath account = createString("account");

    public final EnumPath<com.multitap.payment.api.common.enums.BankCode> bankCode = createEnum("bankCode", com.multitap.payment.api.common.enums.BankCode.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath mentorUuid = createString("mentorUuid");

    public final EnumPath<com.multitap.payment.api.common.enums.ExchangeStatus> status = createEnum("status", com.multitap.payment.api.common.enums.ExchangeStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Integer> volt = createNumber("volt", Integer.class);

    public QExchange(String variable) {
        super(Exchange.class, forVariable(variable));
    }

    public QExchange(Path<? extends Exchange> path) {
        super(path.getType(), path.getMetadata());
    }

    public QExchange(PathMetadata metadata) {
        super(Exchange.class, metadata);
    }

}

