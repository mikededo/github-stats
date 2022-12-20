package io.pakland.mdas.githubstats.application.handlers;

public interface RequestHandler {
    void addNext(RequestHandler request);

    void handle(Request request);
}
