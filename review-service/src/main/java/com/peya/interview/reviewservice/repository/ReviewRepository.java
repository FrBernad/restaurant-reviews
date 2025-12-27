package com.peya.interview.reviewservice.repository;

import com.peya.interview.reviewservice.repository.entity.ReviewEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReviewRepository extends JpaRepository<ReviewEntity, UUID> {

  boolean existsByRestaurantIdAndUserId(final UUID restaurantId, final UUID userId);

  Slice<ReviewEntity> findByRestaurantIdOrderByCreatedAtDesc(
      final UUID restaurantId, final Pageable pageable);

  Slice<ReviewEntity> findByUserIdOrderByCreatedAtDesc(final UUID userId, final Pageable pageable);
}
