package ru.feolex.DaoLayer.Dao;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import org.springframework.stereotype.Component;
import ru.feolex.DaoLayer.Entities.Cat;
import ru.feolex.DaoLayer.Entities.Owner;
import ru.feolex.DaoLayer.Exceptions.UpdateArgsException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class OwnerDao extends DaoBase<Owner> implements OwnerDaoInterface {

    private static Integer UPD_ARGS_COUNT = 2; /// minimal count of List<String> params in update()

    public OwnerDao(EntityManagerFactory entityManagerFactor) {
        super(entityManagerFactor);
    }

    @Override
    public Optional<Owner> get(Long id) {
        return Optional.ofNullable(entityManager.find(Owner.class, id));
    }

    @Override
    public List<Owner> getAll() {
        Query query = entityManager.createQuery("FROM Owner");
        return query.getResultList();
    }

    /**
     * Strong order for String in "params"
     * /// Min_size of params is 2
     * /// All parameters are String except notated as parametr_name(Long)
     * /// Owner_name, Birthdate, Cat1Id(Long), Cat2Id(Long), ... CatNId(Long)
     **/
    @Override
    public void update(Owner owner, List<String> params) throws UpdateArgsException {

        if (params.size() < UPD_ARGS_COUNT) {
            throw new UpdateArgsException("Too small arguments in provided list of params to update Cat! Min size is " + UPD_ARGS_COUNT + "! Provided list size:" + String.valueOf(params.size()) + "Provided list is: ", params);
        }

        owner.setName(Objects.requireNonNull(params.get(0), "Name cannot be null"));
        owner.setBirthDate(params.get(1));

        List<Cat> cats = null;
        if (params.size() != UPD_ARGS_COUNT) {
            cats = params
                    .stream()
                    .skip(2)
                    .map(Long::valueOf)
                    .map(id -> entityManager.getReference(Cat.class, id)).toList();
            cats.forEach(System.out::println);
        }
        owner.setCats(cats);

        executeInsideTransaction(entityManager -> entityManager.merge(owner));
    }
}
