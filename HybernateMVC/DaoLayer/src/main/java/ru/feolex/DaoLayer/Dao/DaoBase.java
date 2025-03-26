package ru.feolex.DaoLayer.Dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@NoArgsConstructor
public abstract class DaoBase<T> implements DaoInterface<T> {
    @Autowired
    protected EntityManagerFactory entityManagerFactory;

    @Getter
    protected EntityManager entityManager;

    protected DaoBase(EntityManagerFactory entityManagerFactor) {
        this.entityManagerFactory = entityManagerFactor;
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    protected void setEntityManagerFactory(String persistenceUnitName) {
        entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
    }

    @Override
    public void save(T t) {
        executeInsideTransaction(entityManager -> entityManager.persist(entityManager.contains(t) ? t : entityManager.merge(t)));
    }

    @Override
    public void delete(T t) {
        executeInsideTransaction(entityManager -> entityManager.remove(entityManager.contains(t) ? t : entityManager.merge(t)));

        entityManager.getTransaction().begin();
        entityManager.flush();
        entityManager.clear();
        entityManager.getTransaction().commit();
//        entityManager.close();
    }

    protected void executeInsideTransaction(Consumer<EntityManager> work) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            work.accept(entityManager);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Override
    protected void finalize() {
        entityManager.close();
    }


}
