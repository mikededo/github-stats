package io.pakland.mdas.githubstats.infrastructure.shell.validation;

public interface InputValidator<T> {

    boolean validate(T input);

}
