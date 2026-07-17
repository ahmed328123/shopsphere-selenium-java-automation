package de.ahmedali.shopsphere.tests.db;

import de.ahmedali.shopsphere.app.ShopSphereServer;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.Statement;

public class DatenbankTests {
    @BeforeSuite(alwaysRun = true) public void serverStarten() { ShopSphereServer.starten(); }
    @AfterSuite(alwaysRun = true) public void serverStoppen() { ShopSphereServer.stoppen(); }
    @Test public void sechsProdukteSindVorhanden() throws Exception { try (Statement s = ShopSphereServer.datenbank().createStatement(); ResultSet r = s.executeQuery("SELECT COUNT(*) FROM produkte")) { r.next(); Assert.assertEquals(r.getInt(1), 6); } }
    @Test public void allePreiseSindPositiv() throws Exception { try (Statement s = ShopSphereServer.datenbank().createStatement(); ResultSet r = s.executeQuery("SELECT COUNT(*) FROM produkte WHERE preis<=0")) { r.next(); Assert.assertEquals(r.getInt(1), 0); } }
    @Test public void alleBestaendeSindPositiv() throws Exception { try (Statement s = ShopSphereServer.datenbank().createStatement(); ResultSet r = s.executeQuery("SELECT COUNT(*) FROM produkte WHERE bestand<=0")) { r.next(); Assert.assertEquals(r.getInt(1), 0); } }
    @Test public void produktIdsSindEindeutig() throws Exception { try (Statement s = ShopSphereServer.datenbank().createStatement(); ResultSet r = s.executeQuery("SELECT COUNT(*) gesamt, COUNT(DISTINCT id) eindeutig FROM produkte")) { r.next(); Assert.assertEquals(r.getInt("gesamt"), r.getInt("eindeutig")); } }
}
