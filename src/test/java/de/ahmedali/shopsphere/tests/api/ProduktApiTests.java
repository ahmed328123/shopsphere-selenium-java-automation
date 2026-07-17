package de.ahmedali.shopsphere.tests.api;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ProduktApiTests extends ApiBasisTest {
    @Test public void produktlisteEnthaeltSechsProdukte() { given().when().get("/api/produkte").then().statusCode(200).body("size()", is(6)); }
    @Test public void produktKannNachIdGeladenWerden() { given().when().get("/api/produkte/1").then().statusCode(200).body("name", equalTo("Kabellose Kopfhörer")); }
    @Test public void unbekanntesProduktLiefert404() { given().when().get("/api/produkte/999").then().statusCode(404).body("meldung", equalTo("Produkt wurde nicht gefunden")); }
    @Test public void produktEnthaeltPflichtfelder() { given().when().get("/api/produkte").then().body("[0].id", notNullValue()).body("[0].preis", greaterThan(0f)).body("[0].bestand", greaterThan(0)); }
}
