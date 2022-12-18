package io.pakland.mdas.githubstats.application.external;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.entity.User;
import io.pakland.mdas.githubstats.domain.repository.UserExternalRepository;

import java.util.List;

public class FetchUsersFromTeam {
    private final UserExternalRepository userExternalRepository;


    public FetchUsersFromTeam(UserExternalRepository userExternalRepository) {
        this.userExternalRepository = userExternalRepository;
    }

     public List<User> execute(String organizationName, String teamName) throws HttpException {
        return this.userExternalRepository.fetchUsersFromTeam(organizationName, teamName);
     }
}
