package de.ahmedali.shopsphere.tests.api;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class BestellungApiTests extends ApiBasisTest {
    @Test public void bestellungKannErstelltWerden() { given().contentType("application/json").body("{\"kunde\":\"API Testkunde\",\"summe\":99.50}").when().post("/api/bestellungen").then().statusCode(201).body("status", equalTo("BESTÄTIGT")); }
    @Test public void bestellungMitNullsummeWirdAbgelehnt() { given().contentType("application/json").body("{\"kunde\":\"API Testkunde\",\"summe\":0}").when().post("/api/bestellungen").then().statusCode(400); }
    @Test public void bestellungenKoennenAbgerufenWerden() { given().when().get("/api/bestellungen").then().statusCode(200); }
}
