package com.multitap.payment.batch;

import com.multitap.payment.api.domain.Exchange;
import com.multitap.payment.api.infrastructure.ExchangeRepository;
import com.multitap.payment.batch.entity.ExchangeLog;
import com.multitap.payment.batch.entity.ExchangeLogStatus;
import com.multitap.payment.batch.repository.ExchangeLogRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class ExchangeJpaBatch {
    // make thing to do mendatory

    // jpa vs jdbc => 실행시간 비교

    // category kafka 되는지

    // mongodb 연결
    

    // jpa repo vs crudrepo
    // empty method to implement later
    // page<>
    // SELECT NEW
    // writer return why?
    // method builder
    // responsebody
    // Enum type db 저장 방식..
    // test code
    // Page<> vs List<> why?
    // ItemWriter <> 랑 item 맞춰줘야 함?
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final ExchangeRepository exchangeRepository;
    private final ExchangeLogRepository exchangeLogRepository;

    @Bean
    public Job exchangeJpaJob() {
        return new JobBuilder("exchangeJpaJob", jobRepository)
            .start(exchangeJpaStep())
            .build();
    }


    @Bean
    public Step exchangeJpaStep() {
        return new StepBuilder("exchangeJpaStep", jobRepository)
            .<Exchange, Exchange>chunk(2, platformTransactionManager)
            .reader(exchangeJpaReader())
            .writer(exchangeJpaWriter())
            .allowStartIfComplete(true) // 중복 실행 허용
            .build();
    }

    @Bean
    public RepositoryItemReader<Exchange> exchangeJpaReader() {
        return new RepositoryItemReaderBuilder<Exchange>()
            .name("exchangeReader")
            .repository(exchangeRepository)
            .methodName("findManyThing")
            .pageSize(2)
            .sorts(Map.of("id", Sort.Direction.ASC))
            .build();
    }

    @Bean
    public ItemProcessor<Exchange, Exchange> exchangeProcessor() {
        // TODO send money to user
        return ExchangeBatchDto -> ExchangeBatchDto;
    }

    @Bean
    public ItemWriter<Exchange> exchangeJpaWriter() {
        return items -> {
            for (Exchange exchange : items) {
                log.info("exchange in itemWriter : {}", exchange.toString());
                exchangeLogRepository.save(ExchangeLog.builder()
                    .exchangeId(exchange.getId())   // 중복 실행 되었을 때의 저장 문제?
                    .status(ExchangeLogStatus.SUCCESS)
                    .build());
            }
        };
    }
}
