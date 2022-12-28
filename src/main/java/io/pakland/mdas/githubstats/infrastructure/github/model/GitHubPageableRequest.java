package io.pakland.mdas.githubstats.infrastructure.github.model;

public class GitHubPageableRequest {
    protected Integer page;
    protected Integer perPage;

    public GitHubPageableRequest(Integer page, Integer perPage) {
        if (page <= 0 || perPage <= 0) {
            throw new IllegalArgumentException("Github request pagination parameters cannot be < 0!");
        }
        this.page = page;
        this.perPage = perPage;
    }

    public String getRequestUriWithParameters() {
        return String.format("per_page=%d&page=%d", perPage, page);
    }
}
