//package com.multitap.payment.api.common.config;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class BatchJobRunner implements CommandLineRunner {
//
//    private final JobLauncher jobLauncher;
//    private final Job simpleJob1;
//
//    public BatchJobRunner(JobLauncher jobLauncher, Job simpleJob1) {
//        this.jobLauncher = jobLauncher;
//        this.simpleJob1 = simpleJob1;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        System.out.println("Hello World from CommandLineRunner");
//        jobLauncher.run(simpleJob1, new JobParameters());
//    }
//
//
//}
