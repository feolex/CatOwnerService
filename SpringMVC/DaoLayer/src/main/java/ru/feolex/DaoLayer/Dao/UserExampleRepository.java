package ru.feolex.DaoLayer.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.feolex.DaoLayer.Entities.UserExample;

import java.util.Optional;

@Repository
public interface UserExampleRepository extends JpaRepository<UserExample, Long>, AdditionalMethodsForRepository {
    Optional<UserExample> findByName(String name);

    @Modifying
    @Transactional
    @Query("update UserExample u set u.name = :name, u.comment = :comment where u.id = :id")
    int updateNameAndCommentById(@NonNull @Param("name") String name, @Param("comment") String comment, @Param("id") Long id);
}