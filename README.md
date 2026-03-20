# CoreSuite — Automated Acceptance Tests

> **Java 17 · Maven · Cucumber 7 · Selenium 4 · WebDriverManager · Masterthought**

Automated BDD acceptance test suite for **CoreSuite**, a modern enterprise
inspection and certification platform replacing a 25-year-old legacy system
across vehicle roadworthiness, industrial safety and environmental compliance
domains.

All tests were written and executed with **Visual Studio Code** under **Linux Mint**.

---

![Java](https://img.shields.io/badge/Java-17-007396?logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?logo=apachemaven&logoColor=white)
![Cucumber](https://img.shields.io/badge/Cucumber-7-23D96C?logo=cucumber&logoColor=white)
![Selenium](https://img.shields.io/badge/Selenium-4-43B02A?logo=selenium&logoColor=white)
![Chromium](https://img.shields.io/badge/Chromium-headless-4285F4?logo=googlechrome&logoColor=white)
![Linux Mint](https://img.shields.io/badge/Linux%20Mint-21.2-87CF3E?logo=linuxmint&logoColor=white)
![VS Code](https://img.shields.io/badge/VS%20Code-editor-007ACC?logo=visualstudiocode&logoColor=white)
![CI](https://github.com/PinoLopez/java/actions/workflows/ci.yml/badge.svg)

---

## 📊 Live Test Report

The Masterthought HTML report is published automatically on every push to `master`:

**👉 [View test report on GitHub Pages](https://pinolopez.github.io/java/)**

---

## Project Context

| Item | Detail |
|---|---|
| **Client domain** | Inspection, certification and safety management |
| **Legacy system age** | ~25 years (end-of-life, scheduled for decommission) |
| **New platform** | CoreSuite v3 — Java-based, cloud-ready |
| **Testing strategy** | BDD from day one — acceptance tests drive development |
| **Test scope** | Login, dashboard, 3 inspection domains, certification, audits, admin |
| **Standards covered** | ISO 9001 · ISO 14001 · ISO 45001 · MOT/ITV · TÜV · ADAC |

---

## What this project demonstrates

- BDD test structure using Gherkin feature files (English)
- Page Object Model (POM) — one class per application page
- `TestContext` for shared WebDriver state across steps and hooks
- `Hooks` — `@Before` / `@After` / `@AfterStep` with automatic screenshot on failure
- `HighlightHelper` — elements highlighted with cycling colours during execution
- WebDriverManager — zero-config ChromeDriver management
- Python `http.server` as a lightweight mock app server (no extra infrastructure)
- Masterthought reporting — rich HTML report with feature/scenario breakdown
- GitHub Actions CI — tests run headless on every push
- GitHub Pages — report published automatically after each CI run

---

## Running the tests locally

### Prerequisites
```bash
# Java 17
java -version

# Maven 3.9+
mvn -version

# Chromium (headless — no window opens)
chromium-browser --version
```

### Run all tests
```bash
cd ~/Documents/Java
mvn verify
```

The mock server starts automatically on port 8090, all tests run headless,
the server stops, and the Masterthought report is generated at:
```
cucumber-report/cucumber-html-reports/overview-features.html
```

Open it in your browser:
```bash
xdg-open cucumber-report/cucumber-html-reports/overview-features.html
```

---

## Test scenarios — 10 features, 19 scenarios

| # | Feature file | Module | Scenarios |
|---|---|---|---|
| 01 | `01_login_valid.feature` | Login | Valid credentials → dashboard |
| 02 | `02_login_invalid.feature` | Login | Wrong password + empty fields |
| 03 | `03_dashboard_modules.feature` | Dashboard | All 6 module cards + navigation |
| 04 | `04_vehicle_inspection_overview.feature` | Vehicle | KPIs + records table |
| 05 | `05_vehicle_inspection_search.feature` | Vehicle | Search by plate + button highlight |
| 06 | `06_industrial_safety_risks.feature` | Industrial | Risk cards + assessment table |
| 07 | `07_environmental_sampling.feature` | Environmental | Sample results + filter |
| 08 | `08_certification_management.feature` | Certification | Expiry alert + status badges |
| 09 | `09_audit_reports.feature` | Audits | Report archive + status badges |
| 10 | `10_administration_users.feature` | Admin | User table + system settings |

---

## Project structure
```
.
├── .github/
│   └── workflows/
│       └── ci.yml                  # CI/CD pipeline → GitHub Pages
├── mock-app/                       # Static HTML mock of CoreSuite
│   ├── index.html                  # Login page
│   ├── dashboard.html              # Main dashboard
│   ├── vehicle-inspection.html     # Vehicle module
│   ├── industrial-safety.html      # Industrial safety module
│   ├── environmental.html          # Environmental module
│   ├── certification.html          # Certification module
│   ├── audits.html                 # Audit reports module
│   └── admin.html                  # Administration module
├── src/test/
│   ├── java/com/wikipedia/tests/
│   │   ├── pages/                  # Page Object Model classes (1 per page)
│   │   ├── runners/
│   │   │   └── TestRunner.java     # JUnit Platform suite entry point
│   │   ├── stepdefinitions/
│   │   │   └── StepDefinitions.java
│   │   └── support/
│   │       ├── Hooks.java          # Before / AfterStep / After hooks
│   │       ├── HighlightHelper.java
│   │       └── TestContext.java    # Shared WebDriver + base URL
│   └── resources/
│       ├── features/               # 10 Gherkin feature files
│       └── config/
│           └── cucumber.properties
├── cucumber-report/                # Masterthought report (generated)
└── pom.xml
```

---

## Author

**Juan Pino** — [github.com/PinoLopez](https://github.com/PinoLopez)