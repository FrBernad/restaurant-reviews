package com.peya.interview.reviewservice.model.exceptions;

import java.util.UUID;

public class DuplicateReviewException extends RuntimeException {

  public DuplicateReviewException(
      final UUID restaurantId, final UUID userId, final Throwable cause) {
    super(
        "Review for restaurant %s by user %s already exists.".formatted(restaurantId, userId),
        cause);
  }
}
