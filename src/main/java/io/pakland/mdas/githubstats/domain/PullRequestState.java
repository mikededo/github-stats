package io.pakland.mdas.githubstats.domain;

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
