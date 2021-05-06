package com.wani.springbatchtutorial;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class SpringBatchTutorialApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchTutorialApplication.class, args);
    }

}
