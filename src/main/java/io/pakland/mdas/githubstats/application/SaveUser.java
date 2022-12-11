package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.domain.User;
import io.pakland.mdas.githubstats.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SaveUser {

    public final UserRepository userRepository;

    public SaveUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void execute(User user) {
        Optional<User> found = userRepository.findById(user.getId());
        if (found.isPresent()) {
            return;
        }
        userRepository.save(user);
    }
}
