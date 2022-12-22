package io.pakland.mdas.githubstats.application.mappers;

import io.pakland.mdas.githubstats.application.dto.TeamDTO;
import io.pakland.mdas.githubstats.domain.entity.Team;

public class TeamMapper {
    public static Team dtoToEntity(TeamDTO dto) {
        return Team.builder().id(dto.getId()).slug(dto.getSlug()).build();
    }
}
