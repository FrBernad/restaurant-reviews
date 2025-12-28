package com.peya.interview.ratingservice.client;

import com.peya.interview.ratingservice.model.dto.response.RestaurantRatingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RestRatingClient implements RatingClient {

  private final RestClient restClient;

  @Override
  public RestaurantRatingDto getRating(final UUID restaurantId) {
    return restClient
        .get()
        .uri("/ratings/{id}", restaurantId)
        .retrieve()
        .body(RestaurantRatingDto.class);
  }
}
