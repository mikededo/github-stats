package io.pakland.mdas.githubstats.domain.model;

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
@Table(name = "repository")
public class Repository {

  @Id
  @Column(updatable = false, nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "organization_id")
  private int organizationId;

  @Column(name = "team_id")
  private int teamId;

  @Column
  private String name;

  @Column
  private String owner;

  @Column(name = "merges_url")
  private String mergesUrl;

  @Column(name = "commits_url")
  private String commitsUrl;

}
