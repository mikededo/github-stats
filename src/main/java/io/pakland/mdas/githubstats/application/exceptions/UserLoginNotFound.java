package io.pakland.mdas.githubstats.application.exceptions;

public class UserLoginNotFound extends Exception {
    public UserLoginNotFound(String login) {
        super("UserLogin: " + login + " not found");
    }
}
