package ru.feolex.SecurityLayer.Security;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    USER(Set.of(Permission.USER)),
    ADMIN(Set.of(Permission.USER, Permission.ADMIN));

    @Getter
    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities(){
        return permissions
                .stream()
                .map(p -> new SimpleGrantedAuthority(p.getPermission()))
                .collect(Collectors.toSet());
    }
}
