//package com.multitap.payment.batch;
//
//import com.multitap.payment.api.infrastructure.ExchangeRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//@Slf4j
//@RequiredArgsConstructor
//@Configuration
//public class SimpleJobConfiguration {
//
//
//    private final JobRepository jobRepository;
//    private final ExchangeRepository exchangeRepository;
//
//    @Bean
//    public Job simpleJob() {
//        return new JobBuilder("simpleJob", jobRepository)
//            .start(simpleStep1())
//            .build();
//    }
//
//    @Bean
//    public Step simpleStep1(DataSourceTransactionManager transactionManager) {
//
//        return new StepBuilder("simpleStep1", jobRepository)
//            .tasklet((contribution, chunkContext) -> {
//                log.info("Step1!!!");
//                return RepeatStatus.FINISHED;
//            }, transactionManager)
//            .build();
//
//    }
//
////    @Bean
////    public RepositoryItemReader<ExchangeCountDto> exchangeReader() {
////        log.info("wishReader!!!");
////        return new RepositoryItemReaderBuilder<ExchangeCountDto>()
////            .name("exchangeReader")
////            .repository(exchangeRepository)
////            .methodName("findAll")
////            .pageSize(3)
////            .build();
////    }
//
//
//}
