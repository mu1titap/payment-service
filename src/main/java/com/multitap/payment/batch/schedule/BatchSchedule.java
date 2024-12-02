package com.multitap.payment.batch.schedule;

import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class BatchSchedule {

    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;


    @Scheduled(cron = "*/10 * * * * *", zone = "Asia/Seoul")
    public void runExchangeJob() throws Exception {
        log.info("run Scheduler ");

        JobParameters jobParameters = new JobParametersBuilder()
            .addDate("date", new Date())    // for duplicate implement
            .toJobParameters();

        jobLauncher.run(jobRegistry.getJob("exchangeJpaJob"), jobParameters);
    }


}
