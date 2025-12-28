package com.peya.interview.reviewservice.service;

import com.peya.interview.events.ReviewCreatedEvent;
import com.peya.interview.reviewservice.messaging.producer.ReviewEventProducer;
import com.peya.interview.reviewservice.model.dto.request.CreateReviewDto;
import com.peya.interview.reviewservice.model.dto.response.ReviewDto;
import com.peya.interview.reviewservice.model.entity.ReviewEntity;
import com.peya.interview.reviewservice.model.exceptions.DuplicateReviewException;
import com.peya.interview.reviewservice.repository.ReviewRepository;
import com.peya.interview.reviewservice.service.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

  private final ReviewRepository reviewRepository;
  private final ReviewEventProducer reviewEventProducer;
  private final ReviewMapper reviewMapper;

  @Override
  @Transactional
  public ReviewDto createReview(final CreateReviewDto request) {
    final var restaurantId = request.restaurantId();
    final var userId = request.userId();

    try {
      final var entity =
          ReviewEntity.builder()
              .restaurantId(restaurantId)
              .userId(userId)
              .rating(request.rating())
              .comment(request.comment())
              .createdAt(Instant.now())
              .build();
      final var savedEntity = reviewRepository.saveAndFlush(entity);

      reviewEventProducer.publishReviewCreated(entityToCreatedEvent(savedEntity));

      return reviewMapper.entityToDto(savedEntity);
    } catch (DataIntegrityViolationException ex) {
      throw new DuplicateReviewException(restaurantId, userId, ex);
    }
  }

  private ReviewCreatedEvent entityToCreatedEvent(final ReviewEntity entity) {
    return new ReviewCreatedEvent(
        entity.getId(),
        entity.getRestaurantId(),
        entity.getUserId(),
        entity.getRating(),
        entity.getCreatedAt());
  }

  @Override
  @Transactional(readOnly = true)
  public Slice<ReviewDto> getReviewsByRestaurant(final UUID restaurantId, final Pageable pageable) {
    return reviewRepository
        .findByRestaurantIdOrderByCreatedAtDesc(restaurantId, pageable)
        .map(reviewMapper::entityToDto);
  }
}
