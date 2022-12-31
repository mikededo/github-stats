package io.pakland.mdas.githubstats.application.dto;

import java.util.Date;

public interface ReviewDTO {

    Integer getId();

    String getBody();

    UserDTO getUser();

    Date getSubmittedAt();
}
