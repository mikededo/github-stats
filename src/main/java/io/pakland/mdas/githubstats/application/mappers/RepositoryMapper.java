package io.pakland.mdas.githubstats.application.mappers;

import io.pakland.mdas.githubstats.application.dto.RepositoryDTO;
import io.pakland.mdas.githubstats.domain.entity.Repository;

public class RepositoryMapper {

    public static Repository dtoToEntity(RepositoryDTO dto) {
        return Repository.builder()
            .id(dto.getId())
            .name(dto.getName())
            .ownerLogin(dto.getOwnerLogin())
            .build();
    }

}
