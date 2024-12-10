package com.multitap.payment.api.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QVoltHistory is a Querydsl query type for VoltHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVoltHistory extends EntityPathBase<VoltHistory> {

    private static final long serialVersionUID = -2058545796L;

    public static final QVoltHistory voltHistory = new QVoltHistory("voltHistory");

    public final com.multitap.payment.common.entity.QBaseEntity _super = new com.multitap.payment.common.entity.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath menteeUuid = createString("menteeUuid");

    public final StringPath mentorUuid = createString("mentorUuid");

    public final StringPath sessionUuid = createString("sessionUuid");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Integer> volt = createNumber("volt", Integer.class);

    public QVoltHistory(String variable) {
        super(VoltHistory.class, forVariable(variable));
    }

    public QVoltHistory(Path<? extends VoltHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVoltHistory(PathMetadata metadata) {
        super(VoltHistory.class, metadata);
    }

}

