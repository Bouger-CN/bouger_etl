package com.bouger.bouger_etl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class BougerEtlApplication {

    public static void main(String[] args) {
        SpringApplication.run(BougerEtlApplication.class, args);
    }

}
