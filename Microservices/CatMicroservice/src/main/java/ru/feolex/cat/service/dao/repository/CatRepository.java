package ru.feolex.cat.service.dao.repository;

import ru.feolex.cat.service.common.model.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
    List<Cat> findByOwnerId(Long ownerId);
    List<Cat> findByName(String name);
    List<Cat> findByBreed(String breed);
} 