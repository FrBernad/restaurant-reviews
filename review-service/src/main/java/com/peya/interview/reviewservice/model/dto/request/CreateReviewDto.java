package com.peya.interview.reviewservice.model.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateReviewDto(
    @NotNull UUID restaurantId,
    @NotNull UUID userId,
    @Min(1) @Max(5) int rating,
    @Size(max = 2000) String comment) {}
