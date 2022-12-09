package io.pakland.mdas.githubstats.application.exceptions;

public class OrganizationNotFound extends Exception {
    public OrganizationNotFound(Integer id) {
        super("Organization with id: " + id + " not found");
    }
}
