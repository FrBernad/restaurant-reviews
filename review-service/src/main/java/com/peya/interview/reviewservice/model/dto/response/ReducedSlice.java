package com.peya.interview.reviewservice.model.dto.response;

public record ReducedSlice<T>(Iterable<T> content, boolean hasNext, long pageNumber) {}
