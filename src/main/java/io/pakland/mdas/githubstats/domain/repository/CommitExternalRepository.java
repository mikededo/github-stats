package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.Commit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public interface CommitExternalRepository {
    List<Commit> fetchCommitsFromPullRequest(FetchCommitsFromPullRequestRequest request) throws HttpException;

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class FetchCommitsFromPullRequestRequest {
        private String repositoryOwner;
        private String repositoryName;
        private Integer pullRequestNumber;
        private Integer page;
        private Integer perPage;
    }
}
