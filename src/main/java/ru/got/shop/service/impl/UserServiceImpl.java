package ru.got.shop.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.got.shop.exception.UserNotFoundException;
import ru.got.shop.mapper.UserMapper;
import ru.got.shop.model.dto.NewPassword;
import ru.got.shop.model.dto.ResponseWrapperUser;
import ru.got.shop.model.dto.User;
import ru.got.shop.repository.UserRepository;
import ru.got.shop.service.UserService;
import ru.got.shop.constant.UserFactory;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final  UserMapper userMapper;
    @Override
    public User getUserUsingGET(Integer id) {
        ru.got.shop.model.User user = userRepository.findById(1).orElseThrow(UserNotFoundException::new);
        System.out.println("user getAds = " + user.getAds());
        System.out.println("user = " + user);
//        return UserFactory.getUser();
        return userMapper.toDto(user);
    }

    @Override
    public ResponseWrapperUser getUsersUsingGET() {
        // Какое кривое называние для такого метода. Дергается http://localhost:8080/users/me
        //TODO найти способ брать юзера из сессии или из хедеров
        User user = userRepository.findById(1).map(userMapper::toDto).orElseThrow(UserNotFoundException::new);
        ResponseWrapperUser responseWrapperUser = new ResponseWrapperUser(1, List.of(user));
        return responseWrapperUser;
    }

    @Override
    public NewPassword setPasswordUsingPOST(NewPassword newPassword) {
        return UserFactory.getNewPassword();
    }

    @Override
    public User updateUserUsingPATCH(User user) {
        User tmp = UserFactory.getUser();
        tmp.setId(1);
        log.info("updateUserUsingPATCH");
        return tmp;
    }
}
