package io.pakland.mdas.githubstats.application.mappers;

import io.pakland.mdas.githubstats.application.dto.TeamDTO;
import io.pakland.mdas.githubstats.domain.Team;

public final class TeamMapper {
    public static Team dtoToEntity(TeamDTO teamDTO) {
        return Team.builder().id(teamDTO.getId()).slug(teamDTO.getSlug()).build();
    }
}
