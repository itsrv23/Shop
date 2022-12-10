package ru.got.shop.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public interface AuthenticationFacade {
    default Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
