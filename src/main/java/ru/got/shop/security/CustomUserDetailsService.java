package ru.got.shop.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.got.shop.model.User;
import ru.got.shop.repository.UserRepository;
import ru.got.shop.service.impl.UserServiceImpl;

import javax.persistence.EntityNotFoundException;

@Service()
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findFirstByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(UserServiceImpl.NOT_EXIST));
        return SecurityUser.fromUser(user);
    }
}
