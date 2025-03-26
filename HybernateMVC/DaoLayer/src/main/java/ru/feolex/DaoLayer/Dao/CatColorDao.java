package ru.feolex.DaoLayer.Dao;

import jakarta.persistence.EntityManagerFactory;
import ru.feolex.DaoLayer.Entities.CatColor;
import ru.feolex.DaoLayer.Exceptions.UpdateArgsException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
public class CatColorDao extends DaoBase<CatColor> implements CatColorDaoInterface {

    private static final Integer UPD_ARGS_COUNT = 2;
    public CatColorDao(EntityManagerFactory entityManagerFactory){
        super(entityManagerFactory);
    }
    @Override
    public Optional<CatColor> get(Long id) {
        Optional<CatColor> result = Optional.ofNullable(entityManager.find(CatColor.class, id));
        if (result.isEmpty()) {
            result = Optional.ofNullable(entityManager.find(CatColor.class, id));
        }

        return result;
    }

    @Override
    public Collection<CatColor> getAll() {
        return entityManager.createQuery("FROM CatColor").getResultList();
    }

    @Override
    public void update(CatColor color, List<String> params) throws UpdateArgsException {
        if (params.size() != UPD_ARGS_COUNT) {
            throw new UpdateArgsException("Uncorrect count of arguments in provided list of params to update CatColor! Size must be " + UPD_ARGS_COUNT + "! Provided list size:" + String.valueOf(params.size()) + "Provided list is: ", params);
        }
    }

    @Override
    public Optional<CatColor> isContainsColor(String provided_color){
        String query = "SELECT c FROM CatColor c WHERE c.colorName = :providedColor";
        return entityManager.createQuery(query)
                .setParameter("providedColor", provided_color)
                .getResultList().stream().findFirst();
    }
}
