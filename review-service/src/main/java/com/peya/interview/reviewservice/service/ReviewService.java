package com.peya.interview.reviewservice.service;

import com.peya.interview.reviewservice.model.dto.request.CreateReviewDto;
import com.peya.interview.reviewservice.model.dto.response.ReducedSlice;
import com.peya.interview.reviewservice.model.dto.response.ReviewDto;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ReviewService {
  ReviewDto createReview(final CreateReviewDto request);

  ReducedSlice<ReviewDto> getReviewsByRestaurant(final UUID restaurantId, final Pageable pageable);
}
