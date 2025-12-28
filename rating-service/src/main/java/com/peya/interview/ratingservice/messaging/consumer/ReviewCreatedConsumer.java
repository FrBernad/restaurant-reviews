package com.peya.interview.ratingservice.messaging.consumer;

import com.peya.interview.events.ReviewCreatedEvent;
import com.peya.interview.ratingservice.service.RatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewCreatedConsumer {

  private final RatingService ratingService;

  @KafkaListener(
      topics = "${app.kafka.topics.review-created}",
      groupId = "${spring.application.name}",
      concurrency = "${app.kafka.consumer.review-created.concurrency:2}")
  public void onReviewCreated(final ReviewCreatedEvent event) {
    log.info("Received ReviewCreatedEvent: {}", event);
    ratingService.applyNewReview(event.restaurantId(), event.rating(), event.createdAt());
  }
}
