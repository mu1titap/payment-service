package com.multitap.payment.api.infrastructure;

import com.multitap.payment.api.domain.MemberVoltAmountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberVoltAmountRepository extends JpaRepository<MemberVoltAmountEntity, Long> {

}
