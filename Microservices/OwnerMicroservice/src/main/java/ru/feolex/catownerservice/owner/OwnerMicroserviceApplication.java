package com.catownerservice.owner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OwnerMicroserviceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OwnerMicroserviceApplication.class, args);
    }
} 