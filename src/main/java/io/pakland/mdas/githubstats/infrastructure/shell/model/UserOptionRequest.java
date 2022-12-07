package io.pakland.mdas.githubstats.infrastructure.shell.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class UserOptionRequest {
    private String userName;
    private String apiKey;
}
