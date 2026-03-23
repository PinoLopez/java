# TechDeck — Test Strategy (v1.0)

**Purpose**  
This document explains **why** we test the way we do, **how** each layer is implemented, and **when** each test type is executed.  
Target audience: international QA teams, architects, auditors, stakeholders.

## 1. Overall Testing Pyramid & Why BDD
We follow the industry-standard pyramid + BDD (Gherkin) because:
- Business & IT speak the same language
- Tests are living documentation
- Changes in UI only affect Page Objects → very maintainable

## 2. Test Types – Why / How / When

### Smoke Tests – Basic
**Why**: Fast sanity check that the application is up and the most critical path works.  
**How**: Login + dashboard load (Feature 01).  
**When**: Every CI run, every deploy, before any deeper testing.

### Smoke Tests – Advanced
**Why**: Verify all main modules are reachable before full regression.  
**How**: Dashboard cards + navigation (Feature 03).  
**When**: After every build / before E2E.

### End-to-End (E2E) Tests
**Why**: Simulate real user journeys end-to-end.  
**How**: Full flows in Features 04–09.  
**When**: Nightly + before every release.

### Regression Tests
**Why**: Ensure nothing broke after changes.  
**How**: All 10 features run in parallel in CI.  
**When**: On every push + on-demand.

### On/Off Tests (Docker)
**Why**: Verify the whole environment can be started and cleanly stopped (critical for cloud/DevOps).  
**How**: Docker Compose up/down + health-check tests + cleanup.  
**When**: During deployment pipeline and environment switch tests.

### Non-Functional Requirements (NFR)
- **Performance** – total run < 3 min, measured in CI  
- **Reliability** – screenshot + embed on every failure  
- **Maintainability** – strict Page Object Model + clear naming  
- **Load/Stress** – planned with JMeter (not yet in this suite)  
- **Security (OWASP)** – static + dynamic scans in pipeline (planned)  
- **Accessibility (BITV/WCAG)** – axe-core integration planned  
- **Scalability** – container resource limits tested  
- **DSGVO compliance** – data masking & consent checks in future scenarios

### Always Mandatory Tests (TechDeck standard)
The following are **never skipped** in any release:
- Integration tests  
- API contract tests  
- Security tests  
- Usability / Accessibility review  
- Compliance (DSGVO / ISO) audit  
- Smoke tests in Production-like environment  

---

**Approval**  
This strategy was approved for TechDeck CoreSuite v1 migration project.