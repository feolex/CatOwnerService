package ru.feolex.ControllerLayer.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

//TODO - add Di via Spring
@Component
@AllArgsConstructor
public class MainController implements MainControllerInterface {

    @Autowired
    private final CatControllerInterface catController;
    @Autowired
    private final OwnerControllerInterface ownerController;

    @Override
    public List<String> getOwnersNamesByCatName(String catName) {
        return Collections.singletonList(catController.getOwnerName(catName));
    }

    @Override
    public String getWholeInfoAboutCat(String catName, String ownerName) {
        Optional<String> possibleCatName = ownerController.getCatNames(ownerName).stream().filter(s -> s.equalsIgnoreCase(catName)).findFirst();
        if (possibleCatName.isEmpty()) {
            return "NULL";
        } else {
            return catController.getWholeInfo(possibleCatName.get());
        }
    }

    @Override
    public String getCatBirthday(String catName, String ownerName) {
        Optional<String> possiblaeCatName = ownerController.getCatNames(ownerName).stream().filter(s -> s.equalsIgnoreCase(catName)).findFirst();
        if (possiblaeCatName.isEmpty()) {
            return "NULL";
        } else {
            return catController.getBirthday(catName);
        }
    }

    @Override
    public String getOwnerBirthday(String ownerName) {
        return ownerController.getOwnerBirthdate(ownerName);
    }

    @Override
    public String getColorOfCat(String catName, String ownerName) {
        Optional<String> possiblaeCatName = ownerController.getCatNames(ownerName).stream().filter(s -> s.equalsIgnoreCase(catName)).findFirst();
        if (possiblaeCatName.isEmpty()) {
            return "NULL";
        } else {
            return catController.getColor(catName);
        }
    }

    @Override
    public String getBreedOfCat(String catName, String ownerName) {
        Optional<String> possiblaeCatName = ownerController.getCatNames(ownerName).stream().filter(s -> s.equalsIgnoreCase(catName)).findFirst();
        if (possiblaeCatName.isEmpty()) {
            return "NULL";
        } else {
            return catController.getBreed(catName);
        }
    }

    @Override
    public List<String> getCatFriends(String catName, String ownerName) {
        Optional<String> possiblaeCatName = ownerController.getCatNames(ownerName).stream().filter(s -> s.equalsIgnoreCase(catName)).findFirst();
        if (possiblaeCatName.isEmpty()) {
            return Collections.singletonList("NULL");
        } else {
            return catController.getFriends(catName);
        }
    }

    @Override
    public List<String> getAllCatsOfOwner(String ownerName) {
        return ownerController.getCatNames(ownerName);
    }
}
