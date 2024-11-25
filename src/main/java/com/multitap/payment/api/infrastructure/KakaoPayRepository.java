package com.multitap.payment.api.infrastructure;

import com.multitap.payment.api.domain.KakaoPay;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KakaoPayRepository extends JpaRepository<KakaoPay, Long> {

    Optional<KakaoPay> findByCid(String cid);

    boolean existsByCid(String cid);
}
