package io.pakland.mdas.githubstats.application.exceptions;

public class HttpException extends Exception {
    public HttpException(Integer code, String message) {
        super("Call returned with code " + code + " and message " + message);
    }
}
