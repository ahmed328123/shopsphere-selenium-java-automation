package de.ahmedali.shopsphere.core;

import de.ahmedali.shopsphere.app.ShopSphereServer;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public abstract class BasisTest {
    protected WebDriver driver;

    @BeforeSuite(alwaysRun = true)
    public void serverStarten() {
        ShopSphereServer.starten();
    }

    @BeforeMethod(alwaysRun = true)
    public void browserStarten() {
        driver = DriverFabrik.erstellen();
        driver.get("http://127.0.0.1:" + ShopSphereServer.PORT);
        driver.manage().deleteAllCookies();
    }

    @AfterMethod(alwaysRun = true)
    public void browserBeenden() {
        warten(Long.parseLong(System.getProperty("keepOpen", "0")));
        DriverFabrik.beenden();
    }

    @AfterSuite(alwaysRun = true)
    public void serverStoppen() {
        ShopSphereServer.stoppen();
    }

    private void warten(long millisekunden) {
        if (millisekunden <= 0) return;
        try {
            Thread.sleep(millisekunden);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }
}
