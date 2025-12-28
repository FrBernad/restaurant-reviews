package com.peya.interview.reviewservice.controller;

import com.peya.interview.reviewservice.model.dto.request.CreateReviewDto;
import com.peya.interview.reviewservice.model.dto.response.ReducedSlice;
import com.peya.interview.reviewservice.model.dto.response.ReviewDto;
import com.peya.interview.reviewservice.service.ReviewService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewControllerImplTest {

  @Mock private ReviewService reviewService;

  @InjectMocks private ReviewController reviewController;

  @Nested
  class MethodGetReviewsByRestaurant {

    @Test
    void shouldGetReviewsByRestaurant() {
      final var mockReviewDto = mock(ReviewDto.class);
      final var paginatedReviews = new SliceImpl<>(List.of(mockReviewDto));
      when(reviewService.getReviewsByRestaurant(any(UUID.class), any(Pageable.class)))
          .thenReturn(
              new ReducedSlice<>(paginatedReviews.getContent(), paginatedReviews.hasNext(), 0));

      final var response = reviewController.getReviewsByRestaurant(UUID.randomUUID(), 0, 10);

      assertThat(response.getStatusCode().value()).isEqualTo(200);
      assertThat(response.getBody()).isNotNull();
      assertThat(response.getBody().content()).hasSize(1);
      assertThat(response.getBody().content()).contains(mockReviewDto);
    }
  }

  @Nested
  class MethodCreateReview {

    @Test
    void shouldCreateReview() {
      final var createReviewDtoMock = mock(CreateReviewDto.class);
      final var reviewDtoMock = mock(ReviewDto.class);
      when(reviewService.createReview(createReviewDtoMock)).thenReturn(reviewDtoMock);

      final var result = reviewController.createReview(createReviewDtoMock);

      assertThat(result.getStatusCode().value()).isEqualTo(201);
      assertThat(result.getBody()).isEqualTo(reviewDtoMock);
    }
  }
}
