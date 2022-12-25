package io.pakland.mdas.githubstats.application.mappers;

import io.pakland.mdas.githubstats.application.dto.UserReviewDTO;
import io.pakland.mdas.githubstats.domain.entity.UserReview;

public class ReviewMapper {
    public static UserReview dtoToEntity(UserReviewDTO dto) {
        return UserReview.builder()
                .id(dto.getId())
                .build();
    }
}
