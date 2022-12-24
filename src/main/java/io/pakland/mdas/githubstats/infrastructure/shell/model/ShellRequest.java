package io.pakland.mdas.githubstats.infrastructure.shell.model;

import io.pakland.mdas.githubstats.domain.enums.EntityType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ShellRequest {

    private String apiKey;

    private EntityType entityType;

    private String name;

    private Date from;

    private Date to;
}
