package io.pakland.mdas.githubstats.infrastructure.rest.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.infrastructure.rest.repository.adapters.UserGitHubRepository;
import io.pakland.mdas.githubstats.application.dto.UserDTO;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserGitHubRepositoryTest {
    private MockWebServer mockWebServer;
    private UserGitHubRepository userGitHubRepository;
    private String teamMembersListResponse;

    @BeforeAll
    void setup() throws IOException {
        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start();
        WebClientConfiguration webClientConfiguration = new WebClientConfiguration(mockWebServer.url("/").toString(), "test-api-key");
        this.userGitHubRepository = new UserGitHubRepository(webClientConfiguration);
        this.teamMembersListResponse = new String(Files.readAllBytes(Paths.get("src/test/java/io/pakland/mdas/githubstats/infrastructure/rest/repository/responses/TeamMembers.json")));
    }

    @AfterAll
    void shutDown() throws IOException {
        this.mockWebServer.shutdown();
    }

    @Test
    void givenValidTeamMembersRequest_shouldCallTeamMembersEndpoint() throws InterruptedException, HttpException {
        MockResponse mockResponse = new MockResponse()
                .setBody(this.teamMembersListResponse)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        mockWebServer.enqueue(mockResponse);

        userGitHubRepository.fetchUsersFromTeam("github-stats-22", "gs-developers");

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals(String.format("/orgs/%s/teams/%s/members", "github-stats-22", "gs-developers"), request.getPath());
    }

    @Test
    void givenValidTeamId_shouldReturnTeamMembers() throws HttpException {

        MockResponse mockResponse = new MockResponse()
                .setBody(this.teamMembersListResponse)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        mockWebServer.enqueue(mockResponse);

        List<UserDTO> response = userGitHubRepository.fetchUsersFromTeam("github-stats-22", "gs-developers");
        List<UserDTO> expected = new ArrayList<>();
        expected.add(0, new UserDTO(33031570, "manerow", "https://api.github.com/users/manerow/orgs"));
        expected.add(1, new UserDTO(48334745, "mikededo", "https://api.github.com/users/mikededo/orgs"));
        expected.add(2, new UserDTO(54351560, "sdomingobasora", "https://api.github.com/users/sdomingobasora/orgs"));

        assertEquals(3, response.size());
        assertArrayEquals(response.toArray(), expected.toArray());
    }
}
