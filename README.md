# TechDeck CoreSuite v1 — Automated Acceptance Tests


Automated BDD (behaviour-driven, plain-English test scripts) acceptance test suite for **TechDeck CoreSuite v1**, the new enterprise
inspection and certification platform built to replace **Softproject X4 BPMS**,
a 25-year-old legacy system, across vehicle roadworthiness, industrial safety
and environmental compliance domains.

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

The Masterthought HTML report is published automatically on every push to `master` —
a clickable test report that anyone can check in a few seconds.
Click any feature or scenario inside the report to expand its full step-by-step outcome:

**👉 [View live test report on GitHub Pages](https://pinolopez.github.io/java/)**

The report shows each of the 19 scenarios with pass/fail status, duration,
step-level detail, and — if a scenario fails — an embedded screenshot taken
at the exact moment of failure.

---

## 📚 Documentation

- **[TEST-STRATEGY-TechDeck.md](docs/TEST-STRATEGY-TechDeck.md)** – Why we test this way, how, and when
- **[TEST-PLAN-TechDeck.md](docs/TEST-PLAN-TechDeck.md)** – Full test categories, execution plan and status

---

## Project Context

| Item | Detail |
|---|---|
| **Legacy system** | Softproject X4 BPMS — in production for ~25 years |
| **New platform** | TechDeck CoreSuite v1 — Java-based, cloud-ready replacement |
| **Migration window** | 5-year parallel-run and cutover plan |
| **Testing strategy** | BDD (behaviour-driven, plain-English test scripts) from day one — acceptance tests drive development |
| **Test scope** | Login, dashboard, 3 inspection domains, certification, audits, admin |
| **Standards covered** | ISO 9001 · ISO 14001 · ISO 45001 · TÜV · ADAC · DSGVO |

---

## Testing Strategy

The suite is structured to cover all major quality assurance categories
relevant to an enterprise migration project:

| Type | Description | Covered in this suite |
|---|---|---|
| **Smoke – Basic** | Verify the system starts and the main entry point is reachable | Feature 01 — login |
| **Smoke – Advanced** | Verify all primary modules load and key UI elements are present | Feature 03 — dashboard cards |
| **End-to-End (E2E)** | Full user journey from login through a module to a result | Features 04, 06, 07, 08, 09 |
| **Regression** | Re-run after any code change to confirm existing behaviour is unbroken | All 10 features run on every push via CI |
| **On/Off (Docker)** | Full environment starts cleanly, health checks pass, stops and removes all containers and volumes without leaving orphans | Docker Compose pipeline — planned |
| **NFR** | Checks that the app is fast, stable, secure and easy to use — non-functional focused | CI timeout · Hooks · Page Object Model |
| **Always mandatory** | Integration, API, Security, Usability, Compliance, Smoke-in-Production | Planned in release gates |

---

## What this project demonstrates

- BDD (behaviour-driven, plain-English test scripts) structure using Gherkin feature files with `Given / When / Then` syntax
- Page Object Model (POM) — one class per application page, UI selectors centralised
- `TestContext` — shared WebDriver state injected into every step and hook class
- `Hooks` — `@Before` / `@After` / `@AfterStep` lifecycle management:
  - `@Before` — logs the scenario name before it starts
  - `@AfterStep` — captures a PNG screenshot on step failure and embeds it directly into the Masterthought report
  - `@After` — quits the browser and logs pass/fail status
- `HighlightHelper` — applies a coloured background and border to each tested element via JavaScript injection; visible in failure screenshots embedded in the report
- WebDriverManager — zero-config ChromeDriver management, no manual driver download
- Python `http.server` — lightweight mock app server, no extra infrastructure needed
- Masterthought reporting — a clickable test report that anyone can check in a few seconds, with feature/scenario/step breakdown, each scenario expandable
- GitHub Actions CI — all tests run headless on every push in under 3 minutes
- GitHub Pages — live report updated automatically after each CI run

---

## Running the tests locally

### Prerequisites
```bash
java -version      # must be 17+
mvn -version       # must be 3.9+
python3 --version  # for the mock server
chromium-browser --version  # or chromium
```

### Preview the mock app

**👉 [Open live demo](https://pinolopez.github.io/java/mock-app/)** — no installation needed, works in any browser.

Login credentials are shown directly on the login page: **inspector** / **suite2024**

This is the simulated TechDeck CoreSuite v1 interface the tests run against.
It has 8 screens: Login, Dashboard, Vehicle Inspection, Industrial Safety,
Environmental Testing, Certification, Audit Reports and Administration.

> **Developers only — run locally instead:**
> ```bash
> cd mock-app
> python3 -m http.server 8090
> ```
> Then open **http://localhost:8090**

### Run all tests
```bash
cd ~/Documents/Java
mvn verify
```

The mock server starts automatically, all 19 scenarios run headless,
the server stops, and the Masterthought report is generated at:
```
cucumber-report/cucumber-html-reports/overview-features.html
```

Open it:
```bash
xdg-open cucumber-report/cucumber-html-reports/overview-features.html
```

---

## Test scenarios — 10 features, 19 scenarios

| # | Feature file | Module | What is tested |
|---|---|---|---|
| 01 | `01_login_valid.feature` | Login | Valid credentials → dashboard reached |
| 02 | `02_login_invalid.feature` | Login | Wrong password and empty fields → error shown, form stays visible |
| 03 | `03_dashboard_modules.feature` | Dashboard | All 6 module cards present + navigation link works |
| 04 | `04_vehicle_inspection_overview.feature` | Vehicle | KPIs (Key Performance Indicators — summary counters for total, passed, failed and pending inspections) visible + records table populated |
| 05 | `05_vehicle_inspection_search.feature` | Vehicle | Plate number search filters the table; the "New Inspection" button is located and highlighted (background colour applied via JavaScript so it appears in failure screenshots) |
| 06 | `06_industrial_safety_risks.feature` | Industrial | Risk assessment cards (summary panels grouping hazards by severity — High/Medium/Low) visible with correct risk level labels + assessment table present |
| 07 | `07_environmental_sampling.feature` | Environmental | Sample results table shows both compliant and non-compliant readings; type filter available |
| 08 | `08_certification_management.feature` | Certification | Expiry alert banner visible; status badges (coloured labels — ACTIVE / EXPIRING / EXPIRED — indicating certificate lifecycle state) present for all three states |
| 09 | `09_audit_reports.feature` | Audits | Report archive shows FINAL, IN REVIEW and DRAFT statuses |
| 10 | `10_administration_users.feature` | Admin | User table lists expected accounts; system settings fields visible |

---

## Project structure
```
.
├── .github/
│   └── workflows/
│       └── ci.yml                  # CI/CD pipeline → GitHub Pages
├── mock-app/                       # Static HTML mock of TechDeck CoreSuite v1
│   ├── index.html                  # Login page
│   ├── dashboard.html              # Main dashboard with 6 module cards
│   ├── vehicle-inspection.html     # Vehicle inspection module
│   ├── industrial-safety.html      # Industrial safety module
│   ├── environmental.html          # Environmental testing module
│   ├── certification.html          # Certification management module
│   ├── audits.html                 # Audit reports module
│   └── admin.html                  # User and system administration
├── src/test/
│   ├── java/com/wikipedia/tests/
│   │   ├── pages/                  # Page Object Model — 1 class per page
│   │   ├── runners/
│   │   │   └── TestRunner.java     # JUnit Platform suite entry point
│   │   ├── stepdefinitions/
│   │   │   └── StepDefinitions.java
│   │   └── support/
│   │       ├── Hooks.java          # @Before / @AfterStep / @After lifecycle
│   │       ├── HighlightHelper.java # JS colour injection for tested elements
│   │       └── TestContext.java    # Shared WebDriver + base URL + DI root
│   └── resources/
│       ├── features/               # 10 Gherkin feature files
│       └── config/
│           └── cucumber.properties
├── cucumber-report/                # Masterthought report output (generated)
└── pom.xml
```

---

## Legacy vs new system

| Aspect | Softproject X4 BPMS (legacy) | TechDeck CoreSuite v1 (new) |
|---|---|---|
| Age | ~25 years | New build |
| Architecture | Monolithic, on-premise | Modular, cloud-ready |
| Test coverage | Manual only | BDD (behaviour-driven, plain-English test scripts) from day one |
| Reporting | None | Masterthought + CI |
| Browser automation | None | Selenium 4 + Chromium |

---

## Author

**Juan Pino** — [github.com/PinoLopez](https://github.com/PinoLopez)
