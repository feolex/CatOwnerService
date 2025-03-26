package ru.feolex.ServiceLayer.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.feolex.DaoLayer.Dao.OwnerRepository;
import ru.feolex.DaoLayer.Entities.Owner;
import ru.feolex.OperationClasses.OperationResult;
import ru.feolex.ServiceLayer.Models.OwnerModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OwnerService implements OwnerServiceInterface {

    @Autowired
    private OwnerRepository ownerRepo;

    @Override
    public OperationResult addOwner(OwnerModel owner) {
        Owner ownerRegistred = ownerRepo.save(owner.transform());

        if (ownerRepo.existsById(ownerRegistred.getId())) {
            return new OperationResult.SuccessWithMeaning<OwnerModel>(new OwnerModel(ownerRegistred));
        }else return new OperationResult.Failure("By unkhown problem owner didn't added to db!");
    }

    @Override
    public OperationResult updateOwner(OwnerModel owner) {
        Owner ownerRegistred = ownerRepo.save(owner.transform());

        if (ownerRepo.existsById(ownerRegistred.getId())) {
            return new OperationResult.SuccessWithMeaning<OwnerModel>(new OwnerModel(ownerRegistred));
        }else return new OperationResult.Failure("By unkhown problem owner didn't update in db!");
    }

    @Override
    public OperationResult deleteOwner(OwnerModel owner) {
        ownerRepo.delete(owner.transform());
        if (!ownerRepo.existsById(owner.getId())) {
            return new OperationResult.Success();
        }else return new OperationResult.Failure("By unkhown problem owner didn't delete from db!");
    }

    @Override
    public OperationResult getOwnerById(Long id) {
        Optional<Owner> result = ownerRepo.findById(id);
        if (result.isEmpty()) {
            return new OperationResult.Failure("Cannot find owner with this id!");
        }

        return new OperationResult.SuccessWithMeaning<OwnerModel>(new OwnerModel(result.get()));
    }

    @Override
    public OperationResult getAllOwnersOrderedById() {
        List<Owner> resultOwners = ownerRepo.findAll();
        if(!resultOwners.isEmpty()){
            List<OwnerModel> resultModels = new ArrayList<>();
            for (Owner resultOwner : resultOwners) {
                resultModels.add(new OwnerModel(resultOwner));
            }

            return new OperationResult.SuccessWithMeaning<>(resultModels);
        }

        return new OperationResult.Failure("No Owners has been found!");
    }
}
