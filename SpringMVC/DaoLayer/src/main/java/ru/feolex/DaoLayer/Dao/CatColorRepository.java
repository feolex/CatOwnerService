package ru.feolex.DaoLayer.Dao;

import jakarta.persistence.NamedNativeQuery;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.feolex.DaoLayer.Entities.Cat;
import ru.feolex.DaoLayer.Entities.CatColor;

import java.util.Optional;

@Repository
public interface CatColorRepository extends CrudRepository<CatColor, Long> {
    boolean existsByColorName(String colorName);
}