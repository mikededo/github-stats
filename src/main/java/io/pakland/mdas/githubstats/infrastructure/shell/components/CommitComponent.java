package io.pakland.mdas.githubstats.infrastructure.shell.components;

import org.springframework.context.annotation.Bean;
import org.springframework.shell.command.CommandRegistration;
import org.springframework.shell.standard.ShellComponent;

@ShellComponent
public class CommitComponent {

    public void commits(String user) {
        System.out.println("commits");
    }

    @Bean
    CommandRegistration commitCommandRegistration() {
        CommitComponent commitComponent = new CommitComponent();

        return CommandRegistration.builder()
            .command("commits")
                .description("Number of commits by user")
            .withTarget()
                .method(commitComponent, "commits")
                .and()
            .withOption()
                .shortNames('u')
                .required()
                .type(String.class)
                .and()
            .build();
    }

}
