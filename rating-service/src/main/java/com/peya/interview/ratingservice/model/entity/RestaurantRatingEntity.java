package com.peya.interview.ratingservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "restaurant_ratings")
public class RestaurantRatingEntity {

  @Id
  @Column(name = "restaurant_id", nullable = false, columnDefinition = "uuid")
  private UUID restaurantId;

  @Column(name = "rating_sum", nullable = false)
  private long ratingSum;

  @Column(name = "rating_count", nullable = false)
  private long ratingCount;

  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt;
}
