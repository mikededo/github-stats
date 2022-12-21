package io.pakland.mdas.githubstats.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @Column(updatable = false, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserReview userReview;

    private int length;
}
