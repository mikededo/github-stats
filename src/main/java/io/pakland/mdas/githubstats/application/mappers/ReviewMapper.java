package io.pakland.mdas.githubstats.application.mappers;

import io.pakland.mdas.githubstats.application.dto.ReviewDTO;
import io.pakland.mdas.githubstats.domain.Review;

public class ReviewMapper {

    public static Review dtoToEntity(ReviewDTO dto) {
        return Review.builder()
            .id(dto.getId())
            .body(dto.getBody())
            .user(UserMapper.dtoToEntity(dto.getUser()))
            .submittedAt(dto.getSubmittedAt())
            .build();
    }
}
