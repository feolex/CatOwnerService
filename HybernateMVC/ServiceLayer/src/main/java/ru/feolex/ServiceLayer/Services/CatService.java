package ru.feolex.ServiceLayer.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.feolex.DaoLayer.Dao.CatDaoInterface;
import ru.feolex.DaoLayer.Entities.Cat;
import ru.feolex.DaoLayer.Exceptions.UpdateArgsException;
import ru.feolex.OperationClasses.OperationResult;
import ru.feolex.ServiceLayer.Models.CatModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CatService implements CatServiceInterface {

    @Autowired
    private CatDaoInterface catDao;

    public CatService(CatDaoInterface catDao) {
        this.catDao = catDao;
    }

    @Override
    public OperationResult addCat(CatModel cat) {
        catDao.save(cat.transform());
        if (existsInBd(cat.getId())) {
            return new OperationResult.Success();
        }else return new OperationResult.Failure("By unkhown problem cat didn't added to db!");
    }

    @Override
    public OperationResult updateCat(CatModel cat){

        try{
            catDao.update(cat.transform(), cat.getFieldsAsString());
        }catch (UpdateArgsException e){
            return new OperationResult.Failure("Uncorrect count of arguments to update!");
        }
        if (existsInBd(cat.getId())) {
            return new OperationResult.Success();
        }else return new OperationResult.Failure("By unkhown problem cat didn't update in db!");
    }

    @Override
    public OperationResult deleteCat(CatModel cat) {
        catDao.delete(cat.transform());
        if (existsInBd(cat.getId())) {
            return new OperationResult.Failure("By unkhown problem cat didn't delete from db!");
        }else return new OperationResult.Success();
    }

    @Override
    public OperationResult getCatById(Long id) {
        Optional<Cat> result = catDao.get(id);
        if (result.isEmpty()) {
            return new OperationResult.Failure("Cannot find cat with this id!");
        }

        return new OperationResult.SuccessWithMeaning<CatModel>(new CatModel(result.get()));
    }

    @Override
    public OperationResult getAllCats() {
        List<Cat> resultCats = catDao.getAll().stream().toList();
        if(!resultCats.isEmpty()){
            List<CatModel> resultModels = new ArrayList<>();
            for (Cat resultCat : resultCats) {
                resultModels.add(new CatModel(resultCat));
            }

            return new OperationResult.SuccessWithMeaning<>(resultModels);
        }

        return new OperationResult.Failure("All cats are empty!");
    }

    private boolean existsInBd(Long id) {
        return catDao.get(id).isPresent();
    }
}
