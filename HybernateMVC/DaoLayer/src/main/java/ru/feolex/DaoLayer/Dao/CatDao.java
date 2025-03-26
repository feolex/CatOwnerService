package ru.feolex.DaoLayer.Dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Component;
import ru.feolex.DaoLayer.Entities.Cat;
import ru.feolex.DaoLayer.Entities.CatColor;
import ru.feolex.DaoLayer.Entities.Owner;
import ru.feolex.DaoLayer.Exceptions.UpdateArgsException;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class CatDao extends DaoBase<Cat> implements CatDaoInterface {
    private static final Integer UPD_ARGS_COUNT = 5; /// minimal count of List<String> params in update()

    private CatColorDaoInterface colorDao;
    public CatDao(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
        colorDao = new CatColorDao(this.entityManagerFactory);
    }

    public CatDao(EntityManagerFactory entityManagerFactory, CatColorDaoInterface ccdi) {
        super(entityManagerFactory);
        colorDao = ccdi;
    }

    @Override
    public Optional<Cat> get(Long id) {
        Optional<Cat> result = Optional.ofNullable(entityManager.find(Cat.class, id));
        if (result.isEmpty()) {
            result = Optional.ofNullable(entityManager.find(Cat.class, id));
        }

        return result;
    }

    @Override
    public Collection<Cat> getAll() {
        return entityManager.createQuery("FROM Cat").getResultList();
    }

    /**
     * Strong order for String in "params"
     * /// Min_size of params is 5
     * /// All parameters are String except notated as parametr_name(Long)
     * /// Cat_name, Birthdate, Breed, Color, OwnerId(Long), CatFriend1Id(Long), CatFriend2id(Long), ... CatFriendNId(Long)
     **/
    @Override
    public void update(Cat unmanagedCat, List<String> params) throws UpdateArgsException {

        if (params.size() < UPD_ARGS_COUNT) {
            throw new UpdateArgsException("Too small arguments in provided list of params to update Cat! Min size is " + UPD_ARGS_COUNT + "! Provided list size:" + String.valueOf(params.size()) + "Provided list is: ", params);
        }
        Cat cat = entityManager.find(Cat.class, unmanagedCat.getId());

        cat.setName(Objects.requireNonNull(params.get(0), "Name cannot be null"));
        cat.setBirthDate(Objects.requireNonNull(params.get(1), "Birthdate cannot be null"));
        cat.setBreed(params.get(2));

        Optional<CatColor> color = colorDao.isContainsColor(params.get(3));
        if(color.isPresent()){
            cat.setColor(color.get());
        }else{
            cat.setColor(null);
        }

        Long ownerId = Long.parseLong(Objects.requireNonNull(params.get(4), "OwnerId cannot be null!"));
        cat.setOwner(entityManager.getReference(Owner.class, ownerId));

        Set<Cat> cat_friends = null;
        if (params.size() != UPD_ARGS_COUNT) {
            cat_friends = params
                    .stream()
                    .skip(UPD_ARGS_COUNT)
                    .map(Long::valueOf)
                    .map(id -> entityManager.getReference(Cat.class, id))
                    .collect(Collectors.toSet());
        }

        cat.setFriendsCats(cat_friends);

        executeInsideTransaction(entityManager -> entityManager.merge(cat));
        executeInsideTransaction(EntityManager::flush);
    }

}
