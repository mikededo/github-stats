package io.pakland.mdas.githubstats.application.mappers;

import io.pakland.mdas.githubstats.application.dto.RepositoryDTO;
import io.pakland.mdas.githubstats.domain.Repository;

public class RepositoryMapper {
  public static Repository dtoToEntity(RepositoryDTO dto) {
    return Repository.builder().id(dto.getId()).name(dto.getName()).build();
  }
}
