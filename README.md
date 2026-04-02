# TechDeck CoreSuite — Automated Acceptance Tests

[![CI](https://github.com/PinoLopez/java/actions/workflows/ci.yml/badge.svg)](https://github.com/PinoLopez/java/actions)
[![Live Report](https://img.shields.io/badge/Report-Live%20Now-green)](https://pinolopez.github.io/java/overview-features.html)
[![Mock App](https://img.shields.io/badge/Demo-Try%20It-blue)](https://pinolopez.github.io/java/mock-app/)

> **Production Ready:** Complete automated acceptance test suite for TechDeck CoreSuite Java web application.

- [Live Test Report](https://pinolopez.github.io/java/overview-features.html)
- [Mock App Demo](https://pinolopez.github.io/java/mock-app/)
- [CI Pipeline](https://github.com/PinoLopez/java/actions)
- [Infrastructure Diagram](https://github.com/PinoLopez/java/blob/master/INFRASTRUCTURE.md)

---

## Quick Summary

| Metric | Value |
|--------|-------|
| **Total Tests** | 41 scenarios |
| **Test Duration** | ~3 minutes |
| **Last Run** | Auto-updated on every commit |
| **Coverage** | Login, Dashboard, 6 Modules, NFR, API, Docker |

---

## Test Categories

| Category | What It Tests | Tools | When It Runs |
|----------|---------------|-------|--------------|
| **Smoke Basic** | App starts, HTTP 200 responses | Java HttpClient | First, always |
| **Smoke Advanced** | All modules reachable | Selenium | After basic passes |
| **E2E Journeys** | Complete user workflows | Selenium + Cucumber | Every build |
| **Regression** | Critical paths still work | Selenium + Cucumber | Every build |
| **NFR Tests** | Speed, security, accessibility | OWASP ZAP, axe-core | Every build |
| **Infrastructure** | Docker starts/stops correctly | Docker Compose | Every build |

---

## Test Layers (Simple View)

| Layer | Purpose | Duration |
|-------|---------|----------|
| Smoke Basic | HTTP 200 checks | < 5 sec |
| Smoke Advanced | Module navigation | ~1 min |
| E2E + Regression | Full user journeys | ~2 min |
| NFR Tests | Security, accessibility | ~1 min |

See full diagram: [INFRASTRUCTURE.md](https://github.com/PinoLopez/java/blob/master/INFRASTRUCTURE.md)

---

## How It Works (Fully Automated)

1. Developer pushes code — GitHub Actions starts
2. Docker creates clean test environment
3. All 41 tests run automatically
4. Report published to GitHub Pages
5. Live report updated

**Total time:** ~3 minutes (no manual steps)

---

## Vehicle Inspection KPIs

> A KPI (Key Performance Indicator) is a measurable value that shows how effectively the system is tracking inspection activity. The table below shows the main KPIs displayed in the Vehicle Inspection module.

| KPI | What It Means |
|-----|---------------|
| **Total Inspections** | Vehicles tested this month |
| **Passed** | Vehicles that passed all checks |
| **Failed** | Vehicles that failed checks |
| **Pending Review** | Waiting for inspector approval |

---

## Technology Stack

| Layer | Tool | Version |
|-------|------|---------|
| Language | Java | 17 |
| BDD Framework | Cucumber | 7.15.0 |
| Browser Automation | Selenium | 4.21.0 |
| Test Runner | JUnit 5 | 5.10.2 |
| CI/CD | GitHub Actions | ubuntu-latest |

---

*TechDeck CoreSuite Acceptance Tests — v1.0.0 | QA Engineer: Juan Pino | April 2026*