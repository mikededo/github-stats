package io.pakland.mdas.githubstats.application.dto;

import java.util.Date;

public interface CommentDTO {
    Integer getId();

    String getBody();

    UserDTO getUser();

    Date getCreatedAt();
}
