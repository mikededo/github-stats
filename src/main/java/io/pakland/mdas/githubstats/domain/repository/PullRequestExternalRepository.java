package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.entity.PullRequest;
import io.pakland.mdas.githubstats.domain.entity.Repository;

import java.util.Date;
import java.util.List;

public interface PullRequestExternalRepository {

    List<PullRequest> fetchPullRequestsFromRepositoryByPeriodAndPage(
        Repository repository, Date from, Date to, Integer page)
        throws HttpException;
}
