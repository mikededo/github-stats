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
public class User {

  private Integer id;

  private String login;

  private Team team;

  private List<UserReview> userReviews = new ArrayList<>();

  private List<Commit> commits = new ArrayList<>();

  private List<PullRequest> pullRequests = new ArrayList<>();
}
