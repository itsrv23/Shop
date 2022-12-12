package ru.got.shop.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationFacade {
    default Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
    default String getLogin(){
        UserDetails principal = (UserDetails) getAuthentication().getPrincipal();
        return principal.getUsername();
    }
}
