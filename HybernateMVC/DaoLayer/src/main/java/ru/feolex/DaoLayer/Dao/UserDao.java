package ru.feolex.DaoLayer.Dao;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Component;
import ru.feolex.DaoLayer.Entities.UserExample;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class UserDao extends DaoBase<UserExample> {

    public UserDao(EntityManagerFactory entityManagerFactor) {
        super(entityManagerFactor);
    }

    @Override
    public Optional<UserExample> get(Long id) {
        return Optional.ofNullable(entityManager.find(UserExample.class, id));
    }

    @Override
    public Collection<UserExample> getAll() {
        return entityManager.createQuery("FROM UserExample ").getResultList();
    }

    @Override
    public void update(UserExample user, List<String> params) {
        UserExample managedUser = entityManager.find(UserExample.class, user.getId());
        managedUser.setName(params.get(0));
        managedUser.setComment(params.get(1));

        executeInsideTransaction(entityManager -> entityManager.merge(managedUser));

    }
}
