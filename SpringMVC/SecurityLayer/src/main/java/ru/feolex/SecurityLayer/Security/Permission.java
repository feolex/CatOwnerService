package ru.feolex.SecurityLayer.Security;

import lombok.Getter;

public enum Permission {
    USER("user:crudOwnCats"),
    ADMIN("user:allActions");

    @Getter
    private final String permission;
    Permission(String permission){
        this.permission = permission;
    }

}
