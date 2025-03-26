package ru.feolex.DaoLayer.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.feolex.DaoLayer.Entities.Cat;

import java.util.Optional;

@Repository
@Transactional
public interface CatRepository extends JpaRepository<Cat, Long> {
    Optional<Cat> findTopByOrderByIdDesc();
}