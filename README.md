# TechDeck CoreSuite v1 — Automated BDD Acceptance Tests

> **Java 17 · Maven · Cucumber 7 · Selenium 4 · WebDriverManager · Masterthought**  
> **Replacement of 20+ year old Softproject X4 legacy system** (inspired by real-world enterprise migrations such as DEKRA-style inspection platforms)

Modern, maintainable, and fully documented test automation suite for **TechDeck CoreSuite v1** — the new Java-based, cloud-ready web application that replaces the outdated Softproject X4 BPMS used for vehicle, industrial safety and environmental certification for more than 20 years.

Built and executed on **Linux Mint + Visual Studio Code**. Designed for international teams, stakeholders, recruiters and auditors.

![Java](https://img.shields.io/badge/Java-17-007396?logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?logo=apachemaven&logoColor=white)
![Cucumber](https://img.shields.io/badge/Cucumber-7-23D96C?logo=cucumber&logoColor=white)
![Selenium](https://img.shields.io/badge/Selenium-4-43B02A?logo=selenium&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?logo=docker&logoColor=white)
![Linux Mint](https://img.shields.io/badge/Linux%20Mint-21.3-87CF3E?logo=linuxmint&logoColor=white)
![VS Code](https://img.shields.io/badge/VS%20Code-007ACC?logo=visualstudiocode&logoColor=white)
![CI Status](https://github.com/PinoLopez/java/actions/workflows/ci.yml/badge.svg)

---

## 📍 Why This Project Exists (Project Context)

TechDeck is modernising a critical 20+ year old legacy system (Softproject X4) that has been in production since the early 2000s.  
The new CoreSuite v1 is a clean Java/Spring web app with far better UX, scalability and compliance.

**This test suite proves** that the migration can be done safely, quickly and with full traceability — exactly what big enterprises (DEKRA-style) demand.

| Item                  | Detail |
|-----------------------|--------|
| Legacy system         | Softproject X4 BPMS — 20+ years in production |
| New platform          | TechDeck CoreSuite v1 — modern Java web app |
| Migration goal        | Zero-downtime parallel run + full cutover |
| Testing approach      | BDD from day 1 — acceptance tests drive development |
| Standards & compliance| ISO 9001/14001/45001, TÜV, ADAC, DSGVO, OWASP, BITV/WCAG |

---

## 📚 Documentation

- **[TEST-STRATEGY-TechDeck.md](docs/TEST-STRATEGY-TechDeck.md)** – Full testing strategy, why each test type exists, how it is implemented, when it is executed  
- **[TEST-PLAN-TechDeck.md](docs/TEST-PLAN-TechDeck.md)** – Detailed test plan with all categories (Smoke, E2E, Regression, On/Off Docker, NFR, mandatory tests, etc.)

**Live Masterthought Report** → [GitHub Pages](https://pinolopez.github.io/java/) (auto-updated after every push)

---

## 🧪 Test Coverage Overview

10 feature files | 19 scenarios covering:
- Login (valid + invalid)
- Dashboard + all 6 modules
- Vehicle Inspection, Industrial Safety, Environmental, Certification, Audits, Administration

All tests run against a lightweight HTML mock (no real backend needed locally).

---

## 🚀 Running Locally (Linux Mint)

```bash
cd /home/agropecuario/Documents/Java
mvn verify