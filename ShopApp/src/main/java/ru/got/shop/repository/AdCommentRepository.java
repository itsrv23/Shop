package ru.got.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.got.shop.model.AdComment;

public interface AdCommentRepository extends JpaRepository<AdComment, Integer> {
}
