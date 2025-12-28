package com.peya.interview.ratingservice.client;

import com.peya.interview.ratingservice.model.dto.response.RestaurantRatingDto;

import java.util.UUID;

public interface RatingClient {
  RestaurantRatingDto getRating(final UUID restaurantId);
}
