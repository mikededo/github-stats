package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.entity.Commit;
import io.pakland.mdas.githubstats.domain.entity.PullRequest;
import java.util.List;

public interface CommitExternalRepository {

    List<Commit> fetchCommitsFromPullRequestByPage(PullRequest pr, Integer page)
        throws HttpException;
}
