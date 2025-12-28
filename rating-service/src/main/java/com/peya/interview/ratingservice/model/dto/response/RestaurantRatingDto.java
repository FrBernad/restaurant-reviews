package com.peya.interview.ratingservice.model.dto.response;

import java.time.Instant;
import java.util.UUID;

public record RestaurantRatingDto(
    UUID restaurantId, double averageRating, long ratingCount, Instant updatedAt) {}
