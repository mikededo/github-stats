package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.application.exceptions.UserLoginNotFound;
import io.pakland.mdas.githubstats.domain.entity.User;
import io.pakland.mdas.githubstats.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class GetUserByLogin {

    private final UserRepository userRepository;

    public GetUserByLogin(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public User execute(String login) throws UserLoginNotFound {
        Optional<User> user = userRepository.findUserByLogin(login);
        if(user.isEmpty()) {
            throw new UserLoginNotFound(login);
        }
        return user.get();
    }
}
