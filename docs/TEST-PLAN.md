# TechDeck CoreSuite — Test Plan

**Document ID:** TP-CORESUITE-001  
**Version:** 1.0.0  
**Date:** April 2026  
**Author:** QA Tester Engineer — Pino Lopez  
**Status:** Approved — Ready for Production Replacement  

---

## 1. Introduction and Objective

This Test Plan governs the complete acceptance testing of the **TechDeck CoreSuite** Java web application, which is replacing the legacy **Softproject X4 Designer BPMS** (in service for over 20 years) at a large German inspection and certification organisation (Dekra-scale).

The objective is to provide documented, repeatable, automated evidence that the new system is functionally correct, performant, secure, accessible, and compliant with EU data protection law (DSGVO/GDPR) before go-live.

---

## 2. Scope

### In scope

- Login and authentication (valid and invalid credential flows)
- Dashboard — all six module cards and navigation
- Vehicle Inspection — KPIs, inspection records table, search and filter
- Industrial Safety — risk assessment cards, risk level labels, assessments table
- Environmental Testing — sample results table, compliance status badges, filter
- Certification Management — expiry alert, certification table with status badges
- Audit Reports — audit archive table with status badges, new report button
- Administration — user management table, system settings panel
- Docker environment lifecycle (start, health check, stop, repeat ×3)
- HTTP API contract validation — all module endpoints return HTTP 200
- Non-Functional Requirements: performance, security, accessibility, DSGVO compliance

### Out of scope

- Database layer (mock app serves static HTML — no backend database in this iteration)
- Payment or billing workflows
- Native mobile applications
- Backend microservice internals (tested via contract only)

---

## 3. Test Categories and Execution Plan

### 3.1 Smoke Tests — Basic (`@smoke @smoke-basic`)

**Purpose:** Fastest possible sanity check that the platform is alive and reachable.  
**Approach:** Pure HTTP GET requests via Java `HttpClient` — no browser required.  
**Scenarios covered:**
- Login endpoint (`/index.html`) returns HTTP 200
- Dashboard endpoint (`/dashboard.html`) returns HTTP 200

**Pass criteria:** Both endpoints respond 200 within 2 seconds.  
**Execution:** Every CI build, every local run, before any deeper testing.

---

### 3.2 Smoke Tests — Advanced (`@smoke @smoke-advanced`)

**Purpose:** Confirm all five non-dashboard modules are reachable and correctly titled after login.  
**Approach:** Selenium navigates to each module via the dashboard; page title is asserted.  
**Scenarios covered:**
- Industrial Safety module reachable, title contains "Industrial Safety"
- Environmental Testing module reachable, title contains "Environmental"
- Certification module reachable, title contains "Certification"
- Audit Reports module reachable, title contains "Audit"
- Administration module reachable, title contains "Administration"

**Pass criteria:** All five modules load without error and titles match.  
**Execution:** Every CI build, immediately after Smoke Basic.

---

### 3.3 End-to-End Tests (`@e2e`)

**Purpose:** Simulate a complete real-user business journey from login to data interaction.  
**Approach:** Full browser automation (Selenium) through the entire vehicle inspection workflow.  
**Scenarios covered:**
- Inspector logs in → dashboard loads → clicks Vehicle Inspection card → module opens → KPI visible → searches for "KLP" → table shows matching records

**Pass criteria:** Every step in the journey passes without assertion failure or timeout.  
**Execution:** Every CI build; before every release.

---

### 3.4 Regression Tests (`@regression`)

**Purpose:** Guard the most critical paths to ensure no existing behaviour is broken by code changes.  
**Approach:** Subset of the highest-risk scenarios re-run after every commit.  
**Scenarios covered:**
- Login with valid credentials still reaches the dashboard
- All six dashboard module cards still present
- Certification expiry alert still visible
- Audit report statuses (FINAL, IN REVIEW, DRAFT) still present

**Pass criteria:** 100 % of regression scenarios pass.  
**Execution:** Every push to `master`; every pull request.

---

### 3.5 On/Off Tests — Docker Lifecycle (`@docker @onoff`)

**Purpose:** Verify the full containerised environment can be started cleanly, passes health checks, and stops without orphan containers or volumes. Mandatory for cloud/DevOps readiness.  
**Approach:** `DockerStepDefinitions.java` spawns real Docker Compose subprocesses and polls HTTP until healthy.  
**Scenarios covered:**
- Docker Compose brings up the stack → container reaches `healthy` status → login page returns HTTP 200 on port 8091
- Docker Compose brings down the stack → no containers remain → no compose services remain
- Full up/down cycle repeated three times — every cycle must pass

**Pass criteria:** All three cycles complete with exit code 0; no containers or volumes remain after teardown.  
**Execution:** Every CI build (Docker is available on `ubuntu-latest`).

---

### 3.6 NFR — Performance (`@nfr @performance`)

**Purpose:** Confirm the platform responds within acceptable thresholds under simulated concurrent load.  
**Approach:** Java `ExecutorService` with 10 concurrent threads each making an HTTP GET to the login page (simulating JMeter behaviour without requiring JMeter to be installed in CI).  
**Thresholds:**
- Average response time **< 1000 ms**
- Error rate **< 1 %**

**Pass criteria:** Both thresholds met across all 10 samples.  
**Execution:** Every CI build.

---

### 3.7 NFR — Security (`@nfr @security @owasp`)

**Purpose:** Confirm no obvious high-risk security vulnerabilities are exposed on the login page.  
**Approach:** OWASP ZAP baseline passive scan via Docker (`ghcr.io/zaproxy/zaproxy:stable`). ZAP exit code 0 or 1 = pass; exit code 2 = high-risk alerts found = fail.  
**Note:** First run downloads ~1.5 GB image; subsequent runs use the cached image. The image is removed from the Docker cache after the scan to recover disk space.

**Pass criteria:** ZAP exit code < 2 (no high-risk alerts).  
**Execution:** Every CI build.

---

### 3.8 NFR — Accessibility (`@nfr @accessibility @wcag`)

**Purpose:** Confirm the platform meets WCAG 2.1 and BITV (German accessibility law) requirements. Mandatory for any public-sector or enterprise German deployment.  
**Approach:** axe-core 4.10.1 via `AxeBuilder.analyze()` on three pages (login, dashboard, vehicle inspection). Critical violations fail the build; non-critical violations are logged as warnings.  
**Scenarios covered:** Login page, Dashboard page, Vehicle Inspection page.

**Pass criteria:** Zero critical axe-core violations on all three pages.  
**Execution:** Every CI build.

---

### 3.9 NFR — DSGVO / GDPR Compliance (`@nfr @dsgvo @compliance`)

**Purpose:** Verify personal data handling meets EU DSGVO (GDPR) requirements. Mandatory for any system processing citizen or employee data in Germany.  
**Approach:** DOM inspection via Selenium — no credential scanning tool required.  
**Scenarios covered:**
- Login page source does not contain hardcoded credential patterns (`password=`, `passwd=`) or SSN-like numeric patterns
- Administration page has no password fields displaying plaintext values
- Administration page has no email input fields exposing plaintext addresses in the DOM

**Pass criteria:** All assertions pass — no personal data exposed in the DOM.  
**Execution:** Every CI build.

---

### 3.10 Mandatory — Integration and API Contracts (`@mandatory @integration @api`)

**Purpose:** Verify all module endpoints respond correctly at the HTTP level, confirming the integration contract between frontend and backend.  
**Approach:** Data-driven `Scenario Outline` with HTTP GET requests to all endpoints via Java `HttpClient`.  
**Endpoints tested:**

| Endpoint | Expected status |
|---|---|
| `/index.html` | 200 |
| `/dashboard.html` | 200 |
| `/vehicle-inspection.html` | 200 |
| `/audits.html` | 200 |

Additionally: smoke-in-production check confirms `/index.html` returns 200 and body contains "CoreSuite".

**Pass criteria:** All endpoints return HTTP 200; body assertion passes.  
**Execution:** Every CI build.

---

## 4. Test Environment

| Component | Local (Linux Mint) | CI (GitHub Actions) |
|---|---|---|
| OS | Linux Mint 21+ | ubuntu-latest |
| Java | 17 (system) | 17 Temurin |
| Browser | Chromium (snap or system) | Chrome stable (non-snap) |
| Mock server | Python `http.server` port 8090 | Python `http.server` port 8090 |
| Docker | Docker CE + Compose v2 | Included in runner |
| IDE | Visual Studio Code | — |
| Report output | `cucumber-report/cucumber-html-reports/` | GitHub Pages (auto-published) |

---

## 5. Entry Criteria

Before test execution begins, all of the following must be true:

- Mock server is running and `/index.html` returns HTTP 200
- Docker is available (for On/Off and OWASP tests)
- `mvn clean verify` completes the compile phase without errors
- All 20 feature files are present in `src/test/resources/features/`

---

## 6. Exit Criteria

The test suite is considered passed when:

- 100 % of mandatory scenarios pass (features 01–15, 20)
- Zero critical axe-core accessibility violations (feature 18)
- OWASP ZAP exit code < 2 (feature 17)
- Docker on/off cycle passes three consecutive times (feature 15)
- Average response time < 1000 ms and error rate < 1 % (feature 16)
- No DSGVO violations detected (feature 19)
- Masterthought HTML report published to GitHub Pages

---

## 7. Risks and Mitigations

| Risk | Impact | Mitigation |
|---|---|---|
| axe-core API changes between versions | Medium | Version pinned to 4.10.1 in `pom.xml` |
| OWASP ZAP image download time in CI | Low | Image cached by Docker layer; removed after scan to save space |
| Chromium snap vs system binary mismatch | High | `TestContext.java` tries multiple binary paths automatically |
| Mock server port conflict locally | Medium | Port is passed as system property; easily overridden |
| Feature file / step mismatch after refactoring | Medium | Every step definition maps to exactly one `@Given/@When/@Then`; Cucumber will error on undefined steps before running |

---

## 8. Defect Management

Defects found during automated test execution are captured as:

- Screenshot embedded in the Cucumber HTML report (via `Hooks.afterStep()` on failure)
- GitHub Actions build marked as failed
- CI artifact (`cucumber-html-report`) available for download for 30 days

---

## 9. Approvals and Sign-off

| Role | Name | Status |
|---|---|---|
| QA Engineer | Pino Lopez | ✅ Approved |
| QA Lead | — | Pending |
| Tech Lead | — | Pending |
| Compliance Officer | — | Pending |

---

*This Test Plan is fully aligned with `docs/TEST-STRATEGY.md`, `README.md`, and `INFRASTRUCTURE.md`.*  
*CoreSuite TP-CORESUITE-001 v1.0.0 — April 2026*