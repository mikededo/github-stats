package io.pakland.mdas.githubstats.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
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
@Entity
@Table(name = "commit")
public class Commit {

    @Id
    @Column(updatable = false, nullable = false)
    @JsonProperty("sha")
    private String sha;

    @Column(name = "date")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
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
}
