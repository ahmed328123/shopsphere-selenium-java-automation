package de.ahmedali.shopsphere.tests.ui;

import de.ahmedali.shopsphere.core.BasisTest;
import de.ahmedali.shopsphere.pages.AnmeldeSeite;
import de.ahmedali.shopsphere.pages.ProduktSeite;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProduktTests extends BasisTest {
    private ProduktSeite produkte() { return new AnmeldeSeite(driver).gueltigAnmelden(); }
    @Test(groups = "smoke") public void produktkatalogWirdGeladen() { Assert.assertEquals(produkte().produktanzahl(), 6); }
    @Test public void sucheNachProduktname() { ProduktSeite seite = produkte().suchen("Tastatur"); Assert.assertEquals(seite.produktanzahl(), 1); Assert.assertTrue(seite.erstesProdukt().contains("Mechanische Tastatur")); }
    @Test public void sucheNachKategorietext() { Assert.assertEquals(produkte().suchen("Bücher").produktanzahl(), 1); }
    @Test public void unbekannteSucheLiefertKeineProdukte() { Assert.assertEquals(produkte().suchen("nicht-vorhanden").produktanzahl(), 0); }
    @Test public void kategoriefilterElektronik() { Assert.assertEquals(produkte().kategorieWaehlen("Elektronik").produktanzahl(), 3); }
    @Test public void kategoriefilterSport() { Assert.assertEquals(produkte().kategorieWaehlen("Sport").produktanzahl(), 1); }
    @Test public void preissortierungAufsteigend() { Assert.assertTrue(produkte().sortieren("preis-auf").erstesProdukt().contains("Praxishandbuch Softwaretest")); }
    @Test public void preissortierungAbsteigend() { Assert.assertTrue(produkte().sortieren("preis-ab").erstesProdukt().contains("Smartwatch Active")); }
    @Test public void bewertungssortierungAbsteigend() { Assert.assertTrue(produkte().sortieren("bewertung-ab").erstesProdukt().contains("Praxishandbuch Softwaretest")); }
}
