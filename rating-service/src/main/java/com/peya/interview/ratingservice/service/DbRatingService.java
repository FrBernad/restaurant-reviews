package com.peya.interview.ratingservice.service;

import com.peya.interview.ratingservice.model.dto.response.RestaurantRatingDto;
import com.peya.interview.ratingservice.repository.RatingRepository;
import com.peya.interview.ratingservice.service.mapper.RestaurantRatingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service("dbRatingService")
@RequiredArgsConstructor
public class DbRatingService implements RatingService {

  private final RatingRepository ratingRepository;
  private final RestaurantRatingMapper restaurantRatingMapper;

  @Override
  @Transactional
  public void applyNewReview(
      final UUID restaurantId, final Integer rating, final Instant eventTime) {
    ratingRepository.upsertIncrement(restaurantId, rating, eventTime);
  }

  @Override
  public RestaurantRatingDto getRating(final UUID restaurantId) {
    return ratingRepository
        .findById(restaurantId)
        .map(restaurantRatingMapper::entityToDto)
        .orElseGet(() -> new RestaurantRatingDto(restaurantId, 0.0, 0L, Instant.now()));
  }
}
