package ru.feolex.ControllerLayer.Controllers;

import java.util.List;

public interface OwnerControllerInterface {
    String getOwnerBirthdate(String ownerName);

    List<String> getCatNames(String ownerName);
}
