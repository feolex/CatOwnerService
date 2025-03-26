package ru.feolex.ControllerLayer.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.feolex.ControllerLayer.Exceptions.SyncException;
import ru.feolex.OperationClasses.OperationResult;
import ru.feolex.ServiceLayer.Models.OwnerModel;
import ru.feolex.ServiceLayer.Services.OwnerServiceInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
//TODO - add DI via Spring
@Component
public class OwnerController implements OwnerControllerInterface {

    @Autowired
    private final OwnerServiceInterface service;
    private Map<String, OwnerModel> owners;

    public OwnerController(OwnerServiceInterface service){
        this.service = service;
        synchronizeWithDb();
    }

    @Override
    public String getOwnerBirthdate(String ownerName) {
        return owners.get(ownerName).getBirthDate();
    }

    @Override
    public List<String> getCatNames(String ownerName) {
        return owners.get(ownerName).getCatsNames();
    }

    @lombok.SneakyThrows
    private void synchronizeWithDb(){
        Map<String, OwnerModel> afterSync = new HashMap<>();
        OperationResult result = service.getAllOwners();

        switch (result.Type()){
            case SuccessWithMeaning -> {
                //TODO - maybe error
                OperationResult.SuccessWithMeaning meaningResult = (OperationResult.SuccessWithMeaning) result;
                List<OwnerModel> models = (List<OwnerModel>) meaningResult.getValue();
                for (OwnerModel model : models) {
                    afterSync.put(model.getName(), model);
                }

                owners = afterSync;
                return;
            }
            default -> {
                var failruResult = (OperationResult.Failure) result;
                throw new SyncException(failruResult.toString());
            }
        }
    }
}
