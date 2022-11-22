package io.pakland.mdas.githubstats.domain;

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
@Table(name = "team")
public class Team {

  @Id
  @Column(name = "id", updatable = false, nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "organization_id")
  private int organizationId;

  @Column(name = "slug")
  private String slug;

  @Column(name = "member_url")
  private String memberUrl;

  @Column(name = "repository_url")
  private String repositoryUrl;
}
