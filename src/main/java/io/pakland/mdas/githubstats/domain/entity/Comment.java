package io.pakland.mdas.githubstats.domain.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    private Integer id;

    private UserReview userReview;

    private String body;

    public Integer getLength() {
        return this.body.length();
    }
}
