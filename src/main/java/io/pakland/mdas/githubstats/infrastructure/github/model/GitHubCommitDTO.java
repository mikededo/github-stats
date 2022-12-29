package io.pakland.mdas.githubstats.infrastructure.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.pakland.mdas.githubstats.application.dto.CommitDTO;
import io.pakland.mdas.githubstats.application.dto.UserDTO;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GitHubCommitDTO implements CommitDTO {
    @JsonProperty("sha")
    private String sha;

    @JsonProperty("author")
    private GitHubUserDTO user;

    private Integer additions;

    private Integer deletions;

    private Date committedAt;

    @JsonProperty("commit")
    private void unpackDateFromNestedObject(Map<String, Object> commitJson) {
        Map<String, Object> committer = (Map<String, Object>)commitJson.get("committer");
        this.committedAt = Date.from(Instant.parse(committer.get("date").toString()));
    }

    @JsonProperty("stats")
    private void setAdditionDeletionsLines(Map<String, Object> stats) {
        additions = Integer.parseInt(stats.get("additions").toString());
        deletions = Integer.parseInt(stats.get("deletions").toString());
    }

    @Override
    public String getSha() {
        return this.sha;
    }

    @Override
    public UserDTO getUser() {
        return this.user;
    }

    @Override
    public Integer getDeletions() {
        return this.deletions;
    }

    @Override
    public Integer getAdditions() {
        return this.additions;
    }

    public Date getCommittedAt() {
        return this.committedAt;
    }
}
