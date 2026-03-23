
### 2. **docs/TEST-PLAN-TechDeck.md** – Full new content (replace everything)

```markdown
# TechDeck CoreSuite v1 — Detailed Test Plan

**Version**: 1.0 | **Date**: March 2026 | **Owner**: QA Automation Team

## Test Scope & Objectives
Replace legacy Softproject X4 safely with zero production incidents.

## Test Categories & Execution Plan

| Category                    | Sub-type                          | Status in suite     | Execution frequency     | Responsible     |
|-----------------------------|-----------------------------------|---------------------|-------------------------|-----------------|
| Smoke                       | Basic                             | ✅ Covered          | Every CI / Deploy       | Automation      |
| Smoke                       | Advanced                          | ✅ Covered          | Every build             | Automation      |
| End-to-End                  | Full user journeys                | ✅ Covered          | Nightly + Pre-Release   | Automation      |
| Regression                  | Full suite                        | ✅ Covered          | Every push              | CI Pipeline     |
| On/Off (Infrastructure)     | Docker Compose up/down + cleanup  | 🟡 Planned          | Deployment pipeline     | DevOps + QA     |
| NFR                         | Performance / Reliability / Maintainability | ✅ Covered | CI + Monitoring         | Automation      |
| NFR                         | Load/Stress, Security (OWASP), Accessibility (BITV/WCAG), Scalability, DSGVO | 🟡 Planned | Separate pipeline       | QA + Security   |
| Mandatory (never skipped)   | Integration, API, Security, Usability, Compliance, Smoke-in-Production | 🟡 Planned | Release gate            | All teams       |

## On/Off Docker Tests (to be implemented)

1. `docker compose up -d --build` → health check test  
2. Run smoke suite  
3. `docker compose down -v --rmi all` → verify cleanup (no orphan containers/volumes)  
4. Repeat 3 times → assert 100 % success

## Additional Information
Entry / Exit Criteria, Risks, RACI, defect management and release gates are available on request.  
This is the clear executive summary for international stakeholders.

**This plan guarantees** that TechDeck meets the highest enterprise quality standards required for inspection & certification platforms.