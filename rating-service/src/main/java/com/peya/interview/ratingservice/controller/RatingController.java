package com.peya.interview.ratingservice.controller;

import com.peya.interview.ratingservice.model.dto.response.RestaurantRatingDto;
import com.peya.interview.ratingservice.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RatingController {

  private final RatingService ratingService;

  @GetMapping("/{restaurantId}/rating")
  public ResponseEntity<RestaurantRatingDto> getRating(@PathVariable final UUID restaurantId) {
    return ResponseEntity.ok(ratingService.getRating(restaurantId));
  }
}
