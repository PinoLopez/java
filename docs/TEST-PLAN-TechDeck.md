# TechDeck CoreSuite v1 — Test Plan (simple version for recruiters and managers)

**Version**: 1.0 | **Date**: March 2026 | **Owner**: QA Team

## What we test and why
We make sure the new Java web app works perfectly before it replaces the old 25-year-old system.

## Test Categories (everything that is covered)

| Category                  | What it does (simple)                              | How often          |
|---------------------------|----------------------------------------------------|--------------------|
| Smoke Basic               | App starts + login works                           | Every time         |
| Smoke Advanced            | All main pages open                                | Every time         |
| End-to-End Journeys       | Full real-user workflows                           | Every time         |
| Regression                | Nothing broke after changes                        | Every time         |
| Docker On/Off             | Environment starts and stops cleanly               | Every time         |
| NFR (Performance, Security, Accessibility, DSGVO) | Speed, safety, easy for disabled users, data privacy | Every time + nightly |
| API Contracts             | Backend connections work                           | Every time         |

**Easy explanations**  
- **Embedded or via hooks**: Automatic background helpers that run without you noticing.  
- **Data-driven**: One test that automatically tries many different examples.  
- **Docker/OWASP in CI**: Heavy checks are available manually or run nightly so the normal pipeline stays super fast (~2 minutes).

This plan guarantees the new app meets enterprise standards.
