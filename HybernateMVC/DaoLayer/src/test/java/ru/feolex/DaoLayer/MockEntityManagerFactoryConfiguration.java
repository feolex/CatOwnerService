package ru.feolex.DaoLayer;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MockEntityManagerFactoryConfiguration {
    @Bean
    public EntityManagerFactory provide(){
        return Persistence.createEntityManagerFactory("ru.feolex.DaoLayer.DaoTests.jpa");
    }
}
