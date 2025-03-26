package ru.feolex.DaoLayer.Dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class UserExampleRepositoryImpl implements AdditionalMethodsForRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean summirizeAllComments() {
        return entityManager != null;
    }
}
