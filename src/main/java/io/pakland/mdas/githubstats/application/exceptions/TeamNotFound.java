package io.pakland.mdas.githubstats.application.exceptions;

public class TeamNotFound extends Exception{
    public TeamNotFound(String team) {
        super("Team: " + team + ", has no repositories");
    }
}
