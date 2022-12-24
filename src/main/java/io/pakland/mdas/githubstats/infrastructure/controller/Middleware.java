package io.pakland.mdas.githubstats.infrastructure.controller;

import io.pakland.mdas.githubstats.infrastructure.shell.model.ShellRequest;

public abstract class Middleware {

    protected final ShellRequest request;

    private Middleware next;

    protected Middleware(ShellRequest request) {
        this.request = request;
    }

    public static Middleware link(Middleware first, Middleware... chain) {
        Middleware head = first;
        for (Middleware nextInChain: chain) {
            head.next = nextInChain;
            head = nextInChain;
        }
        return first;
    }

    public abstract String execute();

    protected String checkNext() {
        if (next == null) {
            return "unknow middleware";
        }
        return next.execute();
    }
}
