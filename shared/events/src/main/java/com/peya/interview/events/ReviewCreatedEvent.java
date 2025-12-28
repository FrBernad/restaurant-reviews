package com.peya.interview.events;

import java.time.Instant;
import java.util.UUID;

public record ReviewCreatedEvent(
    UUID reviewId, UUID restaurantId, UUID userId, int rating, Instant createdAt) {}
