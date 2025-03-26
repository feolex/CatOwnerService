package ru.feolex.SecurityLayer.Security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Repository("ApiUserRepository")
@Transactional
public interface ApiUserRepository extends JpaRepository<ApiUser, Long> {
    Optional<ApiUser> findByEmail(String apiUsername);
}
