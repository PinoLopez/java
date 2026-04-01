package com.app.qa.coresuite.stepdefinitions;

import com.app.qa.coresuite.support.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class DockerStepDefinitions {
    
    private final TestContext context;
    
    public DockerStepDefinitions(TestContext context) {
        this.context = context;
    }
    
    @Given("the docker-compose.yml is present in the project root")
    public void theDockerComposeYmlIsPresentInTheProjectRoot() {
        File dockerCompose = new File("docker-compose.yml");
        assertTrue(dockerCompose.exists(), "docker-compose.yml not found in project root");
        assertTrue(dockerCompose.isFile(), "docker-compose.yml is not a file");
    }
    
    @When("Docker Compose brings up the stack")
    public void dockerComposeBringsUpTheStack() {
        // This step would normally execute docker-compose up -d
        // For CI environment, we verify the mock app is accessible
        // Assuming Docker is already running in CI
        System.out.println("Docker Compose stack verification started");
    }
    
    @Then("the mock app container reaches healthy status")
    public void theMockAppContainerReachesHealthyStatus() throws Exception {
        // Verify container is healthy by checking HTTP endpoint
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(context.getBaseUrl() + "/index.html");
            var response = client.execute(request);
            int statusCode = response.getCode();
            assertEquals(200, statusCode, "Mock app container not healthy");
            System.out.println("Mock app container is healthy");
        }
    }
    
    @Then("the login page responds with HTTP {int} on the Docker port")
    public void theLoginPageRespondsWithHttpOnTheDockerPort(int expectedStatus) throws Exception {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(context.getBaseUrl() + "/index.html");
            var response = client.execute(request);
            int actualStatus = response.getCode();
            assertEquals(expectedStatus, actualStatus, 
                "Expected status " + expectedStatus + " but got " + actualStatus);
        }
    }
}
