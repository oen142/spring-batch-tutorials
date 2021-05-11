package com.wani.springbatchtutorial.domain.application.listener;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

    private final JobExplorer jobExplorer;
    private final JobRepository jobREpository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        jobExplorer.getJobNames().forEach(
            jobName -> {
                Set<JobExecution> runningJobExecutions = jobExplorer
                    .findRunningJobExecutions(jobName);

                runningJobExecutions.forEach(r -> {
                    System.out.println("r.get = " + r.get);
                });
            }
        );
    }
}
