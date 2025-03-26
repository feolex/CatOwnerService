package ru.feolex.SecurityLayer.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.feolex.DaoLayer.Entities.Cat;
import ru.feolex.OperationClasses.OperationResult;

import java.util.List;
import java.util.Optional;

@Service
@EnableJpaRepositories(basePackages = "ru.feolex.SecurityLayer")
@EntityScan(basePackages = "ru.feolex.SecurityLayer")
public class UserDetailsServiceImpl implements UserDetailsService, ApiUserService {

    private final ApiUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailsServiceImpl(@Qualifier("InitializedApiUserRepository") ApiUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String apiUsername) throws UsernameNotFoundException {
        ApiUser user = userRepository.findByEmail(apiUsername).orElseThrow(() -> new UsernameNotFoundException("Api username:'" + apiUsername + "'not found!"));
        return SecurityUser.fromApiUser(user);
    }

    public Optional<ApiUser> findByEmail(String apiUser){
        return userRepository.findByEmail(apiUser);
    }
    public OperationResult registerUser(ApiUser apiUser) {
        apiUser.setPassword(passwordEncoder.encode(apiUser.getPassword()));
        ApiUser registredUser = userRepository.save(apiUser);

        if (userRepository.existsById(apiUser.getId())) {
            return new OperationResult.SuccessWithMeaning<ApiUser>(apiUser);
        } else return new OperationResult.Failure("By unkhown problem user didn't registred to db!");
    }

    public OperationResult updateUser(ApiUser apiUser) {
        if (!userRepository.existsById(apiUser.getId())) {
            return new OperationResult.Failure("ApiUser with this id:" + apiUser.getId() + "didn't registred!");
        }
        ApiUser updatedUser = userRepository.save(apiUser);
        if (userRepository.existsById(apiUser.getId())) {
            return new OperationResult.SuccessWithMeaning<ApiUser>(apiUser);
        } else return new OperationResult.Failure("By unkhown problem user didn't updated to db!");
    }
    public OperationResult deleteUser(ApiUser apiUser) {
        if (!userRepository.existsById(apiUser.getId())) {
            return new OperationResult.Failure("ApiUser with this id:" + apiUser.getId() + "didn't registred!");
        }
        userRepository.delete(apiUser);
        if (!userRepository.existsById(apiUser.getId())) {
            return new OperationResult.Success();
        } else return new OperationResult.Failure("By unkhown problem user didn't deleted from db!");
    }

    public OperationResult getUser(Long apiUserId){
        if (!userRepository.existsById(apiUserId)) {
            return new OperationResult.Failure("ApiUser with this id:" + apiUserId + "didn't registred!");
        }
        Optional<ApiUser> registredUser = Optional.of(userRepository.getReferenceById(apiUserId));
        if (registredUser.isPresent()) {
            return new OperationResult.SuccessWithMeaning<ApiUser>(registredUser.get());
        } else return new OperationResult.Failure("By unkhown problem user didn't get from db!");
    }

    public OperationResult getALlUsers(){
        List<ApiUser> result = userRepository.findAll();
        if(result.isEmpty()){
           return new OperationResult.Failure("No one user registred in bd!");
        }

        return new OperationResult.SuccessWithMeaning<List<ApiUser>>(result);
    }
}
