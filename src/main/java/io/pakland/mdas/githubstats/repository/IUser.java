package io.pakland.mdas.githubstats.repository;

import io.pakland.mdas.githubstats.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUser extends JpaRepository<User,Long> {

}
