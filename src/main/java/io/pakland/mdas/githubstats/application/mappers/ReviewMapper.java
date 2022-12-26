package io.pakland.mdas.githubstats.application.mappers;

import io.pakland.mdas.githubstats.application.dto.UserReviewDTO;
import io.pakland.mdas.githubstats.domain.entity.Review;

public class ReviewMapper {
    public static Review dtoToEntity(UserReviewDTO dto) {
        return Review.builder()
                .id(dto.getId())
                .build();
    }
}
