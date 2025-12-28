package com.peya.interview.ratingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peya.interview.ratingservice.model.dto.response.RestaurantRatingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Component
public class RedisRatingCache implements RatingCache {

  private final StringRedisTemplate redis;
  private final ObjectMapper objectMapper;
  private final Duration cacheTtl;

  @Autowired
  public RedisRatingCache(
      final StringRedisTemplate redis,
      final ObjectMapper objectMapper,
      @Value("${app.redis.rating-cache.ttl}") final Duration cacheTtl) {
    this.redis = redis;
    this.objectMapper = objectMapper;
    this.cacheTtl = cacheTtl;
  }

  @Override
  public Optional<RestaurantRatingDto> get(final UUID restaurantId) {
    final var key = key(restaurantId);
    final var json = redis.opsForValue().get(key);
    if (json == null) return Optional.empty();
    try {
      return Optional.of(objectMapper.readValue(json, RestaurantRatingDto.class));
    } catch (final JsonProcessingException e) {
      redis.delete(key);
      return Optional.empty();
    }
  }

  @Override
  public void put(final UUID restaurantId, final RestaurantRatingDto value) {
    try {
      redis.opsForValue().set(key(restaurantId), objectMapper.writeValueAsString(value), cacheTtl);
    } catch (final JsonProcessingException ignored) {
    }
  }

  @Override
  public void evict(final UUID restaurantId) {
    redis.delete(key(restaurantId));
  }

  private String key(final UUID restaurantId) {
    return "rating:restaurant:" + restaurantId;
  }
}
