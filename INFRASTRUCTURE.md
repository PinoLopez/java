# TechDeck CoreSuite — Test Infrastructure (Simple Overview)

This diagram shows in plain English how the automatic tests work for the new Java web app.

```mermaid
%%{ init: { 'flowchart': { 'curve': 'basis' }, 'theme': 'base', 'themeVariables': { 'fontSize': '18px' } } }%%
flowchart TD
    A[PinoLopez/java<br/>GitHub Repository — main source code] 
    --> B[GitHub Actions<br/>Automatically starts when someone saves changes or submits a request]
    
    B --> C[Docker Compose Environment<br/>Creates a clean test setup that starts and stops automatically with health checks]
    
    C --> D[Mock App<br/>A simple copy of the web app used only for testing]
    
    C --> E[Selenium + Chrome<br/>Opens the app in a browser to run the tests]
    
    C --> F[Test Runner<br/>Maven + Cucumber — the tool that executes all tests]
    
    F --> G[All Tests Run Here]
    
    G --> H1[Smoke Basic<br/>Quick check: Does the app start and can you log in?]
    G --> H2[Smoke Advanced<br/>Check: Can you open all main pages?]
    G --> I[E2E Journeys<br/>Complete real-user flows from start to finish]
    G --> J[Regression<br/>Double-check that nothing important broke]
    G --> K[NFR Tests<br/>Speed, security, accessibility for disabled users, and data privacy]
    G --> L[API Checks<br/>Make sure the connections to the backend work]
    
    H1 & H2 & I & J & K & L 
    --> M[Cucumber JSON report<br/>Raw test results]
    
    M --> N[Beautiful HTML Report<br/>Easy-to-read summary with pass/fail status]
    
    N --> O[GitHub Pages<br/>Live public report that updates automatically]
    
    style A fill:#e3f2fd,stroke:#1976d2
    style O fill:#e3f2fd,stroke:#1976d2
    style C fill:#f0f4f8