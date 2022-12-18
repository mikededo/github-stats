package io.pakland.mdas.githubstats.application.internal;

import io.pakland.mdas.githubstats.domain.entity.Commit;
import io.pakland.mdas.githubstats.domain.repository.CommitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SaveAllCommits {

    private final CommitRepository commitRepository;

    SaveAllCommits(CommitRepository commitRepository) {
        this.commitRepository = commitRepository;
    }

    @Transactional
    public void execute(List<Commit> commits) {
        commitRepository.saveAll(commits);
    }
}
