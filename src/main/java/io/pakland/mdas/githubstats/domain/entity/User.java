package io.pakland.mdas.githubstats.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {

  @Id
  @Column(updatable = false, nullable = false)
  private Integer id;

  @Column(name = "login")
  private String login;

  @ManyToOne(fetch = FetchType.LAZY)
  private Team team;

  @OneToMany(
    mappedBy = "user",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<UserReview> userReviews = new ArrayList<>();

  @OneToMany(
    mappedBy = "user",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<Commit> commits = new ArrayList<>();

  @OneToMany(
    mappedBy = "user",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<PullRequest> pullRequests = new ArrayList<>();
}
