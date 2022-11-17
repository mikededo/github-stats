package io.pakland.mdas.githubstats.domain.repository;

import io.pakland.mdas.githubstats.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IComment extends JpaRepository<Comment,Integer> {
}
