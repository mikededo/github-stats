package io.pakland.mdas.githubstats.application.mappers;

import io.pakland.mdas.githubstats.application.dto.OrganizationDTO;
import io.pakland.mdas.githubstats.domain.Organization;

public class OrganizationMapper {

    public static Organization dtoToEntity(OrganizationDTO dto) {
        return Organization.builder().id(dto.getId()).login(dto.getLogin()).build();
    }
}
