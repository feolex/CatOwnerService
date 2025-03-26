package ru.feolex.ControllerLayer.Controllers;

import java.util.List;

public interface CatControllerInterface {
    String getOwnerName(String catName);
    String getWholeInfo(String catName);

    String getBirthday(String catName);
    String getColor(String catName);
    String getBreed(String catName);
    List<String> getFriends(String catName);
}
