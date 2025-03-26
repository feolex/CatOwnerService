package ru.feolex;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ComponentScan(basePackages = {"ru.feolex.ServiceLayer.Services", "ru.feolex.DaoLayer", "ru.feolex.ControllerLayer"})
@PropertySource(value = {"classpath:application.properties"})
@Configuration
@EnableWebMvc
public class TestConfig implements WebMvcConfigurer { }
