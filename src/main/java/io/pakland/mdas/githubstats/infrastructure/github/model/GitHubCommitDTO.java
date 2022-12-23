package io.pakland.mdas.githubstats.infrastructure.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.pakland.mdas.githubstats.application.dto.CommitDTO;
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

    @JsonProperty("user")
    private String user;

    private Integer additions;

    private Integer deletions;

    private Date date;

    @JsonProperty("commit")
    private void unpackDateFromNestedObject(Map<String, Object> commitJson) {
        Map<String, Object> committer = (Map<String, Object>)commitJson.get("committer");
        this.date = Date.from(Instant.parse(committer.get("date").toString()));
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
    public String getUser() {
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

    @Override
    public Date getDate() {
        return this.date;
    }
}
