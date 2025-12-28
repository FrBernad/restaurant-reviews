package com.peya.interview.ratingservice.service;

import com.peya.interview.ratingservice.model.dto.response.RestaurantRatingDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
@Primary
public class CachedRatingService implements RatingService {

  private final RatingService delegate;
  private final RatingCache cache;

  @Autowired
  public CachedRatingService(
      @Qualifier("dbRatingService") final RatingService delegate, final RatingCache cache) {
    this.delegate = delegate;
    this.cache = cache;
  }

  @Override
  public void applyNewReview(
      final UUID restaurantId, final Integer rating, final Instant eventTime) {
    delegate.applyNewReview(restaurantId, rating, eventTime);
    cache.evict(restaurantId);
  }

  @Override
  public RestaurantRatingDto getRating(final UUID restaurantId) {
    return cache
        .get(restaurantId)
        .map(
            rating -> {
              log.info("Cache hit for restaurantId {}", restaurantId);
              return rating;
            })
        .orElseGet(
            () -> {
              log.info("Cache miss for restaurantId {}", restaurantId);
              final var rating = delegate.getRating(restaurantId);
              cache.put(restaurantId, rating);
              return rating;
            });
  }
}
