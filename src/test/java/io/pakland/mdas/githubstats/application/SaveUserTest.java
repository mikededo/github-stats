package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.domain.User;
import io.pakland.mdas.githubstats.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class SaveUserTest {

    @Test
    public void shouldSaveUser_whenDoesNotExist() {
        User user = new User();
        user.setId(1);
        UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);
        Mockito.when(userRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        new SaveUser(userRepositoryMock).execute(user);

        Mockito.verify(userRepositoryMock, Mockito.times(1)).findById(1);
        Mockito.verify(userRepositoryMock, Mockito.times(1)).save(user);
    }

    @Test
    public void shouldNotSaveUser_whenDoesExist() {
        User user = new User();
        user.setId(1);
        UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);
        Mockito.when(userRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(user));

        new SaveUser(userRepositoryMock).execute(user);

        Mockito.verify(userRepositoryMock, Mockito.times(1)).findById(1);
        Mockito.verify(userRepositoryMock, Mockito.times(0)).save(Mockito.any(User.class));
    }
}
