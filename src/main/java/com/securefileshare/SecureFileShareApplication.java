package com.securefileshare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.cloud.client.discovery.KubernetesDiscoveryClientAutoConfiguration;

//@SpringBootApplication(exclude = KubernetesDiscoveryClientAutoConfiguration.class)
@SpringBootApplication()
@EnableJpaRepositories(basePackages = "com.securefileshare.repository")

public class SecureFileShareApplication {

    public static void main(String[] args) {
        // Start the Spring Boot application
        SpringApplication.run(SecureFileShareApplication.class, args);
    }
}

