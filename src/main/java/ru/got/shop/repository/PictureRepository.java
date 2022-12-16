package ru.got.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.got.shop.model.Picture;

import java.util.Optional;

public interface PictureRepository extends JpaRepository<Picture, String> {

    Optional<Picture> findByAdsId(Integer adsId);
}
