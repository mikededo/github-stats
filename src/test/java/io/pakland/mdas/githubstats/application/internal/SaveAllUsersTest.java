package io.pakland.mdas.githubstats.application.internal;

import io.pakland.mdas.githubstats.application.internal.SaveAllUsers;
import io.pakland.mdas.githubstats.domain.entity.User;
import io.pakland.mdas.githubstats.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class SaveAllUsersTest {

    @Test
    public void savingUsers_shouldCallRepositoryMethods() {
        List<User> users = new ArrayList<>();
        UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);

        SaveAllUsers useCase = new SaveAllUsers(userRepositoryMock);
        useCase.execute(users);

        Mockito.verify(userRepositoryMock, Mockito.times(1)).saveAll(Mockito.anyList());
    }

}
