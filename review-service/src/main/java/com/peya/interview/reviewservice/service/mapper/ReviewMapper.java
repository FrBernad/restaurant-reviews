package com.peya.interview.reviewservice.service.mapper;

import com.peya.interview.reviewservice.model.dto.response.ReviewDto;
import com.peya.interview.reviewservice.model.entity.ReviewEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
  ReviewDto entityToDto(final ReviewEntity entity);
}
