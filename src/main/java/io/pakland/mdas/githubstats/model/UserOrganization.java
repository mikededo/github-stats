package io.pakland.mdas.githubstats.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <b>Class</b>: UserOrganitzation <br/>
 * <b>Copyright</b>: 2022 La Salle - mdas <br/>.
 *
 * @author 2022  MDAS <br/>
 * <u>Service Provider</u>: La Salle <br/>
 * <u>Developed by</u>: mdas <br/>
 * <u>Changes:</u><br/>
 * <ul>
 *   <li>
 *     Septiembre 13, 2022 Creación de Clase.
 *   </li>
 * </ul>
 */

@Data
@NoArgsConstructor
@ToString
@Entity
@Table(name = "user_by_organization")
public class UserOrganization {

  @Column(name = "member_id")
  private String memberId;

  @Column(name = "organization_id")
  private String organizationId;
}
