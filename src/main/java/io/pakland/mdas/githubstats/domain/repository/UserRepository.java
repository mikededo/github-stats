package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Add jdoc about the rep
 */

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    public Optional<User> findUserByLogin(String login);
}
