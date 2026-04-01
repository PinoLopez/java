# CoreSuite Test Strategy (High-Level Approach)

**Objective:** Ensure the new Java web app is reliable, performant, secure, accessible, and DSGVO-compliant before replacing the legacy Softproject X4 Designer BPMS.

**Scope:** Complete functional + non-functional coverage of login, dashboard, vehicle/industrial/environmental inspections, certification, audits, admin, and all integration points.

**Approach:** BDD (Cucumber 7 + Gherkin) + Page Object Model + Selenium 4 (Chromium) + Docker lifecycle validation.

**Test Categories & Strategy:**

- **Smoke Tests Basic** → Fast HTTP health checks (key endpoints)
- **Smoke Tests Advanced** → Navigation and module reachability
- **End-to-End Tests** → Complete business journeys (e.g. full vehicle inspection)
- **Regression Tests** → Critical paths guarded after every change
- **On/Off Tests** → Docker Compose start/stop + health checks (3 cycles)
- **NFR (Non-Functional Tests)** → Performance (JMeter), Security (OWASP ZAP), Accessibility (axe-core), DSGVO compliance
- **Mandatory Tests** → API contract validation + integration points

**Tools & Environment:**
- Local / CI: Mock-app (Python http.server simulating the Java UI)
- Docker Compose with health checks
- CI pipeline runs in < 5 minutes and auto-publishes the Masterthought Cucumber report to GitHub Pages

**Alignment:** This document, TEST-PLAN.md and README.md are fully synchronized.
