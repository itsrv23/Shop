package ru.got.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.got.shop.model.Ads;
import ru.got.shop.model.User;

import java.util.List;

public interface AdsRepository extends JpaRepository<Ads, Integer>, JpaSpecificationExecutor<Ads> {

    List<Ads> findAllByUserId(User user);

}