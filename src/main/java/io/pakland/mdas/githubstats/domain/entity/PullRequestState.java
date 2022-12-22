package io.pakland.mdas.githubstats.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PullRequestState {
    ALL {
        @Override
        public String toString() {
            return "all";
        }
    },
    OPEN {
        @Override
        public String toString() {
            return "open";
        }
    },
    CLOSED {
        @Override
        public String toString() {
            return "closed";
        }
    }
}
