package com.wikipedia.tests.stepdefinitions;

import com.wikipedia.tests.support.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

public class NfrSecurityStepDefinitions {

    private final TestContext context;
    private String zapOutput;
    private int    zapExitCode;

    public NfrSecurityStepDefinitions(TestContext context) {
        this.context = context;
    }

    @Given("the mock server is available for security scanning")
    public void mockAvailableForScan() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(context.baseUrl + "/index.html"))
            .GET().build();
        HttpResponse<String> res = client.send(req,
            HttpResponse.BodyHandlers.ofString());
        assertEquals(200, res.statusCode(),
            "Mock server not reachable for security scan: " + context.baseUrl);
    }

    @When("OWASP ZAP runs a baseline scan against the login page")
    public void zapBaselineScan() throws Exception {
        // Note: first run pulls ~1.5 GB image — may take several minutes
        String target = context.baseUrl + "/";
        ProcessBuilder pb = new ProcessBuilder(
            "docker", "run", "--rm", "--network", "host",
            "ghcr.io/zaproxy/zaproxy:stable",
            "zap-baseline.py", "-t", target, "-I");
        pb.redirectErrorStream(true);
        Process p = pb.start();
        zapOutput   = new String(p.getInputStream().readAllBytes());
        zapExitCode = p.waitFor();
        System.out.println("ZAP exit code: " + zapExitCode);
        System.out.println("ZAP output:\n" + zapOutput);
        // Remove ZAP image after scan to recover disk space
        try {
            new ProcessBuilder("docker", "rmi", "ghcr.io/zaproxy/zaproxy:stable")
                .redirectErrorStream(true).start().waitFor();
            System.out.println("ZAP image removed from local Docker cache.");
        } catch (Exception ignored) {}
    }

    @Then("the scan completes without high-risk alerts")
    public void scanNoHighRiskAlerts() {
        // ZAP baseline exit codes:
        // 0 = no alerts  1 = warnings only  2 = high-risk found  3 = error
        assertTrue(zapExitCode < 2,
            "OWASP ZAP found high-risk alerts (exit code "
            + zapExitCode + "). Review ZAP output above.");
    }
}
