package ru.feolex.ServiceLayer.Providers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.feolex.ServiceLayer.Services.OwnerService;
import ru.feolex.ServiceLayer.Services.OwnerServiceInterface;

@Configuration
public class OwnerServiceProvider {
    @Bean(name = "OwnerProvider")
    public OwnerServiceInterface provide(){
        return new OwnerService();
    }
}
