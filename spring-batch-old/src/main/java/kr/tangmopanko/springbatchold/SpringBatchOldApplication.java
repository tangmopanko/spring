package kr.tangmopanko.springbatchold;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class SpringBatchOldApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchOldApplication.class, args);
	}

}
