package io.pakland.mdas.githubstats.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "organization")
public class Organization {
  @Id
  @Column(updatable = false, nullable = false)
  @JsonProperty("id")
  private Integer id;

  @JsonProperty("login")
  private String login;

  @OneToMany(
    mappedBy = "organization",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<Team> teams = new ArrayList<>();
}
