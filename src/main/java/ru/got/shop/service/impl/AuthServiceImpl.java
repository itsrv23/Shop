package ru.got.shop.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.got.shop.openapi.dto.RegReq;
import ru.got.shop.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserDetailsManager manager;

    private final PasswordEncoder encoder;

    public AuthServiceImpl(UserDetailsManager manager) {
        this.manager = manager;
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public boolean login(String userName, String password) {
        if (!manager.userExists(userName)) {
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(RegReq registerReq, RegReq.RoleEnum role) {
        if (manager.userExists(registerReq.getUsername())) {
            return false;
        }
        manager.createUser(User.withDefaultPasswordEncoder()//TODO заменить на не устаревший
                .password(registerReq.getPassword())
                .username(registerReq.getUsername())
                .roles(role.name())
                .build());
        return true;
    }
}
