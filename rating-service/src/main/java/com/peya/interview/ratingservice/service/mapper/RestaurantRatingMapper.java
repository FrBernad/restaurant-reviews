package com.peya.interview.ratingservice.service.mapper;

import com.peya.interview.ratingservice.model.dto.response.RestaurantRatingDto;
import com.peya.interview.ratingservice.model.entity.RestaurantRatingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Mapper(componentModel = "spring")
public interface RestaurantRatingMapper {

  @Mapping(
      target = "averageRating",
      expression = "java(calculateAverage(entity.getRatingSum(), entity.getRatingCount()))")
  RestaurantRatingDto entityToDto(RestaurantRatingEntity entity);

  default double calculateAverage(long ratingSum, long ratingCount) {
    if (ratingCount == 0) {
      return 0.0;
    }

    return BigDecimal.valueOf(ratingSum)
        .divide(BigDecimal.valueOf(ratingCount), 1, RoundingMode.HALF_UP)
        .doubleValue();
  }
}
