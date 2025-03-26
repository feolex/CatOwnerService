package ru.feolex.SecurityLayer.Security;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String name;
    private String password;
}
