package io.pakland.mdas.githubstats.infrastructure.github.model;

import io.pakland.mdas.githubstats.domain.enums.PullRequestState;

public class GitHubPageablePullRequestRequest extends GitHubPageableRequest {
    private PullRequestState state;

    public GitHubPageablePullRequestRequest(PullRequestState state, Integer page, Integer perPage) {
        super(page, perPage);
        this.state = state;
    }

    @Override
    public String getRequestUriWithParameters() {
        return String.format(
            "state=%s&per_page=%d&page=%d",
            state.toString(),
            this.perPage,
            this.page
        );
    }
}
