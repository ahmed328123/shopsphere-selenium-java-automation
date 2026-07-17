package de.ahmedali.shopsphere.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class KasseSeite extends BasisSeite {
    private final By name = By.cssSelector("[data-testid='kasse-name']");
    private final By adresse = By.cssSelector("[data-testid='kasse-adresse']");
    private final By stadt = By.cssSelector("[data-testid='kasse-stadt']");
    private final By zahlungsart = By.cssSelector("[data-testid='zahlungsart']");
    private final By bestellen = By.cssSelector("[data-testid='bestellung-aufgeben']");
    private final By fehler = By.cssSelector("[data-testid='kasse-fehler']");
    private final By bestellung = By.cssSelector("[data-testid='bestellung-zeile']");

    public KasseSeite(WebDriver driver) { super(driver); }
    public KasseSeite leerAbsenden() { klicken(bestellen); return this; }
    public KasseSeite ausfuellen(String nameWert, String adresseWert, String stadtWert, String zahlungswert) {
        eingeben(name, nameWert); eingeben(adresse, adresseWert); eingeben(stadt, stadtWert);
        new Select(sichtbar(zahlungsart)).selectByValue(zahlungswert); pause(); return this;
    }
    public KasseSeite bestellungAufgeben() { klicken(bestellen); return this; }
    public String fehlermeldung() { return text(fehler); }
    public boolean bestellungSichtbar() { return sichtbar(bestellung).isDisplayed(); }
}
