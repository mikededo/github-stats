package io.pakland.mdas.githubstats.infrastructure.controller;

import io.pakland.mdas.githubstats.infrastructure.shell.model.ShellRequest;

public abstract class Middleware {

    private Middleware next;

    public static Middleware link(Middleware first, Middleware... chain) {
        Middleware head = first;
        for (Middleware nextInChain: chain) {
            head.next = nextInChain;
            head = nextInChain;
        }
        return first;
    }

    public abstract String execute(ShellRequest request);

    protected String checkNext(ShellRequest request) {

        if (next == null) {
            return "unknow middleware";
        }
        return next.execute(request);
    }
}
