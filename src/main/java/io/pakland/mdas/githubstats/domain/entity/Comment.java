package io.pakland.mdas.githubstats.domain.entity;

import java.util.Date;
import lombok.*;

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
