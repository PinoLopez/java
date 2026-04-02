# TechDeck CoreSuite — Test Infrastructure

Simple overview of how the automatic tests work.

---

## Live Links

- [GitHub Repository](https://github.com/PinoLopez/java) — Source code
- [CI Pipeline](https://github.com/PinoLopez/java/actions) — Auto-test runs
- [Live Test Report](https://pinolopez.github.io/java/overview-features.html) — Test results
- [Mock App Demo](https://pinolopez.github.io/java/mock-app/) — Test target
- [GitHub Pages Deployments](https://github.com/PinoLopez/java/deployments) — Auto-deploy history

---

## Infrastructure Diagram
```mermaid
graph TD
    A[Developer pushes to master] --> B[GitHub Actions CI starts]
    B --> C[Java 17 + Chrome installed]
    C --> D[Mock App starts on port 8090]
    D --> E[mvn clean verify]
    E --> F[20 feature files executed]
    F --> G[Masterthought HTML report generated]
    G --> H[Report deployed to GitHub Pages]
    H --> I[Live report available]

    E --> J[Smoke Basic]
    E --> K[Smoke Advanced]
    E --> L[E2E + Regression]
    E --> M[NFR: Security, Accessibility, DSGVO]
    E --> N[Docker On/Off x3]
    E --> O[API Contract checks]
```

---

*TechDeck CoreSuite — Test Infrastructure | QA Engineer: Juan Pino | April 2026*