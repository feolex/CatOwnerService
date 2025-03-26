package ru.feolex.DaoLayer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.feolex.DaoLayer.Dao.CatDao;
import ru.feolex.DaoLayer.Dao.OwnerDao;

@Configuration
@Import({MockEntityManagerFactoryConfiguration.class})
@ComponentScan({"ru.feolex"})
public class Repo {
    public static ApplicationContext getContext() {
        return new AnnotationConfigApplicationContext(Repo.class);
    }

    public static OwnerDao getOwnerDao() {
        return getContext().getBean(OwnerDao.class);
    }

    public static CatDao getCatDao() {
        return getContext().getBean(CatDao.class);
    }
}