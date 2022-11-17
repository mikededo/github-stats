package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUser extends JpaRepository<User,Integer> {

}
