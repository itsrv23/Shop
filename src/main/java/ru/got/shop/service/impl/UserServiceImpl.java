package ru.got.shop.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.got.shop.exception.ForbiddenException;
import ru.got.shop.mapper.UserMapper;
import ru.got.shop.model.User;
import ru.got.shop.model.dto.NewPasswordDto;
import ru.got.shop.model.dto.ResponseWrapperUserDto;
import ru.got.shop.model.dto.UserDto;
import ru.got.shop.repository.UserRepository;
import ru.got.shop.service.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public final static String NOT_EXIST = "User doesn't exist!!!";

    @Override
    public UserDto findUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_EXIST));
        return userMapper.toDto(user);
    }

    @Override
    public UserDto findUser(String login) {
        User user = userRepository.findFirstByEmail(login).orElseThrow(() -> new EntityNotFoundException(NOT_EXIST));
        return userMapper.toDto(user);
    }

    @Override
    public ResponseWrapperUserDto getUsers() {
        // Выше проверка на роли
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        return new ResponseWrapperUserDto(users.size(), userMapper.toDtos(users));
    }

    @Override
    public NewPasswordDto setPassword(NewPasswordDto newPasswordDto, String login) {
        if (!newPasswordDto.getCurrentPassword().equals(newPasswordDto.getNewPassword())) {
            throw new ForbiddenException("Не верный старый пароль");
        }
        User user = findUserByLogin(login);
        user.setPassword(passwordEncoder.encode(newPasswordDto.getNewPassword()));
        userRepository.save(user);
        return newPasswordDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User updated = findUserByLogin(userDto.getEmail());
        updated.setFirstName(userDto.getFirstName());
        updated.setLastName(userDto.getLastName());
        updated.setPhone(userDto.getPhone());
        return userMapper.toDto(userRepository.save(updated));
    }

    private User findUserByLogin(String login) {
        return userRepository.findFirstByEmail(login).orElseThrow(EntityNotFoundException::new);
    }

}
