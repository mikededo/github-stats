package io.pakland.mdas.githubstats.domain.model;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@Entity
@Table(name = "comment")
public class Comment {

  @Id
  @Column(updatable = false, nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private UserReview userReview;

  private int length;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Comment )) return false;
    return id != null && id.equals(((Comment) o).getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

}
