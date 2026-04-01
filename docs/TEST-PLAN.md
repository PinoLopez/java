# CoreSuite Test Plan – Java Web App (Dekra Replacement)

**Version:** 1.0.0  
**Date:** April 2026  
**Author:** QA Tester Engineer (Pino Lopez)  
**Objective:** Replace the 20+ year old Softproject X4 Designer BPMS with a modern Java (Spring Boot / Jakarta EE) web application while ensuring zero regression in functionality, performance, security, accessibility and compliance.

## 1. Scope
Full coverage of:
- Login / Authentication
- Dashboard
- Vehicle Inspection
- Industrial Safety
- Environmental Testing
- Certification Management
- Audit Reports
- Administration
- Integration points (API + Docker lifecycle)

## 2. Test Categories (Mandatory & Always Executed)

| Category                  | Tag(s)                     | Purpose                                      | Execution |
|---------------------------|----------------------------|----------------------------------------------|-----------|
| **Smoke Basic**           | `@smoke @smoke-basic`      | Quick health check (HTTP 200 on key pages)   | Every build |
| **Smoke Advanced**        | `@smoke @smoke-advanced`   | Navigation + module reachability             | Every build |
| **End-to-End**            | `@e2e`                     | Full vehicle inspection journey              | Every build |
| **Regression**            | `@regression`              | Critical paths after any change              | Every build |
| **Docker On/Off**         | `@docker @onoff`           | Docker Compose start/stop + health checks    | Every build |
| **NFR – Performance**     | `@nfr @performance`        | JMeter load tests (response time, error rate)| Every build |
| **NFR – Security**        | `@nfr @security @owasp`    | OWASP ZAP baseline scan                      | Every build |
| **NFR – Accessibility**   | `@nfr @accessibility @wcag`| axe-core WCAG checks                         | Every build |
| **NFR – DSGVO / Compliance** | `@nfr @dsgvo`           | No hardcoded personal data                   | Every build |
| **Mandatory Integration / API** | `@mandatory @integration @api` | API contract + endpoint validation     | Every build |

## 3. Test Environment
- Local: Linux Mint + VS Code + Docker Compose + Python mock server
- CI: GitHub Actions (ubuntu-latest)
- Browser: Chromium (headless in CI)
- Report: Live at https://pinolopez.github.io/java/

## 4. Exit Criteria
- 100 % of mandatory scenarios PASS
- No critical accessibility or security findings
- Docker on/off cycle passes 3 times
- Average response time < 1000 ms, error rate < 1 %
- Report published to GitHub Pages

**Approved by:** QA Lead  
**Sign-off:** Ready for production replacement of Softproject X4
