# TechDeck CoreSuite — Test Infrastructure (Simple Overview)

This diagram shows how the automated tests run for the new Java web app.

```mermaid
flowchart TD
    A[PinoLopez/java<br/>Source of truth — master branch] --> B[GitHub Actions<br/>push or PR trigger — ~2 minutes]
    B --> C[Docker Compose Environment<br/>with health checks on all services]
    C --> D[Mock App<br/>CoreSuite UI on port 8080]
    C --> E[Selenium + Chrome<br/>Headless browser]
    C --> F[Test Runner<br/>Maven + Cucumber + JUnit]
    F --> G[All Tests]
    G --> H1[Smoke Basic<br/>HTTP health checks]
    G --> H2[Smoke Advanced<br/>Module navigation]
    G --> I[E2E Journeys<br/>Full user workflows]
    G --> J[Regression<br/>Critical paths]
    G --> K[NFR Tests<br/>JMeter load, Accessibility, DSGVO]
    G --> L[API Contracts<br/>Mandatory endpoint checks]
    H1 & H2 & I & J & K & L --> M[Cucumber JSON report]
    M --> N[Beautiful HTML Report<br/>cucumber-reporting]
    N --> O[GitHub Pages<br/>Live report — always up-to-date]
    style A fill:#e3f2fd,stroke:#1976d2
    style O fill:#e3f2fd,stroke:#1976d2
    style C fill:#f0f4f8