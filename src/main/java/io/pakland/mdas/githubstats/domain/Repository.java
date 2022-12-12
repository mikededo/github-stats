package io.pakland.mdas.githubstats.domain;

import java.util.Map;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "repository")
public class Repository {

  @Id
  @Column(updatable = false, nullable = false)
  @JsonProperty("id")
  private Integer id;

  @Column(name = "name")
  @JsonProperty("name")
  private String name;

  @Column(name = "owner_login")
  private String ownerLogin;

  @ManyToOne(fetch = FetchType.LAZY)
  @ToString.Exclude
  private Team team;

  @OneToMany(
    mappedBy = "repository",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<PullRequest> pullRequests = new ArrayList<>();

  @JsonProperty("owner")
  private void unpackNameFromNestedObject(Map<String, String> owner) {
    this.ownerLogin = owner.get("login");
  }
}
