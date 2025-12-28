package com.peya.interview.ratingservice.service;

import com.peya.interview.ratingservice.model.dto.response.RestaurantRatingDto;

import java.util.Optional;
import java.util.UUID;

public interface RatingCache {
  Optional<RestaurantRatingDto> get(final UUID restaurantId);

  void put(final UUID restaurantId, final RestaurantRatingDto value);

  void evict(final UUID restaurantId);
}
