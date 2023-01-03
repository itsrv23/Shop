package ru.got.shop.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import ru.got.shop.exception.ForbiddenException;

public interface AuthenticationFacade {
    default String getLogin() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            return principal.getUsername();
        } catch (Exception e) {
            throw new ForbiddenException();
        }
    }
}
