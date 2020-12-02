package uk.co.kafka.producer.kafkaproducer.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class DBInitializer {

    private static final Logger log = LoggerFactory.getLogger(DBInitializer.class);

    @Bean
    public CommandLineRunner dbinit(){
        return args -> {
            log.info("log1");
            log.info("log2");
        };
    }

}

