package io.pakland.mdas.githubstats.application.external;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.entity.Commit;
import io.pakland.mdas.githubstats.domain.entity.DateRange;
import io.pakland.mdas.githubstats.domain.entity.PullRequest;
import io.pakland.mdas.githubstats.domain.repository.CommitExternalRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class FetchCommitsFromPullRequestInDateRange {

    private final CommitExternalRepository commitExternalRepository;

    public FetchCommitsFromPullRequestInDateRange(
        CommitExternalRepository commitExternalRepository) {
        this.commitExternalRepository = commitExternalRepository;
    }

    public List<Commit> execute(PullRequest pullRequest, DateRange range) throws HttpException {
        int page = 1, responseResults = 0;
        List<Commit> commitList = new ArrayList<>();

        do {
            List<Commit> apiResults = this.commitExternalRepository
                .fetchCommitsFromPullRequestByPage(pullRequest, page);
            if (apiResults.size() == 0) {
                break;
            }

            Instant first = apiResults.get(0).getCommittedAt().toInstant(),
                last = apiResults.get(apiResults.size() - 1).getCommittedAt().toInstant();

            // If first is before the start of the range, then we should not keep fetching the
            // data since all will be outside the range
            if (range.isPreviousToRange(first)) {
                break;
            }

            // If the last one is after the date range, we still need to fetch to check if there
            // are older results that are in range
            if (range.isFollowingToRange(last)) {
                responseResults = apiResults.size();
                page++;
                continue;
            }

            if (range.isBetweenRange(first) && range.isBetweenRange(last)) {
                commitList.addAll(apiResults);
            } else {
                // However, if first one is after the range but the last isn't, we have to filter the
                // commits that are within the range.
                // We have to do the same thing if the last is not in range but the first is
                // Also if the first is after but the last is previous, there may be commits in between
                // that are within the range
                commitList.addAll(apiResults.parallelStream()
                    .filter(commit -> range.isBetweenRange(commit.getCommittedAt().toInstant()))
                    .toList());
            }

            responseResults = apiResults.size();
            page++;
        } while (responseResults != 100);
        pullRequest.addCommits(commitList);

        return commitList;
    }
}
