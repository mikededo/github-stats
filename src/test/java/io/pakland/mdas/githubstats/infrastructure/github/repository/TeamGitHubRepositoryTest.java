package io.pakland.mdas.githubstats.infrastructure.github.repository;

import io.pakland.mdas.githubstats.application.dto.TeamDTO;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;
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
class TeamGitHubRepositoryTest {
    private MockWebServer mockWebServer;
    private TeamGitHubRepository teamGitHubRepository;
    private String organizationTeamsListResponse;

    private final Integer orgId = 119930124;

    @BeforeAll
    void setup() throws IOException {
        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start();
        WebClientConfiguration webClientConfiguration = new WebClientConfiguration(mockWebServer.url("/").toString(), "test-api-key");
        this.teamGitHubRepository = new TeamGitHubRepository(webClientConfiguration);
        this.organizationTeamsListResponse = new String(Files.readAllBytes(Paths.get("src/test/java/io/pakland/mdas/githubstats/infrastructure/github/repository/responses/OrganizationTeams.json")));
    }

    @AfterAll
    void shutDown() throws IOException {
        this.mockWebServer.shutdown();
    }

    @Test
    void givenValidTeamMembersRequest_shouldCallTeamMembersEndpoint() throws InterruptedException, HttpException {
        MockResponse mockResponse = new MockResponse()
                .setBody(this.organizationTeamsListResponse)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        mockWebServer.enqueue(mockResponse);

        teamGitHubRepository.fetchTeamsFromOrganization(orgId);

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals(String.format("/orgs/%d/teams", orgId), request.getPath());
    }

    @Test
    void givenValidTeamId_shouldReturnTeamMembers() throws HttpException {
        Integer teamId = 7098104;
        MockResponse mockResponse = new MockResponse()
                .setBody(this.organizationTeamsListResponse)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        mockWebServer.enqueue(mockResponse);

        List<TeamDTO> response = teamGitHubRepository.fetchTeamsFromOrganization(orgId);
        List<TeamDTO> expected = new ArrayList<>();
        expected.add(0, new TeamDTO(teamId, "gs-developers", "gs-developers"));

        assertEquals(response.size(), 1);
        assertArrayEquals(response.toArray(), expected.toArray());
    }
}
