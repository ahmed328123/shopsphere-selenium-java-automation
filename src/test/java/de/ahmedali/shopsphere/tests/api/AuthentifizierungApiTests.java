package de.ahmedali.shopsphere.tests.api;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AuthentifizierungApiTests extends ApiBasisTest {
    @Test public void gueltigeAnmeldungLiefertToken() { given().contentType("application/json").body("{\"email\":\"qa@shopsphere.local\",\"passwort\":\"ShopSphere-QA-2026!A7x9\"}").when().post("/api/anmeldung").then().statusCode(200).body("token", equalTo("demo-token")); }
    @Test public void ungueltigeAnmeldungLiefert401() { given().contentType("application/json").body("{\"email\":\"x\",\"passwort\":\"y\"}").when().post("/api/anmeldung").then().statusCode(401).body("meldung", equalTo("Ungültige E-Mail-Adresse oder ungültiges Passwort")); }
}
