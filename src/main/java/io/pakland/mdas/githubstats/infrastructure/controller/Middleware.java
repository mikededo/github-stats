package io.pakland.mdas.githubstats.infrastructure.controller;

import io.pakland.mdas.githubstats.infrastructure.shell.model.ShellRequest;

import java.io.IOException;

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

    public abstract void execute(ShellRequest request);

    protected void checkNext(ShellRequest request) {

        if (next == null) {
            return;
        }
        next.execute(request);
    }
}
