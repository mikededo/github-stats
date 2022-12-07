package io.pakland.mdas.githubstats.infrastructure.rest.repository;

import io.pakland.mdas.githubstats.application.dto.OrganizationDTO;
import io.pakland.mdas.githubstats.application.exceptions.HttpException;
import io.pakland.mdas.githubstats.infrastructure.rest.repository.adapters.OrganizationRESTRepository;
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
class OrganizationRESTRepositoryTest {

    private MockWebServer mockWebServer;
    private OrganizationRESTRepository organizationRESTRepository;
    private String availableOrganizationsListResponse;

    @BeforeAll
    void setup() throws IOException {
        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start();
        WebClientConfiguration webClientConfiguration = new WebClientConfiguration(mockWebServer.url("/").toString(), "test-api-key");
        this.organizationRESTRepository = new OrganizationRESTRepository(webClientConfiguration);
        this.availableOrganizationsListResponse = new String(Files.readAllBytes(Paths.get("src/test/java/io/pakland/mdas/githubstats/infrastructure/rest/repository/responses/AvailableOrganizations.json")));
    }

    @AfterAll
    void shutDown() throws IOException {
        this.mockWebServer.shutdown();
    }

    @Test
    void givenValidUserOrganizationsRequest_shouldCallUserOrganizationsEndpoint() throws InterruptedException, HttpException {
        MockResponse mockResponse = new MockResponse()
                .setBody(this.availableOrganizationsListResponse)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        mockWebServer.enqueue(mockResponse);

        organizationRESTRepository.fetchAvailableOrganizations();

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals("/user/orgs", request.getPath());
    }

    @Test
    void givenValidGithubAPIKey_shouldReturnAPIKeyUserOrganizations() throws HttpException {

        MockResponse mockResponse = new MockResponse()
                .setBody(this.availableOrganizationsListResponse)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        mockWebServer.enqueue(mockResponse);

        List<OrganizationDTO> response = organizationRESTRepository.fetchAvailableOrganizations();
        List<OrganizationDTO> expected = new ArrayList<>();
        expected.add(new OrganizationDTO(119930124, "github-stats-22"));

        assertArrayEquals(response.toArray(), expected.toArray());
    }
}
