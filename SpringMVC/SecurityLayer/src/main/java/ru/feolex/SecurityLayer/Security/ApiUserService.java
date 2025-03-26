package ru.feolex.SecurityLayer.Security;

import ru.feolex.OperationClasses.OperationResult;

import java.util.List;
import java.util.Optional;

public interface ApiUserService {

    Optional<ApiUser> findByEmail(String email);
    OperationResult registerUser(ApiUser apiUser);
    OperationResult updateUser(ApiUser apiUser);
    OperationResult deleteUser(ApiUser apiUser);

    OperationResult getUser(Long apiUserId);

    OperationResult getALlUsers();
}
