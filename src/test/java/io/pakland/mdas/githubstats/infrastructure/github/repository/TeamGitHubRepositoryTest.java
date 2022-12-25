package io.pakland.mdas.githubstats.infrastructure.github.repository;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.entity.Organization;
import io.pakland.mdas.githubstats.domain.entity.Team;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TeamGitHubRepositoryTest {

    private final String organizationName = "github-stats-22";
    private final String teamName = "gs-developers";
    private MockWebServer mockWebServer;
    private TeamGitHubRepository teamGitHubRepository;
    private String organizationTeamsListResponse;

    @BeforeAll
    void setup() throws IOException {
        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start();
        WebClientConfiguration webClientConfiguration = new WebClientConfiguration(
            mockWebServer.url("/").toString(), "test-api-key");
        this.teamGitHubRepository = new TeamGitHubRepository(webClientConfiguration);
        this.organizationTeamsListResponse = new String(Files.readAllBytes(Paths.get(
            "src/test/java/io/pakland/mdas/githubstats/infrastructure/github/repository/responses/OrganizationTeams.json")));
    }

    @AfterAll
    void shutDown() throws IOException {
        this.mockWebServer.shutdown();
    }

    @Test
    void givenValidTeamMembersRequest_shouldCallTeamMembersEndpoint()
        throws InterruptedException, HttpException {
        MockResponse mockResponse = new MockResponse()
            .setBody(this.organizationTeamsListResponse)
            .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        mockWebServer.enqueue(mockResponse);

        teamGitHubRepository.fetchTeamsFromOrganization(
            Organization.builder().login(this.organizationName).build());

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals(String.format("/orgs/%s/teams", this.organizationName), request.getPath());
    }

    @Test
    void givenValidTeamName_shouldReturnTeamMembers() throws HttpException {
        MockResponse mockResponse = new MockResponse()
            .setBody(this.organizationTeamsListResponse)
            .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        mockWebServer.enqueue(mockResponse);

        List<Team> response = teamGitHubRepository.fetchTeamsFromOrganization(
            Organization.builder().login(this.organizationName).build());
        List<Team> expected = new ArrayList<>();
        expected.add(0,
            new Team(7098104, teamName, null, new HashSet<>(), new HashSet<>()));

        assertEquals(1, response.size());
        assertArrayEquals(response.toArray(), expected.toArray());
    }
}
