package ru.feolex.ServiceLayer.Providers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.feolex.ServiceLayer.Services.CatService;
import ru.feolex.ServiceLayer.Services.CatServiceInterface;

@Configuration
public class CatServiceProvider {
    @Bean(name = "CatProvider")
    public CatServiceInterface provide(){
        return new CatService();
    }
}
