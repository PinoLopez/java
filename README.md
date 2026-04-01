# TechDeck CoreSuite — Automated Acceptance Tests

[![CI](https://github.com/PinoLopez/java/actions/workflows/ci.yml/badge.svg)](https://github.com/PinoLopez/java/actions/workflows/ci.yml)
[![Live Report](https://img.shields.io/badge/Report-GitHub%20Pages-blue)](https://pinolopez.github.io/java/)
[![Mock App](https://img.shields.io/badge/Demo-Mock%20App-green)](https://pinolopez.github.io/java/mock-app/)

> **Context:** A large German inspection and certification organisation (TechDeck) is retiring its 20-year-old Softproject X4 Designer BPMS and replacing it with a modern Java (Spring Boot / Jakarta EE) web application. This repository contains the complete automated acceptance test suite for that replacement.

---

## Live Links

| Resource | URL |
|---|---|
| **Live test report** | https://pinolopez.github.io/java/ |
| **Live mock app demo** | https://pinolopez.github.io/java/mock-app/ |
| **CI pipeline** | https://github.com/PinoLopez/java/actions |

---

## Technology Stack

| Layer | Tool | Version |
|---|---|---|
| Language | Java | 17 |
| BDD framework | Cucumber + Gherkin | 7.15.0 |
| Test runner | JUnit 5 Platform Suite | 5.10.2 |
| Browser automation | Selenium 4 | 4.21.0 |
| Browser | Chromium (headless in CI) | stable |
| Driver management | WebDriverManager | 5.8.0 |
| Accessibility | axe-core | 4.10.1 |
| Security | OWASP ZAP (via Docker) | stable |
| Build + reporting | Maven + Masterthought | 3 / 5.8.1 |
| Mock server | Python http.server | 3.x |
| Containerisation | Docker Compose | v2 |
| CI | GitHub Actions | ubuntu-latest |

---

## Project Structure

```
.
├── mock-app/                        # Static HTML simulation of the Java UI
│   ├── index.html                   # Login page
│   ├── dashboard.html
│   ├── vehicle-inspection.html
│   ├── industrial-safety.html
│   ├── environmental.html
│   ├── certification.html
│   ├── audits.html
│   └── admin.html
├── src/test/
│   ├── java/com/wikipedia/tests/
│   │   ├── pages/                   # Page Object Model (one class per page)
│   │   ├── stepdefinitions/         # Step definitions (one class per feature group)
│   │   ├── support/                 # TestContext, Hooks, HighlightHelper
│   │   └── runners/                 # TestRunner (JUnit Platform Suite)
│   └── resources/
│       ├── config/cucumber.properties
│       └── features/                # 20 Gherkin feature files
├── cucumber-report/                 # Auto-generated Masterthought HTML report
├── docs/
│   ├── TEST-PLAN.md
│   └── TEST-STRATEGY.md
├── INFRASTRUCTURE.md                # Mermaid diagram of the full pipeline
├── docker-compose.yml               # Mock app container with health check
├── pom.xml
└── .github/workflows/ci.yml        # Full CI pipeline
```

---

## Test Categories

| # | Feature file | Category | Tag(s) | Scope |
|---|---|---|---|---|
| 01 | `01_login_valid.feature` | Functional | — | Valid login flow |
| 02 | `02_login_invalid.feature` | Functional | — | Invalid credentials |
| 03 | `03_dashboard_modules.feature` | Functional | — | All 6 module cards |
| 04 | `04_vehicle_inspection_overview.feature` | Functional | — | KPIs + table |
| 05 | `05_vehicle_inspection_search.feature` | Functional | — | Search + filter |
| 06 | `06_industrial_safety_risks.feature` | Functional | — | Risk cards + table |
| 07 | `07_environmental_sampling.feature` | Functional | — | Results + filter |
| 08 | `08_certification_management.feature` | Functional | — | Expiry alert + statuses |
| 09 | `09_audit_reports.feature` | Functional | — | Report statuses |
| 10 | `10_administration_users.feature` | Functional | — | Users + settings |
| 11 | `11_smoke_basic_health.feature` | **Smoke Basic** | `@smoke @smoke-basic` | HTTP 200 checks |
| 12 | `12_smoke_advanced_navigation.feature` | **Smoke Advanced** | `@smoke @smoke-advanced` | Module navigation |
| 13 | `13_e2e_vehicle_full_journey.feature` | **End-to-End** | `@e2e` | Full vehicle journey |
| 14 | `14_regression_critical_paths.feature` | **Regression** | `@regression` | Critical paths |
| 15 | `15_docker_onoff.feature` | **On/Off** | `@docker @onoff` | Docker lifecycle ×3 |
| 16 | `16_nfr_performance.feature` | **NFR** | `@nfr @performance` | 10 concurrent users |
| 17 | `17_nfr_security_owasp.feature` | **NFR** | `@nfr @security @owasp` | OWASP ZAP baseline |
| 18 | `18_nfr_accessibility_wcag.feature` | **NFR** | `@nfr @accessibility @wcag` | axe-core WCAG 2.1 |
| 19 | `19_nfr_dsgvo.feature` | **NFR** | `@nfr @dsgvo @compliance` | GDPR/DSGVO checks |
| 20 | `20_mandatory_integration_api.feature` | **Mandatory** | `@mandatory @integration @api` | API contract + endpoints |

---

## Running the Tests Locally

### Prerequisites

```bash
# Java 17
java -version

# Maven
mvn -version

# Docker (for On/Off and OWASP ZAP tests)
docker -v

# Chromium
chromium-browser --version || /snap/bin/chromium --version
```

### Quick start (mock server only)

```bash
# 1. Start mock server
cd ~/Documents/Java/mock-app
python3 -m http.server 8090 --bind 127.0.0.1 &

# 2. Run full suite
cd ~/Documents/Java
mvn clean verify \
  -Dheadless=true \
  -Dmock.server.port=8090

# 3. Stop mock server
pkill -f "http.server 8090"

# 4. Open the report
xdg-open cucumber-report/cucumber-html-reports/overview-features.html
```

### Docker Compose start (On/Off test environment)

```bash
docker compose up -d
# wait for healthy status
docker compose ps
# run the full suite
mvn clean verify -Dheadless=true -Dmock.server.port=8091
# stop and clean
docker compose down -v --remove-orphans
```

### Run a single tag

```bash
mvn clean verify -Dheadless=true -Dmock.server.port=8090 \
  -Dcucumber.filter.tags="@smoke"
```

### Run headed (visible browser, useful for debugging locally)

```bash
mvn clean verify \
  -Dheadless=false \
  -Dmock.server.port=8090
```

---

## CI Pipeline

The GitHub Actions workflow (`.github/workflows/ci.yml`) runs on every push and pull request to `master`:

1. Checks out the repository
2. Sets up Java 17 (Temurin) and Python 3
3. Installs Chromium (non-snap, stable)
4. Starts the mock server on port 8090
5. Runs `mvn clean verify` (Surefire → Masterthought report generated automatically)
6. Stops the mock server
7. Copies the Masterthought report to `gh-pages` branch
8. Publishes to GitHub Pages (live within ~60 seconds)
9. Uploads the report as a downloadable artifact (retained 30 days)

---

## Design Principles

- **BDD / Gherkin** — plain-English scenarios understood by QA, developers, business and auditors
- **Page Object Model** — UI locators are encapsulated in page classes; step definitions never touch selectors directly
- **HighlightHelper** — every element is scrolled into view and colour-highlighted during headed runs for visual debugging
- **Lazy WebDriver** — `TestContext.getDriver()` creates the browser only when a test actually needs it; pure HTTP tests (smoke, API, performance) run without opening a browser at all
- **Screenshot on failure** — `Hooks.afterStep()` embeds a PNG screenshot into the Cucumber report on every failed step automatically

---

## Docs

- [`docs/TEST-PLAN.md`](docs/TEST-PLAN.md) — complete test plan (scope, categories, environment, exit criteria)
- [`docs/TEST-STRATEGY.md`](docs/TEST-STRATEGY.md) — testing strategy (pyramid, rationale, NFR approach)
- [`INFRASTRUCTURE.md`](INFRASTRUCTURE.md) — Mermaid pipeline diagram

---

## Demo Credentials

| Field | Value |
|---|---|
| Username | `inspector` |
| Password | `suite2024` |

---

*CoreSuite Acceptance Tests — TechDeck v1.0.0 | QA Engineer: Pino Lopez | April 2026*