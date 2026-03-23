# TechDeck CoreSuite v1 — Automated Acceptance Tests

> Java 17 · Maven · Cucumber 7 · Selenium 4 · WebDriverManager · Masterthought

Automated acceptance test suite for **TechDeck CoreSuite v1**, the new enterprise inspection and certification platform built to replace the 25-year-old Softproject X4 BPMS legacy system used for vehicle roadworthiness, industrial safety and environmental compliance.

All tests were written and executed with **Visual Studio Code** under **Linux Mint**.

---

## Live Test Report

The Masterthought HTML report is published automatically on every push to `master`.  
**View live test report** → [GitHub Pages](https://pinolopez.github.io/java/)

---

## 📚 Documentation

- **[TEST-STRATEGY-TechDeck.md](docs/TEST-STRATEGY-TechDeck.md)** – Why we test this way, how, and when
- **[TEST-PLAN-TechDeck.md](docs/TEST-PLAN-TechDeck.md)** – Full test categories, execution plan and status

---

## Project Context

| Item                  | Detail |
|-----------------------|--------|
| Legacy system         | Softproject X4 BPMS — in production for ~25 years |
| New platform          | TechDeck CoreSuite v1 — modern Java-based replacement |
| Migration window      | 5-year parallel-run and safe cutover |
| Testing approach      | We write tests in simple plain-English sentences (anyone can read “Given… When… Then…” without knowing technical terms) so business teams and developers stay perfectly aligned |
| Test scope            | Login, dashboard, all inspection modules, certification, audits, admin |
| Standards covered     | ISO 9001 · ISO 14001 · ISO 45001 · TÜV · ADAC · DSGVO |

---

## Testing Strategy Overview

Full details are in the docs files above. Quick view of the main categories covered in this repository:

| Type                    | What it checks                                      | Covered here |
|-------------------------|-----------------------------------------------------|--------------|
| Smoke – Basic           | System starts and login works                       | ✅ Feature 01 |
| Smoke – Advanced        | All dashboard modules load                          | ✅ Feature 03 |
| End-to-End              | Complete user journeys                              | ✅ Features 04–09 |
| Regression              | Nothing broke after changes                         | ✅ All 10 features on every push |
| On/Off Docker           | Environment can start and stop cleanly              | 🟡 To be implemented |
| NFR                     | Speed, screenshots, clean code                      | ✅ Built-in |
| Always mandatory        | Integration, API, Security, Usability, Compliance, Smoke-in-Production | 🟡 Planned in release gates |

---

## What this project demonstrates

- Tests written in simple English (no jargon) so everyone understands
- Page Object Model (all UI selectors in one place)
- Shared browser handling, automatic screenshots on failure, coloured highlights on tested elements
- Zero-config ChromeDriver + lightweight HTML mock app
- Rich HTML report + GitHub Actions CI that finishes in under 3 minutes

---

## Running the tests locally

```bash
cd ~/Documents/Java
mvn verify