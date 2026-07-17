package de.ahmedali.shopsphere.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WarenkorbSeite extends BasisSeite {
    private final By zeilen = By.cssSelector("[data-testid='warenkorb-zeile']");
    private final By menge = By.cssSelector("[data-testid='menge']");
    private final By erhoehen = By.cssSelector("[data-testid='erhoehen']");
    private final By verringern = By.cssSelector("[data-testid='verringern']");
    private final By entfernen = By.cssSelector("[data-testid='entfernen']");
    private final By summe = By.cssSelector("[data-testid='warenkorb-summe']");
    private final By zurKasse = By.cssSelector("[data-testid='zur-kasse']");
    private final By meldung = By.cssSelector("[data-testid='warenkorb-meldung']");

    public WarenkorbSeite(WebDriver driver) {
        super(driver);
    }

    public int zeilenanzahl() { return alle(zeilen).size(); }
    public int menge() { return Integer.parseInt(text(menge)); }
    public WarenkorbSeite mengeErhoehen() { klicken(erhoehen); return this; }
    public WarenkorbSeite mengeVerringern() { klicken(verringern); return this; }
    public WarenkorbSeite produktEntfernen() { klicken(entfernen); return this; }
    public double gesamtsumme() { return Double.parseDouble(text(summe)); }
    public KasseSeite zurKasse() { klicken(zurKasse); return new KasseSeite(driver); }
    public String meldung() { return text(meldung); }
}
