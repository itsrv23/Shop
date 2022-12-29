package ru.got.shop.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.got.shop.dto.UserDto;
import ru.got.shop.exception.ForbiddenException;
import ru.got.shop.exception.UserNotFoundException;
import ru.got.shop.model.Ads;
import ru.got.shop.model.User;
import ru.got.shop.repository.AdsRepository;
import ru.got.shop.repository.PictureRepository;
import ru.got.shop.repository.UserRepository;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Service
public class PermissionService implements AuthenticationFacade {
    private final UserRepository userRepository;
    private final AdsRepository adsRepository;
    private final PictureRepository pictureRepository;

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

    public boolean adsEditAccessAllowed(Integer id) {
        Ads ads = getAds(id);
        User user =
                userRepository.findFirstByEmail(getLogin()).orElseThrow(() -> new UserNotFoundException(getLogin()));
        if (ads.getUserId().equals(user) || user.getRoleGroup().equals(Role.ROLE_ADMIN)) {
            return true;
        }
        throw new ForbiddenException();
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

    private Ads getAds(Integer id) {
        return adsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found ads with id: ".concat(id.toString())));
    }
}
