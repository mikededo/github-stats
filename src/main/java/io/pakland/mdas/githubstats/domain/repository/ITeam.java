package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <b>Class</b>: ITeam <br/>
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

@Repository
public interface ITeam extends JpaRepository<Team,Integer> {
}
