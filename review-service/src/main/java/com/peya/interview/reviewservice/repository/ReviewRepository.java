package com.peya.interview.reviewservice.repository;

import com.peya.interview.reviewservice.model.entity.ReviewEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, UUID> {

  boolean existsByRestaurantIdAndUserId(final UUID restaurantId, final UUID userId);

  Slice<ReviewEntity> findByRestaurantIdOrderByCreatedAtDesc(
      final UUID restaurantId, final Pageable pageable);

  Slice<ReviewEntity> findByUserIdOrderByCreatedAtDesc(final UUID userId, final Pageable pageable);
}
