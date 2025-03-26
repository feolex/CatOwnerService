package ru.feolex.ControllerLayer.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.feolex.OperationClasses.OperationResult;
import ru.feolex.ServiceLayer.Enums.ColorMapper;
import ru.feolex.ServiceLayer.Models.CatModel;
import ru.feolex.ServiceLayer.Services.CatServiceInterface;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CatController implements CatControllerInterface {

    @Autowired
    private final CatServiceInterface service;
    private Map<String, CatModel> cats;

    public CatController(CatServiceInterface service){
        this.service = service;
    }
    @Override
    public String getOwnerName(String catName) {
        if (cats.containsKey(catName)) {
            return cats.get(catName).getOwner().getName();
        } else {
            synchronizeWithDb();
            if (cats.containsKey(catName)) {
                return cats.get(catName).getOwner().getName();
            }
            else{
                return "NULL";
            }
        }
    }

    @Override
    public String getWholeInfo(String catName) {
        if (cats.containsKey(catName)) {
            return cats.get(catName).toString();
        } else {
            synchronizeWithDb();
            if (cats.containsKey(catName)) {
                return cats.get(catName).toString();
            }
            else{
                return "NULL";
            }
        }
    }

    @Override
    public String getBirthday(String catName) {
        if (cats.containsKey(catName)) {
            return cats.get(catName).getBirthDate();
        } else {
            synchronizeWithDb();
            if (cats.containsKey(catName)) {
                return cats.get(catName).getBirthDate();
            }
            else{
                return "NULL";
            }
        }
    }

    @Override
    public String getColor(String catName) {
        if (cats.containsKey(catName)) {
            return ColorMapper.PrintColorName(cats.get(catName).getColor());
        } else {
            synchronizeWithDb();
            if (cats.containsKey(catName)) {
                return ColorMapper.PrintColorName(cats.get(catName).getColor());
            }
            else{
                return "NULL";
            }
        }
    }

    @Override
    public String getBreed(String catName) {
        if (cats.containsKey(catName)) {
            return cats.get(catName).getBreed();
        } else {
            synchronizeWithDb();
            if (cats.containsKey(catName)) {
                return cats.get(catName).getBreed();
            }
            else{
                return "NULL";
            }
        }
    }

    @Override
    public List<String> getFriends(String catName) {
        List<String> resultSet;
        if (cats.containsKey(catName)) {
            resultSet = cats.get(catName)
                    .getFriendsCatsList()
                    .stream()
                    .map(CatModel::getName)
                    .collect(Collectors.toList());
        } else {
            synchronizeWithDb();
            if (cats.containsKey(catName)) {
                resultSet = cats.get(catName)
                        .getFriendsCatsList()
                        .stream()
                        .map(CatModel::getName)
                        .collect(Collectors.toList());
            }
            else{
                return Collections.singletonList("NULL");
            }
        }

        return resultSet;
    }

    private void synchronizeWithDb() {
        Map<String, CatModel> afterSync = new HashMap<>();
        OperationResult result = service.getAllCats();

        switch (result.Type()) {
            case SuccessWithMeaning -> {
                OperationResult.SuccessWithMeaning meaningResult = (OperationResult.SuccessWithMeaning) result;
                List<CatModel> models = (List<CatModel>) meaningResult.getValue();
                for (CatModel model : models) {
                    afterSync.put(model.getName(), model);
                }

                cats = afterSync;
                return;
            }
            default -> {
                var failruResult = (OperationResult.Failure) result;
//                throw new SyncException(failruResult.toString());
            }
        }
    }
}
