package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.domain.Commit;
import io.pakland.mdas.githubstats.domain.repository.CommitRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class SaveCommits {

    private final CommitRepository commitRepository;

    SaveCommits(CommitRepository commitRepository) {
        this.commitRepository = commitRepository;
    }

    @Transactional
    public void execute(List<Commit> commits) {
        commitRepository.saveAll(commits);
    }
}
