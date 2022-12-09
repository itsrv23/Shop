package ru.got.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.got.shop.model.AdsComment;

import java.util.Optional;
import java.util.Collection;

public interface AdsCommentRepository extends JpaRepository<AdsComment, Integer> {
}
