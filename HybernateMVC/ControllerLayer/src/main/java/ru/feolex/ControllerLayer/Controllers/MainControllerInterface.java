package ru.feolex.ControllerLayer.Controllers;

import java.util.List;

public interface MainControllerInterface {
    List<String> getOwnersNamesByCatName(String catName);
    String getWholeInfoAboutCat(String catName, String ownerName);
    String getCatBirthday(String catName, String ownerName);
    String getOwnerBirthday(String ownerName);
    String getColorOfCat(String catName, String ownerName);
    String getBreedOfCat(String catName, String ownerName);
    List<String> getCatFriends(String catName, String ownerName);

    List<String> getAllCatsOfOwner(String ownerName);
}
