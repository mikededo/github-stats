package io.pakland.mdas.githubstats.domain.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    private Integer id;

    private Review review;

    private String body;

    public Integer getLength() {
        return this.body.length();
    }
}
