package io.pakland.mdas.githubstats.domain.entity;

import lombok.*;

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

  private List<Review> reviews = new ArrayList<>();

  private List<Commit> commits = new ArrayList<>();

  private List<PullRequest> pullRequests = new ArrayList<>();
}
