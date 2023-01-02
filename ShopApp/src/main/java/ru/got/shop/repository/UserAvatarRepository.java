package ru.got.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.got.shop.model.User;
import ru.got.shop.model.UserAvatar;

import java.util.Optional;
import java.util.UUID;

public interface UserAvatarRepository extends JpaRepository<UserAvatar, UUID> {
    Optional<UserAvatar> findFirstByUser(User user);
}