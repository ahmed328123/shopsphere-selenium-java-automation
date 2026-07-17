package de.ahmedali.shopsphere.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class ProduktSeite extends BasisSeite {
    private final By produktkarten = By.cssSelector("[data-testid='produktkarte']");
    private final By suche = By.cssSelector("[data-testid='suche']");
    private final By kategorie = By.cssSelector("[data-testid='kategorie']");
    private final By sortierung = By.cssSelector("[data-testid='sortierung']");
    private final By hinzufuegen = By.cssSelector("[data-testid='hinzufuegen']");
    private final By warenkorbAnzahl = By.cssSelector("[data-testid='warenkorb-anzahl']");
    private final By warenkorbNavigation = By.cssSelector("[data-testid='nav-warenkorb']");

    public ProduktSeite(WebDriver driver) {
        super(driver);
    }

    public ProduktSeite wartenBisGeladen() {
        sichtbar(produktkarten);
        return this;
    }

    public int produktanzahl() {
        return alle(produktkarten).size();
    }

    public ProduktSeite suchen(String begriff) {
        eingeben(suche, begriff);
        return this;
    }

    public ProduktSeite kategorieWaehlen(String wert) {
        new Select(sichtbar(kategorie)).selectByValue(wert);
        pause();
        return this;
    }

    public ProduktSeite sortieren(String wert) {
        new Select(sichtbar(sortierung)).selectByValue(wert);
        pause();
        return this;
    }

    public String erstesProdukt() {
        return alle(produktkarten).get(0).getText();
    }

    public ProduktSeite erstesProduktHinzufuegen() {
        klicken(alle(hinzufuegen).get(0));
        return this;
    }

    public int warenkorbAnzahl() {
        return Integer.parseInt(text(warenkorbAnzahl));
    }

    public WarenkorbSeite warenkorbOeffnen() {
        klicken(warenkorbNavigation);
        return new WarenkorbSeite(driver);
    }
}
