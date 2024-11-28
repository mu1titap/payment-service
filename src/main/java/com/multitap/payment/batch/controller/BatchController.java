package com.multitap.payment.batch.controller;

import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/batch")
@RequiredArgsConstructor
public class BatchController {

    private final JobRegistry jobRegistry;
    private final JobLauncher jobLauncher;

    @GetMapping("/jpa/exchange")
    public void startExchangeBatch() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
            .addDate("date", new Date())    // for duplicate implement
            .toJobParameters();

        jobLauncher.run(jobRegistry.getJob("exchangeJpaJob"), jobParameters);

    }

}
