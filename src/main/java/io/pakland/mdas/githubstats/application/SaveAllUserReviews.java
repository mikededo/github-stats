package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.domain.UserReview;
import io.pakland.mdas.githubstats.domain.repository.UserReviewRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class SaveAllUserReviews {

    public final UserReviewRepository userReviewRepository;

    public SaveAllUserReviews(UserReviewRepository userReviewRepository) {
        this.userReviewRepository = userReviewRepository;
    }

    @Transactional
    public void execute(List<UserReview> userReviews) {
        userReviewRepository.saveAll(userReviews);
    }

}
