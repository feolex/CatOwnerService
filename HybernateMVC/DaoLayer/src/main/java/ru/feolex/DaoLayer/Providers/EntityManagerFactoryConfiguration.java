package ru.feolex.DaoLayer.Providers;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@NoArgsConstructor
public class EntityManagerFactoryConfiguration {
    @Bean
    public EntityManagerFactory provide(){
        return Persistence.createEntityManagerFactory("DaoLayer.jpa");
    }
}
