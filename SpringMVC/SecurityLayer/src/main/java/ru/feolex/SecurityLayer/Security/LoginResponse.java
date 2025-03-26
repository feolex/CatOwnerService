package ru.feolex.SecurityLayer.Security;

import lombok.Data;
@Data
public class LoginResponse {
    private boolean result;
    private ApiUser userLoginned;
}
