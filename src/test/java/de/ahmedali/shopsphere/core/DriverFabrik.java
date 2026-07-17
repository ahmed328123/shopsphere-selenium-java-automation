package de.ahmedali.shopsphere.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DriverFabrik {
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverFabrik() {
    }

    public static WebDriver erstellen() {
        Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder").setLevel(Level.SEVERE);
        Logger.getLogger("org.openqa.selenium.chromium.ChromiumDriver").setLevel(Level.SEVERE);

        String browser = System.getProperty("browser", "chrome");
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "true"));
        WebDriver driver;

        if ("edge".equalsIgnoreCase(browser)) {
            EdgeOptions optionen = new EdgeOptions();
            if (headless) optionen.addArguments("--headless=new");
            optionen.addArguments("--window-size=1440,1200", "--disable-notifications", "--inprivate");
            driver = new EdgeDriver(optionen);
        } else {
            ChromeOptions optionen = new ChromeOptions();
            Map<String, Object> einstellungen = new HashMap<>();
            einstellungen.put("credentials_enable_service", false);
            einstellungen.put("profile.password_manager_enabled", false);
            einstellungen.put("profile.password_manager_leak_detection", false);
            einstellungen.put("profile.default_content_setting_values.notifications", 2);
            optionen.setExperimentalOption("prefs", einstellungen);
            if (headless) optionen.addArguments("--headless=new");
            optionen.addArguments(
                    "--window-size=1440,1200",
                    "--disable-notifications",
                    "--disable-save-password-bubble",
                    "--disable-features=PasswordLeakDetection,PasswordManagerOnboarding,PasswordManagerRedesign",
                    "--incognito"
            );
            driver = new ChromeDriver(optionen);
        }
        if (!headless) {
            driver.manage().window().maximize();
        }

        DRIVER.set(driver);
        return driver;
    }

    public static WebDriver holen() {
        return DRIVER.get();
    }

    public static void beenden() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }
}
