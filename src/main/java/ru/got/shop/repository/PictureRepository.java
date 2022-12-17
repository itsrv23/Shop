package ru.got.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.got.shop.model.Picture;

import java.util.Optional;
import java.util.UUID;

public interface PictureRepository extends JpaRepository<Picture, String> {

    Optional<Picture> findByUuid(UUID uuid);
}
