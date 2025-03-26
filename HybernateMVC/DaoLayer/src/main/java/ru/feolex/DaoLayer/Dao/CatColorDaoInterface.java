package ru.feolex.DaoLayer.Dao;

import ru.feolex.DaoLayer.Entities.CatColor;

import java.util.Optional;

public interface CatColorDaoInterface extends DaoInterface<CatColor> {
    Optional<CatColor> isContainsColor(String provided_color);
}
