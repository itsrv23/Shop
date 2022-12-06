package ru.got.shop.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import ru.got.shop.exception.UserEmailNotUniqException;
import ru.got.shop.mapper.UserRegisterMapper;
import ru.got.shop.model.User;
import ru.got.shop.openapi.dto.RegReq;
import ru.got.shop.repository.UserRepository;
import ru.got.shop.service.RegisterService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterServiceImpl implements RegisterService {

    private final UserRepository userRepository;
    private final UserRegisterMapper userRegisterMapper;

    @Override
    public User register(RegReq req) {
        Optional<User> firstByEmail = userRepository.findFirstByEmail(req.getUsername());
        if (firstByEmail.isPresent()){
            throw new UserEmailNotUniqException();
        }

        User entity = userRegisterMapper.toEntity(req);
        String md5 = DigestUtils.md5DigestAsHex(entity.getPassword().getBytes());
        entity.setPassword(md5);
        entity.setRoleGroup(RegReq.RoleEnum.USER);
        log.info("User register: {}", entity);
        return userRepository.save(entity);
    }
}
