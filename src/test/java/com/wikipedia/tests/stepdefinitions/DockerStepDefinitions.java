package com.wikipedia.tests.stepdefinitions;

import com.wikipedia.tests.support.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

public class DockerStepDefinitions {

    private static final int DOCKER_PORT   = 8091;
    private static final int MAX_WAIT_SECS = 30;

    private final TestContext context;
    private boolean cycleResult = true;

    public DockerStepDefinitions(TestContext context) {
        this.context = context;
    }

    // ─── Scenario 1 ─────────────────────────────────────────────

    @Given("the docker-compose.yml is present in the project root")
    public void dockerComposeFilePresent() {
        File f = new File(System.getProperty("user.dir"), "docker-compose.yml");
        assertTrue(f.exists(),
            "docker-compose.yml not found at: " + f.getAbsolutePath());
    }

    @When("Docker Compose brings up the stack")
    public void dockerComposeUp() throws Exception {
        runCommand("docker", "compose", "up", "-d", "--build");
        waitForDockerPort();
    }

    @Then("the mock app container reaches healthy status")
    public void containerHealthy() throws Exception {
        String output = captureCommand("docker", "ps",
            "--filter", "name=coresuite-mock",
            "--format", "{{.Status}}");
        assertTrue(output.contains("Up"),
            "Container not in Up state. docker ps output: " + output);
    }

    @Then("the login page responds with HTTP 200 on the Docker port")
    public void loginPageHTTP200Docker() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create("http://127.0.0.1:" + DOCKER_PORT + "/index.html"))
            .GET().build();
        HttpResponse<String> res = client.send(req,
            HttpResponse.BodyHandlers.ofString());
        assertEquals(200, res.statusCode(),
            "Expected HTTP 200 from Docker container on port " + DOCKER_PORT);
    }

    // ─── Scenario 2 ─────────────────────────────────────────────

    @Given("the Docker stack is running")
    public void dockerStackRunning() throws Exception {
        runCommand("docker", "compose", "up", "-d");
        waitForDockerPort();
    }

    @When("Docker Compose brings down the stack with volume cleanup")
    public void dockerComposeDown() throws Exception {
        runCommand("docker", "compose", "down",
            "-v", "--rmi", "all", "--remove-orphans");
    }

    @Then("no CoreSuite containers remain running")
    public void noContainersRemaining() throws Exception {
        String out = captureCommand("docker", "ps", "-a",
            "--filter", "name=coresuite-mock",
            "--format", "{{.Names}}");
        assertTrue(out.trim().isEmpty(),
            "CoreSuite container still present: " + out);
    }

    @Then("no CoreSuite services remain in compose")
    public void noServicesRemaining() throws Exception {
        String out = captureCommand("docker", "compose", "ps", "-q");
        assertTrue(out.trim().isEmpty(),
            "Docker Compose services still running: " + out);
    }

    // ─── Scenario 3 ─────────────────────────────────────────────

    @When("the Docker Compose full cycle is repeated {int} times")
    public void dockerCycleRepeated(int times) throws Exception {
        cycleResult = true;
        for (int i = 1; i <= times; i++) {
            System.out.println("▶ Docker cycle " + i + "/" + times);
            runCommand("docker", "compose", "up", "-d");
            waitForDockerPort();
            runCommand("docker", "compose", "down",
                "-v", "--remove-orphans");
            System.out.println("✓ Cycle " + i + " complete");
        }
    }

    @Then("every cycle reports a clean start and clean stop")
    public void everyCycleClean() {
        assertTrue(cycleResult,
            "One or more Docker Compose cycles failed");
    }

    // ─── Helpers ────────────────────────────────────────────────

    private void runCommand(String... cmd) throws Exception {
        ProcessBuilder pb = new ProcessBuilder(cmd);
        pb.directory(new File(System.getProperty("user.dir")));
        pb.redirectErrorStream(true);
        Process p = pb.start();
        String out = new String(p.getInputStream().readAllBytes());
        int exit = p.waitFor();
        assertEquals(0, exit,
            "Command failed (exit " + exit + "): "
            + String.join(" ", cmd) + "\n" + out);
    }

    private String captureCommand(String... cmd) throws Exception {
        ProcessBuilder pb = new ProcessBuilder(cmd);
        pb.directory(new File(System.getProperty("user.dir")));
        pb.redirectErrorStream(true);
        Process p = pb.start();
        String out = new String(p.getInputStream().readAllBytes());
        p.waitFor();
        return out;
    }

    private void waitForDockerPort() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        for (int i = 0; i < MAX_WAIT_SECS; i++) {
            try {
                HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create("http://127.0.0.1:"
                        + DOCKER_PORT + "/index.html"))
                    .GET().build();
                HttpResponse<String> res = client.send(req,
                    HttpResponse.BodyHandlers.ofString());
                if (res.statusCode() == 200) return;
            } catch (Exception ignored) {}
            Thread.sleep(1000);
        }
        throw new RuntimeException(
            "Docker container did not become healthy within "
            + MAX_WAIT_SECS + " seconds");
    }
}
