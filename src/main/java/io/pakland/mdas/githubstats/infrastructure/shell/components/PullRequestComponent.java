package io.pakland.mdas.githubstats.infrastructure.shell.components;

import org.springframework.context.annotation.Bean;
import org.springframework.shell.command.CommandRegistration;
import org.springframework.shell.standard.ShellComponent;

@ShellComponent
public class PullRequestComponent {

    private void prExecuted(String user) {
        System.out.println("prExecuted");
    }

    private void prReviewed(String user) {
        System.out.println("prReviewed");
    }

    private void prCommentLength(String user) {
        System.out.println("prCommentLength");
    }


    @Bean
    CommandRegistration pullRequestCommandRegistration() {
        PullRequestComponent pullRequestComponent = new PullRequestComponent();

        return CommandRegistration.builder()
            .command("prExecuted")
                .description("Number of pull requests executed")
            .withTarget()
                .method(pullRequestComponent, "prExecuted")
                .and()
            .withOption()
                .shortNames('u')
                .required()
                .type(String.class)
                .and()
            .build();
    }

}
