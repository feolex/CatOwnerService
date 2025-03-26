package ru.feolex.ControllerLayer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;

@EntityScan("ru.feolex.DaoLayer")
@EnableJpaRepositories("ru.feolex.DaoLayer")
@ComponentScan(basePackages = {"ru.feolex.ControllerLayer",
        "ru.feolex.SecurityLayer",
        "ru.feolex.ServiceLayer.Services",
        "ru.feolex.DaoLayer"})
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
