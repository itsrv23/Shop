package ru.got.shop.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.got.shop.dto.UserDto;
import ru.got.shop.exception.AdNotFoundException;
import ru.got.shop.exception.ForbiddenException;
import ru.got.shop.exception.UserNotFoundException;
import ru.got.shop.model.Ad;
import ru.got.shop.model.User;
import ru.got.shop.repository.AdRepository;
import ru.got.shop.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class PermissionService implements AuthenticationFacade {
    private final UserRepository userRepository;
    private final AdRepository adRepository;

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
        return userRepository.findFirstByEmail(login).orElseThrow(() -> new UserNotFoundException(login));
    }

    public boolean checkAllowedForbidden(Integer id) {
        Ad ad = adRepository.findById(id).orElseThrow(() -> new AdNotFoundException(id));
        User currentUser = userRepository.findFirstByEmail(getLogin()).orElseThrow(() -> new UserNotFoundException(
                "Current user not found"));

        if (ad.getUserId().equals(currentUser) || currentUser.getRoleGroup().equals(Role.ROLE_ADMIN)) {
            return true;
        } else {
            throw new ForbiddenException();
        }
    }

    private boolean isAdminRole(String login) {
        return Role.ROLE_ADMIN.equals(findUserByLogin(login).getRoleGroup());
    }

    private boolean isCurrentUser(String login, Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return user.getEmail().equals(login);
    }

    private boolean isCurrentUser(String login, UserDto userDto) {
        User userByLogin = findUserByLogin(login);
        return userDto.getEmail().equals(userByLogin.getEmail());
    }
}
