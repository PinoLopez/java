package com.app.qa.coresuite.support;

import com.app.qa.coresuite.pages.*;
import org.openqa.selenium.WebDriver;

public class TestContext {
    private WebDriver driver;
    private String baseUrl;
    
    // Page Objects
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private VehicleInspectionPage vehicleInspectionPage;
    private IndustrialSafetyPage industrialSafetyPage;
    private EnvironmentalPage environmentalPage;
    private CertificationPage certificationPage;
    private AuditsPage auditsPage;
    private AdminPage adminPage;
    
    // Default baseUrl (GitHub Pages)
    private static final String DEFAULT_BASE_URL = "https://pinolopez.github.io/java/mock-app";
    
    public TestContext() {
        // Default constructor for DI
    }
    
    public void setDriver(WebDriver driverInstance) { 
        this.driver = driverInstance;
        // Initialize page objects when driver is set
        this.loginPage = new LoginPage(driver);
        this.dashboardPage = new DashboardPage(driver);
        this.vehicleInspectionPage = new VehicleInspectionPage(driver);
        this.industrialSafetyPage = new IndustrialSafetyPage(driver);
        this.environmentalPage = new EnvironmentalPage(driver);
        this.certificationPage = new CertificationPage(driver);
        this.auditsPage = new AuditsPage(driver);
        this.adminPage = new AdminPage(driver);
    }
    
    public WebDriver getDriver() { 
        return driver; 
    }
    
    public void setBaseUrl(String url) { 
        this.baseUrl = url; 
    }
    
    public String getBaseUrl() { 
        if (baseUrl == null || baseUrl.isEmpty()) {
            baseUrl = System.getProperty("baseUrl", DEFAULT_BASE_URL);
        }
        return baseUrl; 
    }
    
    // Page object getters
    public LoginPage getLoginPage() { return loginPage; }
    public DashboardPage getDashboardPage() { return dashboardPage; }
    public VehicleInspectionPage getVehicleInspectionPage() { return vehicleInspectionPage; }
    public IndustrialSafetyPage getIndustrialSafetyPage() { return industrialSafetyPage; }
    public EnvironmentalPage getEnvironmentalPage() { return environmentalPage; }
    public CertificationPage getCertificationPage() { return certificationPage; }
    public AuditsPage getAuditsPage() { return auditsPage; }
    public AdminPage getAdminPage() { return adminPage; }
}
