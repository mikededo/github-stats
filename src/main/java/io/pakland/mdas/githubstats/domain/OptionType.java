package io.pakland.mdas.githubstats.domain;

public enum OptionType {
    ORGANIZATION("organization"),
    TEAM("team_slug"),
    USER("user_name");

    private final String options;
    OptionType(String options) {
        this.options = options;
    }
    public String getOption() {return options;}
}
