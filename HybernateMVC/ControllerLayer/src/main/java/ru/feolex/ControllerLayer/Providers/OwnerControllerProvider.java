package ru.feolex.ControllerLayer.Providers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.feolex.ControllerLayer.Controllers.OwnerController;
import ru.feolex.ControllerLayer.Controllers.OwnerControllerInterface;
import ru.feolex.ServiceLayer.OwnerServiceProvider;

@Configuration
public class OwnerControllerProvider {
    @Bean
    public OwnerControllerInterface provide(){
        return new OwnerController(new OwnerServiceProvider().provide());
    }
}