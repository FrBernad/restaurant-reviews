package com.peya.interview.ratingservice.service;

import com.peya.interview.ratingservice.model.dto.response.RestaurantRatingDto;

import java.time.Instant;
import java.util.UUID;

public interface RatingService {

  void applyNewReview(final UUID restaurantId, final Integer rating, final Instant eventTime);

  RestaurantRatingDto getRating(final UUID restaurantId);
}
