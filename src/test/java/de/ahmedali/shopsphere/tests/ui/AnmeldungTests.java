package de.ahmedali.shopsphere.tests.ui;

import de.ahmedali.shopsphere.core.BasisTest;
import de.ahmedali.shopsphere.pages.AnmeldeSeite;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AnmeldungTests extends BasisTest {
    @Test(groups = "smoke") public void gueltigeAnmeldungZeigtProduktkatalog() { Assert.assertEquals(new AnmeldeSeite(driver).gueltigAnmelden().produktanzahl(), 6); }
    @Test public void falschesPasswortWirdAbgelehnt() { Assert.assertEquals(new AnmeldeSeite(driver).anmelden("qa@shopsphere.local", "falsch").fehlermeldung(), "Ungültige E-Mail-Adresse oder ungültiges Passwort"); }
    @Test public void leereAnmeldedatenWerdenAbgelehnt() { Assert.assertEquals(new AnmeldeSeite(driver).anmelden("", "").fehlermeldung(), "Ungültige E-Mail-Adresse oder ungültiges Passwort"); }
    @Test public void unbekannteEmailWirdAbgelehnt() { Assert.assertEquals(new AnmeldeSeite(driver).anmelden("unbekannt@demo.de", "ShopSphere-QA-2026!A7x9").fehlermeldung(), "Ungültige E-Mail-Adresse oder ungültiges Passwort"); }
}
