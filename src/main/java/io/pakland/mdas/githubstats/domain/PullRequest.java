package io.pakland.mdas.githubstats.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pull_request")
public class PullRequest {
  @Id
  @Column(updatable = false, nullable = false)
  @JsonProperty("id")
  private Integer id;

  @Column(name="number")
  @JsonProperty("number")
  private Integer number;

  @Column
  @JsonProperty("state")
  private PullRequestState state;

  @OneToMany(
    mappedBy = "pullRequest",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<UserReview> userReviews = new ArrayList<>();

  @OneToMany(
    mappedBy = "pullRequest",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<Commit> commits = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  private Repository repository;
}
