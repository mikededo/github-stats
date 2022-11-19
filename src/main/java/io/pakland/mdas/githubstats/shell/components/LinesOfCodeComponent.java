package io.pakland.mdas.githubstats.shell.components;

import org.springframework.context.annotation.Bean;
import org.springframework.shell.command.CommandRegistration;
import org.springframework.shell.standard.ShellComponent;

@ShellComponent
public class LinesOfCodeComponent {

    public void locChanges(String user) {
        System.out.println("locChanges");
    }

    @Bean
    CommandRegistration linesOfCodeCommandRegistration() {
        LinesOfCodeComponent linesOfCodeComponent = new LinesOfCodeComponent();

        return CommandRegistration.builder()
            .command("locChanges")
                .description("Lines of code deleted and added")
            .withTarget()
                .method(linesOfCodeComponent, "locChanges")
                .and()
            .withOption()
                .shortNames('u')
                .required()
                .type(String.class)
                .and()
            .build();
    }
}
