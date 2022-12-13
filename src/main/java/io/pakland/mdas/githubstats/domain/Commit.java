package io.pakland.mdas.githubstats.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private PullRequest pullRequest;

    @Column(name = "date")
    private Date date;

    @JsonProperty("commit")
    private void unpackNameFromNestedObject(Map<String, Map<String, String>> owner) {
        this.date = Date.from(Instant.parse(owner.get("commiter").get("date")));
    }
}
