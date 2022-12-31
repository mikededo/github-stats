package io.pakland.mdas.githubstats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("io.pakland.mdas.githubstats.domain.repository")
public class GithubStatsApplication {
  public static void main(String[] args) {
    SpringApplication.run(GithubStatsApplication.class, args);
  }

}
