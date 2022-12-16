package ru.got.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.got.shop.model.Ads;
import ru.got.shop.model.dto.FullAd;

import java.util.List;
import java.util.Optional;

public interface AdsRepository extends JpaRepository<Ads, Integer> {

    String GET_FULL_AD = "SELECT a.id AS pk,\n" +
            "       ua.first_name AS authorFirstName,\n" +
            "       ua.last_name  AS authorLastName,\n" +
            "       ua.phone,\n" +
            "       ua.email,\n" +
            "       a.title,\n" +
            "       a.price,\n" +
            "       a.description\n" +
            "FROM ads a\n" +
            "LEFT JOIN users_account ua ON ua.id = a.user_id\n" +
            "WHERE a.id = ?";

    String FIND_ALL_BY_AUTHOR = "SELECT * FROM ads WHERE user_id = ?";

    @Query(value = GET_FULL_AD, nativeQuery = true)
    Optional<FullAd> getFullAds(Integer id);

    @Query(value = FIND_ALL_BY_AUTHOR, nativeQuery = true)
    List<Ads> findAllAdsByAuthorId(Integer userId);

    List<Ads> findByTitleAndPrice(String title, Integer price);

    Optional<Ads> findByImage(String uuid);
}