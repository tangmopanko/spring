package kr.tangmopanko.springbatchold.configures;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.github.javafaker.Faker;

import kr.tangmopanko.springbatchold.dto.Beer;
import kr.tangmopanko.springbatchold.dto.Person;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class CustomReaderConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public CustomReaderConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean 
    public Job itemReaderJob() throws Exception{
        return this.jobBuilderFactory.get("ItemReaderJob")
            .incrementer(new RunIdIncrementer())
            .start(this.customItemReaderStep())
            .next(this.csvTypeBeerReaderStep())
            .build();
    }
    @Bean
    public Step csvTypeBeerReaderStep() throws Exception {
        return stepBuilderFactory.get("CsvTypeBeerrrrItemReaderStep")
            .<Beer, Beer>chunk(10)
            .reader(this.csvFilerItemReader())
            //.reader(new ListItemReader<>(getItem())) origin
            .writer(beerWriter())
            .faultTolerant()
            .skip(FlatFileParseException.class)
            .skipLimit(10)
            .build();
    }

    @Bean
    public ItemWriter<Beer> beerWriter() {
        return items -> log.info(items.toString());
    }

    @Bean
    public FlatFileItemReader<Beer> csvFilerItemReader() throws Exception {
        DefaultLineMapper<Beer> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames("sidoNm", "name", "address", "beerType", "desc", "naverUrl", "homepage", "x", "y");
        lineMapper.setLineTokenizer(tokenizer);

        lineMapper.setFieldSetMapper(fieldSet -> {
            String sidoNm = fieldSet.readString("sidoNm");
            String name = fieldSet.readString("name");
            String address = fieldSet.readString("address");
            String beerType = fieldSet.readString("beerType");
            String desc = fieldSet.readString("desc");
            
            String naverUrl = fieldSet.readString("naverUrl");
            String homepage = fieldSet.readString("homepage");
            String x, y;
            x = fieldSet.readString("x");
            y = fieldSet.readString("y");
             
            return new Beer(sidoNm, name, address, beerType, desc, naverUrl, homepage, x, y); 

        });

        FlatFileItemReader<Beer> itemReader = new FlatFileItemReaderBuilder<Beer>()
        .name("CsvFileItemReader")
        .encoding("UTF-8")
        .resource(new ClassPathResource("beer.csv"))
        .linesToSkip(1)
        .lineMapper(lineMapper)
        .build();
        itemReader.afterPropertiesSet();
        
        return itemReader;
        
    }

    // customer 

    @Bean
    public Step customItemReaderStep() {
        return stepBuilderFactory.get("CustomItemReaderStep")
            .<Person, Person>chunk(10)
            .reader(new CustomItemReader<>(getItem()))
            //.reader(new ListItemReader<>(getItem())) origin
            .writer(itemWriter())
            .build();
    }

    private ItemWriter<Person> itemWriter() {
        return items -> log.info(items.stream()
            .map(Person::getName)
            .collect(Collectors.joining(", ")));
    }

    private List<Person> getItem() {
        Faker faker = new Faker();
        List<Person> results = new ArrayList<>();

        for(int idx = 0 ; idx < 20 ; idx++) {
            results.add(new Person(idx, faker.name().fullName(), faker.number().digits(2), faker.address().fullAddress())); 
        }

        return results;
    }

}