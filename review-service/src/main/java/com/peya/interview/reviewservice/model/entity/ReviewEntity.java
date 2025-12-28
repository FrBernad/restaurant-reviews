package com.peya.interview.reviewservice.model.entity;

import com.peya.interview.persistance.generator.GeneratedUuidV7;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
    name = "reviews",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "uk_reviews_restaurant_user",
          columnNames = {"restaurant_id", "user_id"})
    },
    indexes = {
      @Index(name = "idx_reviews_restaurant_id", columnList = "restaurant_id"),
      @Index(name = "idx_reviews_user_id", columnList = "user_id")
    })
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewEntity {

  @Id
  @GeneratedUuidV7
  @Column(name = "id", nullable = false, updatable = false, columnDefinition = "uuid")
  private UUID id;

  @Column(name = "restaurant_id", nullable = false, columnDefinition = "uuid")
  private UUID restaurantId;

  @Column(name = "user_id", nullable = false, columnDefinition = "uuid")
  private UUID userId;

  @Column(name = "rating", nullable = false)
  private Integer rating;

  @Column(name = "comment", length = 2000)
  private String comment;

  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;
}
