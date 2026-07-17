package de.ahmedali.shopsphere.tests.ui;

import de.ahmedali.shopsphere.core.BasisTest;
import de.ahmedali.shopsphere.pages.AnmeldeSeite;
import de.ahmedali.shopsphere.pages.ProfilSeite;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProfilTests extends BasisTest {
    @Test public void profilKannGespeichertWerden() { new AnmeldeSeite(driver).gueltigAnmelden(); ProfilSeite profil = new ProfilSeite(driver).oeffnen().aktualisieren("Ahmed Ali", "+49 157 51237479"); Assert.assertEquals(profil.meldung(), "Profil wurde gespeichert"); }
}
