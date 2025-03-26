package ru.feolex.SecurityLayer.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.feolex.DaoLayer.Entities.Owner;

@Configuration
public class ApiUserRepositoryProvider {
    private final ApiUserRepository userRepository;

    @Autowired
    public ApiUserRepositoryProvider(@Qualifier("ApiUserRepository") ApiUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean("InitializedApiUserRepository")
    public ApiUserRepository provide(){
        ApiUser admin = new ApiUser();
//        ApiUser admin = new ApiUser(1l, "admin", "$2a$12$xIXJzEfdyUAnnA.hfk4WM.dSUdNYIuU2ARy30JXKqNzspUtf2ce82", Role.ADMIN, null);

        admin.setRole(Role.ADMIN);
        admin.setEmail("admin");
        admin.setPassword("$2a$12$xIXJzEfdyUAnnA.hfk4WM.dSUdNYIuU2ARy30JXKqNzspUtf2ce82"); // "admin" codded by BCrypt with stregth 12
//        admin.setOwnerModel(null);

        this.userRepository.save(admin);

        return userRepository;
    }
}
