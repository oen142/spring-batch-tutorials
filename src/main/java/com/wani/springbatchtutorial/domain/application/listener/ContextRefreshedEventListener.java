package com.wani.springbatchtutorial.domain.application.listener;

import java.util.Date;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
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
    private final JobRepository jobRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Stop running jobs");
        jobExplorer.getJobNames().forEach(this::extracted);
    }

    private void extracted(String jobName) {
        Set<JobExecution> runningJobExecutions = jobExplorer
            .findRunningJobExecutions(jobName);

        runningJobExecutions.forEach(this::extracted);
    }

    private void extracted(JobExecution r) {
        r.setStatus(BatchStatus.STOPPED);
        r.setEndTime(new Date());
        for (StepExecution stepExecution : r.getStepExecutions()) {
            extracted(stepExecution);
        }
        jobRepository.update(r);
    }

    private void extracted(StepExecution stepExecution) {
        if (stepExecution.getStatus().isRunning()) {
            stepExecution.setStatus(BatchStatus.STOPPED);
            stepExecution.setEndTime(new Date());
            jobRepository.update(stepExecution);
        }
    }
}
