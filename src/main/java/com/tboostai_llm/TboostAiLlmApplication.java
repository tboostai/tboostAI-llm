package com.tboostai_llm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TboostAiLlmApplication {

    public static void main(String[] args) {
        SpringApplication.run(TboostAiLlmApplication.class, args);
    }

}
