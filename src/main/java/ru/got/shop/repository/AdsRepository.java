package ru.got.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.got.shop.model.Ads;
import ru.got.shop.model.User;

import java.util.List;

public interface AdsRepository extends JpaRepository<Ads, Integer> {

    List<Ads> findAllByUserId(User user);

    List<Ads> findByTitleAndPrice(String title, Integer price);

    List<Ads> findAllByTitleIsStartingWithIgnoreCaseOrDescriptionStartingWithIgnoreCaseOrPriceIsGreaterThanEqualOrPriceLessThanEqual(
            String title,
            String description,
            Integer moreThan,
            Integer lessThan);
}