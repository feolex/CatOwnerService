package ru.feolex.ServiceLayer.Services;


import ru.feolex.OperationClasses.OperationResult;
import ru.feolex.ServiceLayer.Models.OwnerModel;

public interface OwnerServiceInterface {
    OperationResult addOwner(OwnerModel owner);
    OperationResult updateOwner(OwnerModel owner);
    OperationResult deleteOwner(OwnerModel owner);
    OperationResult getOwnerById(Long id);

    OperationResult getAllOwners();
}
