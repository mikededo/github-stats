package io.pakland.mdas.githubstats.application.mappers;

import io.pakland.mdas.githubstats.application.dto.ReviewDTO;
import io.pakland.mdas.githubstats.domain.entity.Review;

public class ReviewMapper {

    public static Review dtoToEntity(ReviewDTO dto) {
        return Review.builder()
            .id(dto.getId())
            .user(UserMapper.dtoToEntity(dto.getUser()))
            .build();
    }
}
