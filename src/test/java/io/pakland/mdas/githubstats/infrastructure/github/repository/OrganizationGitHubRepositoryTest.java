package io.pakland.mdas.githubstats.infrastructure.github.repository;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.domain.Organization;
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
class OrganizationGitHubRepositoryTest {

    private final Integer organizationId = 119930124;
    private MockWebServer mockWebServer;
    private OrganizationGitHubRepository organizationGithubRepository;
    private String availableOrganizationsListResponse;

    @BeforeAll
    void setup() throws IOException {
        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start();
        WebClientConfiguration webClientConfiguration = new WebClientConfiguration(
            mockWebServer.url("/").toString(), "test-api-key");
        this.organizationGithubRepository = new OrganizationGitHubRepository(
            webClientConfiguration);
        this.availableOrganizationsListResponse = new String(Files.readAllBytes(Paths.get(
            "src/test/java/io/pakland/mdas/githubstats/infrastructure/github/repository/responses/AvailableOrganizations.json")));
    }

    @AfterAll
    void shutDown() throws IOException {
        this.mockWebServer.shutdown();
    }

    @Test
    void givenValidUserOrganizationsRequest_shouldCallUserOrganizationsEndpoint()
        throws InterruptedException, HttpException {
        MockResponse mockResponse = new MockResponse()
            .setBody(this.availableOrganizationsListResponse)
            .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        mockWebServer.enqueue(mockResponse);

        organizationGithubRepository.fetchAvailableOrganizations();

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("/user/orgs", request.getPath());
    }

    @Test
    void givenValidGithubAPIKey_shouldReturnAPIKeyUserOrganizations() throws HttpException {

        MockResponse mockResponse = new MockResponse()
            .setBody(this.availableOrganizationsListResponse)
            .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        mockWebServer.enqueue(mockResponse);

        List<Organization> response = organizationGithubRepository.fetchAvailableOrganizations();
        List<Organization> expected = new ArrayList<>();
        expected.add(new Organization(this.organizationId, "github-stats-22", new HashSet<>()));

        assertArrayEquals(response.toArray(), expected.toArray());
    }
}
