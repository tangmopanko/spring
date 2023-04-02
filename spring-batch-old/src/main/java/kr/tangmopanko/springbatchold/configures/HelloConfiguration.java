package kr.tangmopanko.springbatchold.configures;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
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
        .build();
    }

    @Bean 
    public Step helloStep() {
        return stepBuilderFactory.get("HelloStep")
        .tasklet(((contribution, chunkContext) -> {
            log.info("Hello Step Spring Batch");
            return RepeatStatus.FINISHED;
            }
        )).build();
    }
}