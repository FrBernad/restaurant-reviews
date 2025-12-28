package com.peya.interview.ratingservice.service;

import com.peya.interview.ratingservice.model.dto.response.RestaurantRatingDto;
import com.peya.interview.ratingservice.model.entity.RestaurantRatingEntity;
import com.peya.interview.ratingservice.repository.RatingRepository;
import com.peya.interview.ratingservice.service.mapper.RestaurantRatingMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DbRatingServiceTest {

  private static final UUID RESTAURANT_ID = UUID.randomUUID();

  @Mock private RatingRepository ratingRepository;
  @Mock private RestaurantRatingMapper restaurantRatingMapper;

  @InjectMocks private DbRatingService dbRatingService;

  @Nested
  class MethodApplyNewReview {

    @Test
    void shouldInvokeUpsertIncrementOnRepository() {
      dbRatingService.applyNewReview(UUID.randomUUID(), 5, Instant.now());

      verify(ratingRepository)
          .upsertIncrement(any(UUID.class), any(Integer.class), any(Instant.class));
    }
  }

  @Nested
  class MethodGetRating {

    @Nested
    class WhenRatingDoesNotExistInRepository {

      @Test
      void shouldReturnDefaultRestaurantRatingDto() {
        when(ratingRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        final var result = dbRatingService.getRating(RESTAURANT_ID);

        assertThat(result.averageRating()).isEqualTo(0.0);
        assertThat(result.ratingCount()).isEqualTo(0L);
        assertThat(result.restaurantId()).isEqualTo(RESTAURANT_ID);
      }
    }

    @Nested
    class WhenRatingExistsInRepository {

      @Test
      void shouldMapEntityToDtoAndReturnIt() {
        final var mockRating = mock(RestaurantRatingEntity.class);
        when(ratingRepository.findById(any(UUID.class))).thenReturn(Optional.of(mockRating));
        final var mockRatingDto = mock(RestaurantRatingDto.class);
        when(restaurantRatingMapper.entityToDto(any(RestaurantRatingEntity.class)))
            .thenReturn(mockRatingDto);

        final var result = dbRatingService.getRating(RESTAURANT_ID);
        assertThat(result).isEqualTo(mockRatingDto);
      }
    }
  }
}
