package io.pakland.mdas.githubstats.infrastructure.rest.repository;

import io.pakland.mdas.githubstats.infrastructure.rest.repository.adapters.UserRESTRepository;
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
        WebClientConfiguration webClientConfiguration = new WebClientConfiguration(mockWebServer.url("/").toString(), "test-api-key");
        this.userRESTRepository = new UserRESTRepository(webClientConfiguration);
        this.teamMembersListResponse = new String(Files.readAllBytes(Paths.get("src/test/java/io/pakland/mdas/githubstats/infrastructure/rest/repository/responses/TeamMembers.json")));
    }

    @AfterAll
    void shutDown() throws IOException {
        this.mockWebServer.shutdown();
    }

    @Test
    void givenValidTeamMembersRequest_shouldCallTeamMembersEndpoint() throws InterruptedException {
        MockResponse mockResponse = new MockResponse()
                .setBody(this.teamMembersListResponse)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        mockWebServer.enqueue(mockResponse);

        userRESTRepository.fetchUsersFromTeam(this.ORG_NAME, this.TEAM_SLUG);

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals(String.format("/orgs/%s/teams/%s/members", this.ORG_NAME, this.TEAM_SLUG), request.getPath());
    }

    @Test
    void givenValidTeamId_shouldReturnTeamMembers() {

        MockResponse mockResponse = new MockResponse()
                .setBody(this.teamMembersListResponse)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        mockWebServer.enqueue(mockResponse);

        List<UserDTO> response = userRESTRepository.fetchUsersFromTeam(this.ORG_NAME, this.TEAM_SLUG);
        List<UserDTO> expected = new ArrayList<>();
        expected.add(new UserDTO(48334745, "mikededo", "https://api.github.com/users/mikededo/orgs"));

        assertArrayEquals(response.toArray(), expected.toArray());
    }
}
