package io.pakland.mdas.githubstats.infrastructure.postgres;

import io.pakland.mdas.githubstats.domain.EntityType;
import io.pakland.mdas.githubstats.infrastructure.controller.Middleware;
import io.pakland.mdas.githubstats.infrastructure.shell.model.ShellRequest;

public class PostgresMiddleware extends Middleware {

    public PostgresMiddleware(ShellRequest request) {
        super(request);
    }

    @Override
    public String execute() {
        if (super.request.getEntityType().equals(EntityType.USER) &&
            super.request.getName().equals("plozanol")) {
            return "csv from db";
        }
       
        return super.checkNext();
    }
}