package ru.feolex.ControllerLayer.Providers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.feolex.ControllerLayer.Controllers.MainController;
import ru.feolex.ControllerLayer.Controllers.MainControllerInterface;

@Configuration
public class MainControllerProvider {
    @Bean
    public MainControllerInterface provide(){
        return new MainController(new CatControllerProvider().provide(), new OwnerControllerProvider().provide());
    }
}
