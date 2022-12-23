package io.pakland.mdas.githubstats.application.dto;

import java.util.Date;

public interface CommitDTO {
    String getSha();
    String getUser();
    Integer getAdditions();
    Integer getDeletions();
    Date getDate();
}
