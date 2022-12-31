package io.pakland.mdas.githubstats.domain;

public interface Authored {
    boolean isAuthorNamed(String name);
    boolean isAuthorFromEntityTeam();
}
