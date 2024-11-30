package com.multitap.payment.api.application;

import com.multitap.payment.api.dto.in.MemberVoltAmountDto;
import com.multitap.payment.api.infrastructure.MemberVoltAmountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MemberVoltAmountServiceImpl implements MemberVoltAmountService {

    private final MemberVoltAmountRepository memberVoltAmountRepository;


    @Override
    public void saveMemberVoltAmount(MemberVoltAmountDto memberVoltAmountDto) {
        memberVoltAmountRepository.save(memberVoltAmountDto.toEntity());
    }
}
