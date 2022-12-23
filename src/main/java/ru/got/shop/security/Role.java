package ru.got.shop.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static ru.got.shop.security.Permission.*;

@RequiredArgsConstructor
public enum Role {
    ROLE_USER(Set.of(
            USERS_CRUD,
            ADS_CRUD,
            ADS_COMMENT_CRUD)
    ),
    ROLE_ADMIN(Set.of(
            ADS_CRUD, ADS_FULL,
            ADS_COMMENT_CRUD, ADS_COMMENT_FULL,
            USERS_CRUD, USERS_FULL
    ));
    private final Set<Permission> permissions;

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getVALUE()))
                .collect(Collectors.toSet());
    }
}
