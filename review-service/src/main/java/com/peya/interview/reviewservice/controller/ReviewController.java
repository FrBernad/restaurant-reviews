package com.peya.interview.reviewservice.controller;

import com.peya.interview.reviewservice.model.dto.request.CreateReviewDto;
import com.peya.interview.reviewservice.model.dto.response.ReducedSlice;
import com.peya.interview.reviewservice.model.dto.response.ReviewDto;
import com.peya.interview.reviewservice.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

  private final ReviewService reviewService;

  @GetMapping
  public ResponseEntity<ReducedSlice<ReviewDto>> getReviewsByRestaurant(
      @RequestParam final UUID restaurantId,
      @RequestParam final Integer page,
      @RequestParam final Integer size) {
    return ResponseEntity.ok(
        reviewService.getReviewsByRestaurant(restaurantId, PageRequest.of(page, size)));
  }

  @PostMapping
  public ResponseEntity<ReviewDto> createReview(
      @RequestBody @Valid final CreateReviewDto reviewDto) {
    final var review = reviewService.createReview(reviewDto);
    return ResponseEntity.status(HttpStatus.CREATED.value()).body(review);
  }
}
