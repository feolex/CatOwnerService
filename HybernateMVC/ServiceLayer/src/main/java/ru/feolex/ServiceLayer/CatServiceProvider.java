package ru.feolex.ServiceLayer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.feolex.DaoLayer.Providers.CatDaoProvider;
import ru.feolex.ServiceLayer.Services.CatService;
import ru.feolex.ServiceLayer.Services.CatServiceInterface;

@Configuration
public class CatServiceProvider {
    @Bean
    public CatServiceInterface provide(){
        return new CatService(new CatDaoProvider().provide());
    }
}
