package de.ahmedali.shopsphere.tests.ui;

import de.ahmedali.shopsphere.core.BasisTest;
import de.ahmedali.shopsphere.pages.AnmeldeSeite;
import de.ahmedali.shopsphere.pages.KasseSeite;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutTests extends BasisTest {
    private KasseSeite kasse() { return new AnmeldeSeite(driver).gueltigAnmelden().erstesProduktHinzufuegen().warenkorbOeffnen().zurKasse(); }
    @Test public void pflichtfelderWerdenValidiert() { Assert.assertEquals(kasse().leerAbsenden().fehlermeldung(), "Alle Checkout-Felder sind erforderlich"); }
    @Test(groups = "smoke") public void bestellungMitKreditkarteWirdErstellt() { Assert.assertTrue(kasse().ausfuellen("Ahmed Ali", "Teststraße 1", "Duisburg", "karte").bestellungAufgeben().bestellungSichtbar()); }
    @Test public void bestellungMitPaypalWirdErstellt() { Assert.assertTrue(kasse().ausfuellen("Ahmed Ali", "Teststraße 1", "Duisburg", "paypal").bestellungAufgeben().bestellungSichtbar()); }
}
