package io.pakland.mdas.githubstats.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    private Integer id;

    private Review review;

    private String body;

    private Date createdAt;

    public Integer getLength() {
        return this.body.length();
    }
}
