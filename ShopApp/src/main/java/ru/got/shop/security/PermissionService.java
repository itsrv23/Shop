package ru.got.shop.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.got.shop.dto.UserDto;
import ru.got.shop.exception.ForbiddenException;
import ru.got.shop.model.Ads;
import ru.got.shop.model.User;
import ru.got.shop.repository.AdsRepository;
import ru.got.shop.repository.UserRepository;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Service
public class PermissionService implements AuthenticationFacade {
    private final UserRepository userRepository;
    private final AdsRepository adsRepository;

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

    public boolean checkAllowedForbidden(Integer id) {
        Ads ads = adsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found ads with id: ".concat(id.toString())));
        User currentUser = userRepository.findFirstByEmail(getLogin()).orElseThrow(() -> new EntityNotFoundException(
                "Current user not found"));

        if (ads.getUserId().equals(currentUser) || currentUser.getRoleGroup().equals(Role.ROLE_ADMIN)) {
            return true;
        } else {
            throw new ForbiddenException();
        }
    }

    private boolean isAdminRole(String login) {
        return Role.ROLE_ADMIN.equals(findUserByLogin(login).getRoleGroup());
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
