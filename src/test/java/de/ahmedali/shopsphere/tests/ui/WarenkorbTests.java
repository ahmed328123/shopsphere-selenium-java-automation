package de.ahmedali.shopsphere.tests.ui;

import de.ahmedali.shopsphere.core.BasisTest;
import de.ahmedali.shopsphere.pages.AnmeldeSeite;
import de.ahmedali.shopsphere.pages.ProduktSeite;
import de.ahmedali.shopsphere.pages.WarenkorbSeite;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WarenkorbTests extends BasisTest {
    private ProduktSeite produkte() { return new AnmeldeSeite(driver).gueltigAnmelden(); }
    @Test(groups = "smoke") public void produktZumWarenkorbHinzufuegen() { ProduktSeite seite = produkte().erstesProduktHinzufuegen(); Assert.assertEquals(seite.warenkorbAnzahl(), 1); Assert.assertEquals(seite.warenkorbOeffnen().zeilenanzahl(), 1); }
    @Test public void mengeErhoehen() { Assert.assertEquals(produkte().erstesProduktHinzufuegen().warenkorbOeffnen().mengeErhoehen().menge(), 2); }
    @Test public void mengeNichtUnterEinsVerringern() { Assert.assertEquals(produkte().erstesProduktHinzufuegen().warenkorbOeffnen().mengeVerringern().menge(), 1); }
    @Test public void produktAusWarenkorbEntfernen() { WarenkorbSeite warenkorb = produkte().erstesProduktHinzufuegen().warenkorbOeffnen().produktEntfernen(); Assert.assertEquals(warenkorb.zeilenanzahl(), 0); Assert.assertEquals(warenkorb.meldung(), "Ihr Warenkorb ist leer"); }
    @Test public void gesamtsummeWirdAktualisiert() { WarenkorbSeite warenkorb = produkte().erstesProduktHinzufuegen().warenkorbOeffnen(); double einzelpreis = warenkorb.gesamtsumme(); warenkorb.mengeErhoehen(); Assert.assertEquals(warenkorb.gesamtsumme(), einzelpreis * 2, 0.01); }
}
