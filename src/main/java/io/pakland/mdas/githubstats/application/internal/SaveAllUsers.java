package io.pakland.mdas.githubstats.application.internal;

import io.pakland.mdas.githubstats.domain.entity.User;
import io.pakland.mdas.githubstats.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SaveAllUsers {

    public final UserRepository userRepository;

    public SaveAllUsers(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void execute(List<User> users) {
        userRepository.saveAll(users);
    }

}
