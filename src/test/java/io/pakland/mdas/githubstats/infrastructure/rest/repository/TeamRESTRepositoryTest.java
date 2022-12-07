package io.pakland.mdas.githubstats.infrastructure.rest.repository;

import io.pakland.mdas.githubstats.application.dto.TeamDTO;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.infrastructure.rest.repository.adapters.TeamRESTRepository;
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
class TeamRESTRepositoryTest {
    private MockWebServer mockWebServer;
    private TeamRESTRepository teamRESTRepository;
    private String organizationTeamsListResponse;

    @BeforeAll
    void setup() throws IOException {
        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start();
        WebClientConfiguration webClientConfiguration = new WebClientConfiguration(mockWebServer.url("/").toString(), "test-api-key");
        this.teamRESTRepository = new TeamRESTRepository(webClientConfiguration);
        this.organizationTeamsListResponse = new String(Files.readAllBytes(Paths.get("src/test/java/io/pakland/mdas/githubstats/infrastructure/rest/repository/responses/OrganizationTeams.json")));
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

        teamRESTRepository.fetchTeamsFromOrganization("github-stats-22");

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals(String.format("/orgs/%s/teams", "github-stats-22"), request.getPath());
    }

    @Test
    void givenValidTeamId_shouldReturnTeamMembers() throws HttpException {

        MockResponse mockResponse = new MockResponse()
                .setBody(this.organizationTeamsListResponse)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        mockWebServer.enqueue(mockResponse);

        List<TeamDTO> response = teamRESTRepository.fetchTeamsFromOrganization("github-stats-22");
        List<TeamDTO> expected = new ArrayList<>();
        expected.add(0, new TeamDTO(7098104, "gs-developers", "gs-developers"));

        assertEquals(response.size(), 1);
        assertArrayEquals(response.toArray(), expected.toArray());
    }
}
