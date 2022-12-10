package io.pakland.mdas.githubstats.infrastructure.github.repository;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.User;
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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserGitHubRepositoryTest {
    private MockWebServer mockWebServer;
    private UserGitHubRepository userGitHubRepository;
    private String teamMembersListResponse;

    private final Integer organizationId = 119930124;
    private final Integer teamId = 7098104;

    @BeforeAll
    void setup() throws IOException {
        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start();
        WebClientConfiguration webClientConfiguration = new WebClientConfiguration(mockWebServer.url("/").toString(), "test-api-key");
        this.userGitHubRepository = new UserGitHubRepository(webClientConfiguration);
        this.teamMembersListResponse = new String(Files.readAllBytes(Paths.get("src/test/java/io/pakland/mdas/githubstats/infrastructure/github/repository/responses/TeamMembers.json")));
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

        userGitHubRepository.fetchUsersFromTeam(this.organizationId, this.teamId);

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals(String.format("/orgs/%d/teams/%d/members", this.organizationId, this.teamId), request.getPath());
    }

    @Test
    void givenValidTeamId_shouldReturnTeamMembers() throws HttpException {

        MockResponse mockResponse = new MockResponse()
                .setBody(this.teamMembersListResponse)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        mockWebServer.enqueue(mockResponse);

        List<User> response = userGitHubRepository.fetchUsersFromTeam(this.organizationId, this.teamId);
        List<User> expected = new ArrayList<>();
        expected.add(0, new User(33031570, "manerow", null, new ArrayList<>(), new ArrayList<>()));
        expected.add(1, new User(48334745, "mikededo", null, new ArrayList<>(), new ArrayList<>()));
        expected.add(2, new User(54351560, "sdomingobasora", null, new ArrayList<>(), new ArrayList<>()));

        assertEquals(3, response.size());
        assertArrayEquals(response.toArray(), expected.toArray());
    }
}
