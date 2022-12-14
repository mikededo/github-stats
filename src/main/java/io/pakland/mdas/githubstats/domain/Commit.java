package io.pakland.mdas.githubstats.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
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

    @JsonProperty("commit")
    private void unpackDateFromNestedObject(Map<String, Object> commitJson) {
        Map<String, Object> committer = (Map<String, Object>)commitJson.get("committer");
        this.date = Date.from(Instant.parse(committer.get("date").toString()));
    }
}
