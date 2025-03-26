package ru.feolex.DaoLayer.Providers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.feolex.DaoLayer.Dao.CatDao;
import ru.feolex.DaoLayer.Dao.CatDaoInterface;

@Configuration
public class CatDaoProvider {
    @Bean
    public CatDaoInterface provide(){
        return new CatDao(new EntityManagerFactoryConfiguration().provide());
    }
}
