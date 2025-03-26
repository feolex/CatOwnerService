package ru.feolex.ServiceLayer.Services;

public interface ColorServiceInterface {
    boolean existsColorByName(String colorName);

    boolean existsColorById(Long along);

    void initColors();
}
