package ru.feolex.ServiceLayer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.feolex.DaoLayer.Providers.OwnerDaoProvider;
import ru.feolex.ServiceLayer.Services.OwnerService;
import ru.feolex.ServiceLayer.Services.OwnerServiceInterface;

@Configuration
public class OwnerServiceProvider {
    @Bean
    public OwnerServiceInterface provide(){
        return new OwnerService(new OwnerDaoProvider().provide());
    }
}
