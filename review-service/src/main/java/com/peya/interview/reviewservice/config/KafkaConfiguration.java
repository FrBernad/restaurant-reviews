package com.peya.interview.reviewservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration {

  @Bean
  public NewTopic reviewCreatedTopic(
      @Value("${app.kafka.topics.review-created}") final String reviewCreatedTopic) {
    return TopicBuilder.name(reviewCreatedTopic).partitions(3).replicas(1).build();
  }
}
