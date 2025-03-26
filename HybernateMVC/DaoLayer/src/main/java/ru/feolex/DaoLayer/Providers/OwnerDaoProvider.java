package ru.feolex.DaoLayer.Providers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.feolex.DaoLayer.Dao.OwnerDao;
import ru.feolex.DaoLayer.Dao.OwnerDaoInterface;

@Configuration
public class OwnerDaoProvider {
    @Bean
    public OwnerDaoInterface provide(){
        return new OwnerDao(new EntityManagerFactoryConfiguration().provide());
    }
}
