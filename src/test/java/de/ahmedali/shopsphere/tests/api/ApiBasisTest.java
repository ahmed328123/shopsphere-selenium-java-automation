package de.ahmedali.shopsphere.tests.api;

import de.ahmedali.shopsphere.app.ShopSphereServer;
import io.restassured.RestAssured;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public abstract class ApiBasisTest {
    @BeforeSuite(alwaysRun = true) public void serverStarten() { ShopSphereServer.starten(); RestAssured.baseURI = "http://127.0.0.1:" + ShopSphereServer.PORT; }
    @AfterSuite(alwaysRun = true) public void serverStoppen() { ShopSphereServer.stoppen(); }
}
