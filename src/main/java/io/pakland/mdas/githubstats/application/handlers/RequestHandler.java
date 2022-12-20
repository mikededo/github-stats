package io.pakland.mdas.githubstats.application.handlers;

public interface RequestHandler {
    RequestHandler addNext(RequestHandler request);

    void handle(Request request);
}
