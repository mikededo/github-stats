package io.pakland.mdas.githubstats.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@Entity
@Table(name = "user_by_organization")
public class UserOrganization {

  @Column(name = "user_id")
  private int userId;

  @Column(name = "organization_id")
  private int organizationId;
}
