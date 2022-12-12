package ru.got.shop.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.got.shop.mapper.UserRegisterMapper;
import ru.got.shop.model.User;
import ru.got.shop.model.UserAvatar;
import ru.got.shop.model.dto.RegReqDto;
import ru.got.shop.repository.UserRepository;
import ru.got.shop.security.Role;
import ru.got.shop.service.AuthService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager manager;
    private final UserRepository userRepository;
    private final UserRegisterMapper userRegisterMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean login(String userName, String password) {
        Authentication authenticate = manager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
        SecurityContextHolder.getContext()
                .setAuthentication(authenticate);
        return true;
    }

    @Override
    public boolean register(RegReqDto registerReq, RegReqDto.RoleEnum role) {
        Optional<User> firstByEmail = userRepository.findFirstByEmail(registerReq.getUsername());
        if (firstByEmail.isPresent()) {
            throw new RuntimeException("This email is already in use another account!!!");
        }
        User entity = userRegisterMapper.toEntity(registerReq);
        String pass = passwordEncoder.encode(entity.getPassword());
        entity.setPassword(pass);
        entity.setRoleGroup(Role.USER);
        log.info("User register: {}", entity);
        userRepository.save(entity);
        return true;
    }
}
