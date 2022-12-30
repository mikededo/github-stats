package io.pakland.mdas.githubstats.domain;

public enum EntityType {
    ORG("org"),
    TEAM("team"),
    USER("user");

    private final String entity;
    EntityType(String entity) {
        this.entity = entity;
    }
    public String getEntity() {return entity;}
}
