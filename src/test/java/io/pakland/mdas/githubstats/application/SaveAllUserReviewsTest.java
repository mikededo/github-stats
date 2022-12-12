package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.domain.UserReview;
import io.pakland.mdas.githubstats.domain.repository.UserReviewRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class SaveAllUserReviewsTest {

    @Test
    public void savingUserReviews_shouldCallRepositoryMethods() {
        List<UserReview> userReviews = new ArrayList<>();
        UserReviewRepository userReviewRepositoryMock = Mockito.mock(UserReviewRepository.class);

        SaveAllUserReviews useCase = new SaveAllUserReviews(userReviewRepositoryMock);
        useCase.execute(userReviews);

        Mockito.verify(userReviewRepositoryMock, Mockito.times(1)).saveAll(Mockito.anyList());
    }
}
