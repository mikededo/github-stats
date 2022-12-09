package io.pakland.mdas.githubstats;

import io.pakland.mdas.githubstats.application.dto.UserDTO;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.repository.UserExternalRepository;

import java.util.List;

public class FetchUsersFromTeam {
    private UserExternalRepository userExternalRepository;


    public FetchUsersFromTeam(UserExternalRepository userExternalRepository) {
        this.userExternalRepository = userExternalRepository;
    }

     public List<UserDTO> execute(Integer organizationId, Integer teamId) throws HttpException {
        return this.userExternalRepository.fetchUsersFromTeam(organizationId, teamId);
     }
}
