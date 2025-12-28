package com.peya.interview.reviewservice.service;

import com.peya.interview.events.ReviewCreatedEvent;
import com.peya.interview.reviewservice.messaging.producer.ReviewEventProducer;
import com.peya.interview.reviewservice.model.dto.request.CreateReviewDto;
import com.peya.interview.reviewservice.model.dto.response.ReviewDto;
import com.peya.interview.reviewservice.model.entity.ReviewEntity;
import com.peya.interview.reviewservice.model.exceptions.DuplicateReviewException;
import com.peya.interview.reviewservice.repository.ReviewRepository;
import com.peya.interview.reviewservice.service.mapper.ReviewMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceImplTest {

  @Mock private ReviewRepository reviewRepository;
  @Mock private ReviewEventProducer reviewEventProducer;
  @Mock private ReviewMapper reviewMapper;

  @InjectMocks private ReviewServiceImpl reviewService;

  @Nested
  class MethodCreateReview {

    @Nested
    class WhenUserAlreadyReviewedRestaurant {

      @Test
      @SneakyThrows
      void shouldThrowDuplicateReviewExceptionAndNotPublishEvent() {
        when(reviewRepository.saveAndFlush(any(ReviewEntity.class)))
            .thenThrow(DataIntegrityViolationException.class);

        assertThatThrownBy(() -> reviewService.createReview(mock(CreateReviewDto.class)))
            .isInstanceOf(DuplicateReviewException.class);

        verify(reviewEventProducer, never()).publishReviewCreated(any());
      }
    }

    @Nested
    class WhenUserDidNotReviewedRestaurant {

      @Test
      void shouldCreateEnqueueAndReturnReview() {

        final var createReviewDto = mock(CreateReviewDto.class);
        when(reviewRepository.saveAndFlush(any(ReviewEntity.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));
        final var reviewDtoMock = mock(ReviewDto.class);
        when(reviewMapper.entityToDto(any(ReviewEntity.class))).thenReturn(reviewDtoMock);

        final var result = reviewService.createReview(createReviewDto);

        assertThat(result).isEqualTo(reviewDtoMock);
        verify(reviewEventProducer, times(1)).publishReviewCreated(any(ReviewCreatedEvent.class));
      }
    }
  }
}
