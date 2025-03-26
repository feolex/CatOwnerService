package ru.feolex.ControllerLayer.Providers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.feolex.ControllerLayer.Controllers.CatController;
import ru.feolex.ControllerLayer.Controllers.CatControllerInterface;
import ru.feolex.ServiceLayer.CatServiceProvider;

@Configuration
public class CatControllerProvider {
    @Bean
    public CatControllerInterface provide(){
        return new CatController(new CatServiceProvider().provide());
    }
}
