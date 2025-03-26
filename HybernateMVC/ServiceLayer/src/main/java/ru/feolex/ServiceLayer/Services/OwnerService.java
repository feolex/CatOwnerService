package ru.feolex.ServiceLayer.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.feolex.DaoLayer.Dao.OwnerDaoInterface;
import ru.feolex.DaoLayer.Entities.Owner;
import ru.feolex.DaoLayer.Exceptions.UpdateArgsException;
import ru.feolex.OperationClasses.OperationResult;
import ru.feolex.ServiceLayer.Models.OwnerModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OwnerService implements OwnerServiceInterface {

    @Autowired
    private OwnerDaoInterface ownerDao;
    public OwnerService(OwnerDaoInterface ownerDao) {
        this.ownerDao = ownerDao;
    }

    @Override
    public OperationResult addOwner(OwnerModel owner) {
        ownerDao.save(owner.transform());
        if (existsInBd(owner.getId())) {
            return new OperationResult.Success();
        }else return new OperationResult.Failure("By unkhown problem owner didn't added to db!");
    }

    @Override
    public OperationResult updateOwner(OwnerModel owner) {
        try {
            ownerDao.update(owner.transform(), owner.getFieldsAsString());
        }catch (UpdateArgsException e){
            return new OperationResult.Failure("Uncorrect count of arguments to update!");
        }
        if (existsInBd(owner.getId())) {
            return new OperationResult.Success();
        }else return new OperationResult.Failure("By unkhown problem owner didn't update in db!");
    }

    @Override
    public OperationResult deleteOwner(OwnerModel owner) {
        ownerDao.delete(owner.transform());
        if (existsInBd(owner.getId())) {
            return new OperationResult.Failure("By unkhown problem owner didn't delete from db!");
        }else return new OperationResult.Success();
    }

    @Override
    public OperationResult getOwnerById(Long id) {
        Optional<Owner> result = ownerDao.get(id);
        if (result.isEmpty()) {
            return new OperationResult.Failure("Cannot find owner with this id!");
        }

        return new OperationResult.SuccessWithMeaning<OwnerModel>(new OwnerModel(result.get()));
    }

    @Override
    public OperationResult getAllOwners() {
        List<Owner> resultOwners = ownerDao.getAll().stream().toList();
        if(!resultOwners.isEmpty()){
            List<OwnerModel> resultModels = new ArrayList<>();
            for (Owner resultOwner : resultOwners) {
                resultModels.add(new OwnerModel(resultOwner));
            }

            return new OperationResult.SuccessWithMeaning<>(resultModels);
        }

        return new OperationResult.Failure("All cats are empty!");
    }

    private boolean existsInBd(Long id) {
        return ownerDao.get(id).isPresent();
    }
}
