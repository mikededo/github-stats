package io.pakland.mdas.githubstats.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@Entity
@Table(name = "commit")
public class Commit {

  @Id
  @Column(name = "id", updatable = false, nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "user_id")
  private int userId;

  @Column(name = "pull_requests_id")
  private int pullRequestsId;

  @Column(name = "additions")
  private int additions;

  @Column(name = "deletions")
  private int deletions;

  @Column(name = "date")
  @JsonSerialize(using = ToStringSerialize.class)
  private Instant date;

}
