package io.pakland.mdas.githubstats.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
@ToString
public class Commit {

    @JsonProperty("sha")
    private String sha;

    private Date date;

    private User user;

    private PullRequest pullRequest;

    private int additions;

    private int deletions;

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

    // The hashcde must be overridden otherwise it causes a stakoverflow issue
    // when adding a commit to a set like structure
    @Override
    public int hashCode() {
        int result = sha.hashCode();
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + additions;
        result = 31 * result + deletions;
        return result;
    }
}
