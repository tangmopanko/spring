package kr.tangmopanko.springbatchold.configures;

import org.springframework.batch.core.StepExecution;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class HelloConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public HelloConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job helloJob() {
        return jobBuilderFactory.get("HelloJob")
        .incrementer(new RunIdIncrementer())
        .start(this.helloStep())
        .next(this.sharedStep())
        .build();
    }
    
    @Bean 
    public Step helloStep() {
        return stepBuilderFactory.get("HelloStep")
        .tasklet(((contribution, chunkContext) -> {
            log.info("Hello Step Spring Batch");
        
            /// shared parameters.. 
            StepExecution stepExecution = contribution.getStepExecution();
            ExecutionContext executionContext = stepExecution.getExecutionContext();
            executionContext.putString("step1", "Step executionContext.putString: setup parameter");            
            
            JobExecution jobExecution = stepExecution.getJobExecution();
            JobInstance jobInstance = jobExecution.getJobInstance();
            ExecutionContext jobExecutionContext = jobExecution.getExecutionContext();
            jobExecutionContext.putString("job1", "Job executionContext.putString: setup parameter");     
            
            JobParameters jobParameter = jobExecution.getJobParameters();       

            log.info("jobName: {} , stepName: {} , run.id: {}", 
                jobInstance.getJobName(), stepExecution.getStepName(), jobParameter.getLong("run.id")
            );
            
            return RepeatStatus.FINISHED;
            }
        )).build();
    }

    @Bean 
    public Step sharedStep() {
        return stepBuilderFactory.get("SharedStep")
        .tasklet(((contribution, chunkContext) -> {
            log.info("Hello Step Spring Batch");
        
            /// shared parameters.. 
            StepExecution stepExecution = contribution.getStepExecution();
            ExecutionContext executionContext = stepExecution.getExecutionContext();
            
            JobExecution jobExecution = stepExecution.getJobExecution();
            ExecutionContext jobExecutionContext = jobExecution.getExecutionContext();
            
            log.info("jobKey: {} , stepKey: {}" ,
                jobExecutionContext.getString("job1", "empty"),
                executionContext.getString("step1", "empty")

            );
            
            return RepeatStatus.FINISHED;
            }
        )).build();
    }
}