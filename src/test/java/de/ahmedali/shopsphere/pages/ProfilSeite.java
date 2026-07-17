package de.ahmedali.shopsphere.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProfilSeite extends BasisSeite {
    private final By navigation = By.cssSelector("[data-testid='nav-profil']");
    private final By name = By.cssSelector("[data-testid='profil-name']");
    private final By telefon = By.cssSelector("[data-testid='profil-telefon']");
    private final By speichern = By.cssSelector("[data-testid='profil-speichern']");
    private final By meldung = By.cssSelector("[data-testid='profil-meldung']");

    public ProfilSeite(WebDriver driver) { super(driver); }
    public ProfilSeite oeffnen() { klicken(navigation); return this; }
    public ProfilSeite aktualisieren(String nameWert, String telefonWert) { eingeben(name, nameWert); eingeben(telefon, telefonWert); klicken(speichern); return this; }
    public String meldung() { return text(meldung); }
}
