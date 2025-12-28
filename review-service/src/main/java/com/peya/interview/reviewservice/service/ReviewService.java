package com.peya.interview.reviewservice.service;

import com.peya.interview.reviewservice.model.dto.request.CreateReviewDto;
import com.peya.interview.reviewservice.model.dto.response.ReviewDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.UUID;

public interface ReviewService {
  ReviewDto createReview(final CreateReviewDto request);

  Slice<ReviewDto> getReviewsByRestaurant(final UUID restaurantId, final Pageable pageable);
}
