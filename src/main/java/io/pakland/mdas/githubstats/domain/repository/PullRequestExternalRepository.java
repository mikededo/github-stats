package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.entity.PullRequest;
import io.pakland.mdas.githubstats.domain.entity.PullRequestState;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public interface PullRequestExternalRepository {

    List<PullRequest> fetchPullRequestsFromRepository(FetchPullRequestFromRepositoryRequest request)
        throws HttpException;

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class FetchPullRequestFromRepositoryRequest {

        private String repositoryOwner;
        private String repository;
        private Integer page;
        private Integer perPage;
        private PullRequestState state = PullRequestState.ALL;
    }
}
