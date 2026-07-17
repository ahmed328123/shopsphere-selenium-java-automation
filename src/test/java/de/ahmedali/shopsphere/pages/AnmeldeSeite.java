package de.ahmedali.shopsphere.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AnmeldeSeite extends BasisSeite {
    private final By email = By.cssSelector("[data-testid='email']");
    private final By passwort = By.cssSelector("[data-testid='passwort']");
    private final By anmelden = By.cssSelector("[data-testid='anmelden']");
    private final By fehler = By.cssSelector("[data-testid='anmelde-fehler']");

    public AnmeldeSeite(WebDriver driver) {
        super(driver);
    }

    public ProduktSeite gueltigAnmelden() {
        eingeben(email, "qa@shopsphere.local");
        eingeben(passwort, "ShopSphere-QA-2026!A7x9");
        klicken(anmelden);
        return new ProduktSeite(driver).wartenBisGeladen();
    }

    public AnmeldeSeite anmelden(String emailWert, String passwortWert) {
        eingeben(email, emailWert);
        eingeben(passwort, passwortWert);
        klicken(anmelden);
        return this;
    }

    public String fehlermeldung() {
        return text(fehler);
    }
}
