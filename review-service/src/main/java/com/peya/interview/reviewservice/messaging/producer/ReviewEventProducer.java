package com.peya.interview.reviewservice.messaging.producer;

import com.peya.interview.events.ReviewCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReviewEventProducer {

  private final String reviewCreatedTopic;

  private final KafkaTemplate<String, ReviewCreatedEvent> kafkaTemplate;

  @Autowired
  public ReviewEventProducer(
      final KafkaTemplate<String, ReviewCreatedEvent> kafkaTemplate,
      @Value("${app.kafka.topics.review-created}") final String reviewCreatedTopic) {
    this.kafkaTemplate = kafkaTemplate;
    this.reviewCreatedTopic = reviewCreatedTopic;
  }

  public void publishReviewCreated(final ReviewCreatedEvent event) {
    // Partitioning by restaurantId
    kafkaTemplate
        .send(reviewCreatedTopic, event.restaurantId().toString(), event)
        .whenComplete(
            (result, ex) -> {
              if (ex != null) {
                log.error("Failed to publish ReviewCreatedEvent {}", event.reviewId(), ex);
              } else {
                log.info(
                    "Published ReviewCreatedEvent {} to topic/partition: {}/{}",
                    event.reviewId(),
                    result.getRecordMetadata().topic(),
                    result.getRecordMetadata().partition());
              }
            });
  }
}
