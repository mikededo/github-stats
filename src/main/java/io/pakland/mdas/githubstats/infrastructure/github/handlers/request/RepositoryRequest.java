package io.pakland.mdas.githubstats.infrastructure.github.handlers.request;

import io.pakland.mdas.githubstats.application.handlers.Request;
import io.pakland.mdas.githubstats.domain.entity.Repository;

public class RepositoryRequest implements Request {
    private final Repository repository;

    public RepositoryRequest(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Repository getData() {
        return this.repository;
    }
}
