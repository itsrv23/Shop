package ru.got.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.got.shop.model.Ads;

public interface AdsRepository extends JpaRepository<Ads, Integer> {
}