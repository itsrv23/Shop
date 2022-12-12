package ru.got.shop.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.got.shop.exception.ForbiddenException;
import ru.got.shop.model.User;
import ru.got.shop.model.dto.UserDto;
import ru.got.shop.repository.UserRepository;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Service
public class PermissionService {
    private final UserRepository userRepository;

    public boolean checkPermissionForUserController(String login, UserDto userDto) {
        if (isAdminRole(login) || isCurrentUser(login, userDto)) {
            return true;
        }
        throw new ForbiddenException();
    }

    public boolean checkPermissionForUserController(String login, Integer userId) {
        if (isAdminRole(login) || isCurrentUser(login, userId)) {
            return true;
        }
        throw new ForbiddenException();
    }

    private User findUserByLogin(String login) {
        return userRepository.findFirstByEmail(login).orElseThrow(EntityNotFoundException::new);
    }

    private boolean isAdminRole(String login) {
        return Role.ADMIN.equals(findUserByLogin(login).getRoleGroup());
    }


    private boolean isCurrentUser(String login, Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return user.getEmail().equals(login);
    }

    private boolean isCurrentUser(String login, UserDto userDto) {
        User userByLogin = findUserByLogin(login);
        return userDto.getEmail().equals(userByLogin.getEmail());
    }
}
