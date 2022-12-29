package io.pakland.mdas.githubstats.application.dto;

import java.util.Date;

public interface ReviewDTO {

    Integer getId();

    UserDTO getUser();

    Date getSubmittedAt();
}
