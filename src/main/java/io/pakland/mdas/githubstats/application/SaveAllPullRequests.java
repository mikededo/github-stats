package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.domain.entity.PullRequest;
import io.pakland.mdas.githubstats.domain.repository.PullRequestRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class SaveAllPullRequests {

    public final PullRequestRepository pullRequestRepository;

    public SaveAllPullRequests(PullRequestRepository pullRequestRepository) {
        this.pullRequestRepository = pullRequestRepository;
    }

    @Transactional
    public void execute(List<PullRequest> pullRequests) {
        pullRequestRepository.saveAll(pullRequests);
    }

}
