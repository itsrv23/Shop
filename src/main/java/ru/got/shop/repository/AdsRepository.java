package ru.got.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.got.shop.model.Ads;
import ru.got.shop.model.FullAds;

import java.util.Optional;

public interface AdsRepository extends JpaRepository<Ads, Integer> {
    @Query(value = "SELECT a.id AS pk,\n" +
            "       ua.first_name AS authorFirstName,\n" +
            "       ua.last_name AS authorLastName,\n" +
            "       ua.phone,\n" +
            "       ua.email,\n" +
            "       a.title,\n" +
            "       a.price,\n" +
            "       a.description,\n" +
            "       a.image\n" +
            "FROM ads a\n" +
            "LEFT JOIN users_account ua ON ua.id = a.user_id\n" +
            "WHERE a.id = ?", nativeQuery = true)
    Optional<FullAds> getFullAds(Integer id);
}