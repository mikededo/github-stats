package io.pakland.mdas.githubstats.infrastructure.shell.components;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class LoginComponent {

    private boolean login(@ShellOption(value = {"token"}) String token) {
        return token != null;
    }

}
