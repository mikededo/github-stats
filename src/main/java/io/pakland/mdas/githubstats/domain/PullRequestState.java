package io.pakland.mdas.githubstats.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PullRequestState {
    @JsonProperty("all")
    ALL {
        @Override
        public String toString() {
            return "all";
        }
    },
    @JsonProperty("open")
    OPEN {
        @Override
        public String toString() {
            return "open";
        }
    },
    @JsonProperty("closed")
    CLOSED {
        @Override
        public String toString() {
            return "closed";
        }
    }
}
