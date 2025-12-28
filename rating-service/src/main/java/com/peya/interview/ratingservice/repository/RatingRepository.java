package com.peya.interview.ratingservice.repository;

import com.peya.interview.ratingservice.model.entity.RestaurantRatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.UUID;

@Repository
public interface RatingRepository extends JpaRepository<RestaurantRatingEntity, UUID> {

  @Modifying
  @Query(
      value =
          """
              INSERT INTO restaurant_ratings (restaurant_id, rating_sum, rating_count, updated_at)
              VALUES (?1, ?2, 1, ?3)
              ON CONFLICT (restaurant_id)
              DO UPDATE SET
                rating_sum = restaurant_ratings.rating_sum + EXCLUDED.rating_sum,
                rating_count = restaurant_ratings.rating_count + 1,
                updated_at = EXCLUDED.updated_at
              """,
      nativeQuery = true)
  void upsertIncrement(final UUID restaurantId, final Integer rating, final Instant updatedAt);
}
