package ru.got.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.got.shop.model.Ad;
import ru.got.shop.model.User;

import java.util.List;

public interface AdRepository extends JpaRepository<Ad, Integer>, JpaSpecificationExecutor<Ad> {

    List<Ad> findAllByUserId(User user);

}