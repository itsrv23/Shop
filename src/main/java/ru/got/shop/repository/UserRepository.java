package ru.got.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.got.shop.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByEmail(String email);
}