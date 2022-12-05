package io.pakland.mdas.githubstats.infrastructure.repository.github;

import io.pakland.mdas.githubstats.infrastructure.repository.github.adapters.UserRESTRepository;
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
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRESTRepositoryTest {
    private MockWebServer mockWebServer;
    private UserRESTRepository userRESTRepository;
    private String teamMembersListResponse;

    private final String ORG_NAME = "MDASTestOrg";
    private final String TEAM_SLUG = "mdastestteam";

    @BeforeAll
    void setup() throws IOException {
        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start();
        this.userRESTRepository = new UserRESTRepository(
                WebClient.builder(),
                mockWebServer.url("/").toString(),
                "test-api-key"
        );
        this.teamMembersListResponse = new String(Files.readAllBytes(Paths.get("src/test/java/io/pakland/mdas/githubstats/infrastructure/repository/github/responses/TeamMembers.json")));
    }

    @AfterAll
    void shutDown() throws IOException {
        this.mockWebServer.shutdown();
    }

    @Test
    void givenValidTeamMembersRequest_shouldFetchTeamMembersGitHubURL() throws InterruptedException {
        MockResponse mockResponse = new MockResponse()
                .setBody(this.teamMembersListResponse)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        mockWebServer.enqueue(mockResponse);

        userRESTRepository.getUsersFromTeam(this.ORG_NAME, this.TEAM_SLUG);

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals(String.format("/orgs/%s/teams/%s/members", this.ORG_NAME, this.TEAM_SLUG), request.getPath());
    }

    @Test
    void givenValidTeamId_shouldReturnTeamMembers() throws InterruptedException {

        MockResponse mockResponse = new MockResponse()
                .setBody(this.teamMembersListResponse)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        mockWebServer.enqueue(mockResponse);

        List<UserDTO> response = userRESTRepository.getUsersFromTeam(this.ORG_NAME, this.TEAM_SLUG);
        List<UserDTO> expected = new ArrayList<>();
        expected.add(new UserDTO(33031570, "manerow", "https://api.github.com/users/manerow/orgs"));

        assertArrayEquals(response.toArray(), expected.toArray());
    }
}
