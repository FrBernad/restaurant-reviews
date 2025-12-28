package com.peya.interview.reviewservice.model.dto.response;

import java.time.Instant;
import java.util.UUID;

public record ReviewDto(
    UUID id, UUID restaurantId, UUID userId, int rating, String comment, Instant createdAt) {}
