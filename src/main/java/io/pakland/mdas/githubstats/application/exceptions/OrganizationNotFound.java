package io.pakland.mdas.githubstats.application.exceptions;

public class OrganizationNotFound extends Exception {
    public OrganizationNotFound(String message) {
        super(message);
    }
}
