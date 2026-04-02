# TechDeck CoreSuite — Test Strategy

**Document ID:** TS-CORESUITE-001  
**Version:** 1.0.0  
**Date:** April 2026  
**Author:** QA Tester Engineer — Juan Pino  
**Audience:** QA teams, architects, DevOps engineers, auditors, stakeholders  
**Status:** Approved  

---

## 1. Context and Strategic Objective

A large German inspection and certification organisation (TechDeck) is retiring its 20-year-old **Softproject X4 Designer BPMS** and replacing it with **TechDeck CoreSuite**, a modern Java (Spring Boot / Jakarta EE) web application.

The test strategy defines the **why** behind every testing decision: why BDD, why this tool stack, why these test categories, and why this execution order. It governs all test activities from local development through CI to the GitHub Pages live report.

**Strategic objective:** Provide continuous, automated, documented evidence that CoreSuite is functionally correct, performant, secure, accessible, and legally compliant — at every point in the development lifecycle, not only at release time.

---

## 2. Testing Philosophy

### Shift Left
Shift Left means testing as early as possible — instead of testing at the end, we write and agree on test scenarios before any code is written. Problems are caught early when they are cheap to fix, rather than late when they are expensive.

### BDD — Behaviour-Driven Development
We use Cucumber 7 + Gherkin because:

- Scenarios are written in plain English, readable by non-technical stakeholders (auditors, compliance officers, TechDeck management)
- Feature files are living documentation — they describe what the system does, not just whether tests pass
- Changes to the UI only require changes in Page Object classes, not in feature files — keeping maintenance cost low
- Tags (`@smoke`, `@e2e`, `@regression`, `@nfr`, `@docker`, `@mandatory`) allow selective execution at any granularity

### Page Object Model (POM)
Every page of the application has exactly one corresponding Java class in `src/test/java/com/wikipedia/tests/pages/`. No step definition ever references a CSS selector or XPath directly. This means:

- UI changes are fixed in one place, not scattered across dozens of test files
- Page classes can be unit-tested independently
- New pages can be added without touching existing step definitions

### Lazy WebDriver Instantiation
`TestContext.getDriver()` only creates a Chrome instance when a test scenario actually needs a browser. Pure HTTP tests (Smoke Basic, API, Performance) run entirely via Java `HttpClient` — faster, no browser overhead, no display dependency.

---

## 3. Test Layers

| Layer | Purpose | Tools | Duration |
|-------|---------|-------|----------|
| Smoke Basic | HTTP 200 checks, app responds | Java HttpClient | < 5 sec |
| Smoke Advanced | All modules reachable | Selenium | ~1 min |
| E2E Journeys | Complete user workflows | Selenium + Cucumber | ~2 min |
| Regression | Critical paths still work | Selenium + Cucumber | ~2 min |
| NFR Tests | Security, accessibility, GDPR | OWASP ZAP, axe-core | ~1 min |
| Infrastructure | Docker starts/stops correctly | Docker Compose | ~5 min |

Tests run in order from fastest to slowest. If Smoke Basic fails, the build stops immediately to save CI time.
---

## 4. Test Categories — Why, How, When

### 4.1 Smoke Tests — Basic

**Why:** A system that does not respond to HTTP is broken in the most fundamental way. We catch this in under 5 seconds before spending any browser or Docker time.  
**How:** Java `HttpClient` GET requests — no Selenium, no browser process.  
**When:** First test to run in every build. Gate for everything else.

### 4.2 Smoke Tests — Advanced

**Why:** The app may be up but a broken CSS selector or missing navigation element will block all module tests. Catching this before E2E saves significant debugging time.  
**How:** Selenium opens each module from the dashboard and asserts the page title.  
**When:** Immediately after Smoke Basic. Gate for functional and E2E tests.

### 4.3 End-to-End Tests

**Why:** Unit and integration tests cannot catch failures that only appear when multiple system layers interact in a real browser session. E2E tests simulate exactly what an inspector does on day one after go-live.  
**How:** Full Selenium journey: login → dashboard → vehicle inspection → search. The `HighlightHelper` visually marks each element touched — essential for demos and audit evidence.  
**When:** Every build. Mandatory before any release.

### 4.4 Regression Tests

**Why:** Every code change carries the risk of breaking something that worked before. Regression tests guard the highest-value paths (login, dashboard, certification alert, audit statuses) so that no silent regression reaches production.  
**How:** A curated subset of the most critical scenarios from features 01, 03, 08, 09 — fast enough to complete in under 60 seconds.  
**When:** Every push to `master`; every pull request.

### 4.5 On/Off Tests — Docker Lifecycle

**Why:** In cloud and DevOps environments, the ability to start and stop the full application stack reliably is as important as the application itself. A container that starts but leaves orphan volumes or fails health checks is a production incident waiting to happen.  
**How:** `DockerStepDefinitions.java` executes real `docker compose up/down` subprocesses, polls HTTP until healthy (up to 30 seconds), and asserts no containers or services remain after teardown. The full cycle is repeated three times to surface intermittent failures.  
**When:** Every CI build. GitHub Actions `ubuntu-latest` has Docker available by default.

### 4.6 NFR — Performance

**Why:** A system that is functionally correct but too slow to use is not acceptable for production replacement of a system that staff depend on daily.  
**How:** Java `ExecutorService` dispatches 10 concurrent HTTP threads to the login page (equivalent to a JMeter basic load test — no JMeter installation required). Average response time and error rate are measured and asserted.  
**Thresholds:** Average < 1000 ms, error rate < 1 %.  
**When:** Every CI build.

### 4.7 NFR — Security (OWASP ZAP)

**Why:** Enterprise platforms handling certification and inspection data are high-value targets. A passive baseline scan catches the most common vulnerabilities (missing security headers, exposed error messages, cookie flags) before the system goes live.  
**How:** OWASP ZAP baseline scan via Docker image `ghcr.io/zaproxy/zaproxy:stable`. Exit code < 2 = pass. Image is removed from the Docker cache after the scan.  
**When:** Every CI build. The scan is passive (read-only) and takes approximately 60–90 seconds.

### 4.8 NFR — Accessibility (axe-core / WCAG 2.1 / BITV)

**Why:** German law (BITV 2.0, based on WCAG 2.1) requires that enterprise platforms used by public or regulated bodies are accessible to users with disabilities. axe-core is the industry standard automated accessibility testing engine.  
**How:** `AxeBuilder.analyze()` runs on three pages (login, dashboard, vehicle inspection). Critical violations fail the build. Non-critical violations are logged as warnings in the Cucumber report without failing the build.  
**When:** Every CI build.

### 4.9 NFR — DSGVO / GDPR Compliance

**Why:** Any system processing personal data of EU citizens must comply with DSGVO. Hardcoded credentials or plaintext email addresses in the DOM are the most common and easily exploitable compliance failures.  
**How:** DOM inspection via Selenium — no third-party scanning tool required. Three checks: page source does not contain credential patterns or SSN-like data; no password field has a plaintext value; no email input exposes a plaintext address.  
**When:** Every CI build.

### 4.10 Mandatory — Integration and API Contracts

**Why:** In a real Java Spring Boot deployment, the frontend calls REST endpoints. Verifying that all endpoints respond HTTP 200 confirms the integration contract is intact. This category is never skipped regardless of time pressure.  
**How:** Data-driven `Scenario Outline` with Java `HttpClient`. All four module endpoints tested, plus a body assertion confirming the login page contains "CoreSuite".  
**When:** Every CI build. These scenarios are tagged `@mandatory` to signal they may never be excluded.

---

## 5. Tool Selection Rationale

| Tool | Why this tool and not an alternative |
|---|---|
| **Cucumber 7 + Gherkin** | BDD standard; plain English scenarios for non-technical stakeholders; tags for selective execution |
| **Selenium 4** | Industry standard; supports Chromium; BiDi protocol available for future use |
| **axe-core 4.10.1** | Only version with a published Maven Central JAR that works with Selenium 4 (4.7.2 does not exist in Central) |
| **OWASP ZAP (Docker)** | No installation required; reproducible in any CI environment; industry-accepted baseline scan |
| **WebDriverManager** | Eliminates manual ChromeDriver management; handles snap vs system Chromium binary detection |
| **Masterthought** | Best-in-class Cucumber HTML report; features, tags, steps overview; embeds screenshots |
| **Python http.server** | Zero-dependency mock server; serves the static HTML mock app; available on any Linux runner |
| **Docker Compose** | Reproducible environment; health checks; easy start/stop; used in the On/Off test scenarios |
| **GitHub Actions** | Free for public repos; `ubuntu-latest` has Chrome, Docker, Java, Python; direct GitHub Pages integration |

---

## 6. Mandatory Tests — Never Skipped

The following tests are classified as **mandatory** and must pass in every release, regardless of schedule pressure or scope changes:

| Test | Reason it is mandatory |
|---|---|
| Smoke Basic (HTTP 200) | If the app is not up, nothing else matters |
| Login valid flow | The entry point to the entire system |
| All dashboard module cards visible | Loss of navigation breaks all modules |
| Docker On/Off (×3 cycles) | Cloud readiness; production deployment safety |
| OWASP ZAP baseline | Regulatory and audit requirement |
| axe-core accessibility | BITV / WCAG legal requirement in Germany |
| DSGVO DOM inspection | EU legal requirement; personal data protection |
| API contract endpoints (all) | Integration contract; backend readiness |

---

## 7. CI Pipeline Architecture

```
push / PR to master
        │
        ▼
GitHub Actions (ubuntu-latest)
        │
        ├── Set up Java 17 (Temurin)
        ├── Set up Python 3
        ├── Install Chrome stable (non-snap)
        ├── Start mock server (port 8090)
        │
        ├── mvn clean verify
        │     ├── maven-compiler-plugin   → compiles Java 17 sources
        │     ├── maven-surefire-plugin   → runs TestRunner → all 20 features
        │     └── maven-cucumber-reporting → generates Masterthought HTML report
        │
        ├── Stop mock server
        │
        ├── Copy report → gh-pages-content/
        ├── peaceiris/actions-gh-pages → push to gh-pages branch
        │
        └── upload-artifact → cucumber-html-report (30-day retention)
```

---

## 8. Maintenance Guidelines

- **Adding a new module:** Create a new Page Object class, a new feature file (numbered sequentially), and a new step definition file if the step vocabulary is distinct. No changes to existing files.
- **Changing a UI element ID:** Update only the `By` locator in the relevant Page Object class. Feature files and step definitions are unaffected.
- **Changing a port:** Pass `-Dmock.server.port=XXXX` at the command line. No file changes required.
- **Upgrading Selenium or axe-core:** Update the version in `pom.xml` only. Confirm the new version exists in Maven Central before committing.

---


*This strategy is fully aligned with `docs/TEST-PLAN.md`, `README.md`, and `INFRASTRUCTURE.md`.*  
*CoreSuite TS-CORESUITE-001 v1.0.0 — April 2026*