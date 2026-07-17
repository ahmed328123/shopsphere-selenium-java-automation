package de.ahmedali.shopsphere.app;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ShopSphereServer {
    public static final int PORT = 8090;
    private static final String TEST_EMAIL = "qa@shopsphere.local";
    private static final String TEST_PASSWORD = "ShopSphere-QA-2026!A7x9";

    private static HttpServer server;
    private static Connection datenbank;

    private ShopSphereServer() {
    }

    public static synchronized void starten() {
        if (server != null) {
            return;
        }
        try {
            datenbank = DriverManager.getConnection("jdbc:h2:mem:shopsphere;DB_CLOSE_DELAY=-1");
            datenbankInitialisieren();
            testdatenAnlegen();

            server = HttpServer.create(new InetSocketAddress("127.0.0.1", PORT), 0);
            server.createContext("/", ShopSphereServer::verarbeiten);
            server.setExecutor(Executors.newCachedThreadPool());
            server.start();
            System.out.println("ShopSphere wurde unter http://127.0.0.1:" + PORT + " gestartet.");
        } catch (Exception exception) {
            throw new IllegalStateException("ShopSphere konnte nicht gestartet werden.", exception);
        }
    }

    public static synchronized void stoppen() {
        if (server != null) {
            server.stop(0);
            server = null;
        }
    }

    public static Connection datenbank() {
        return datenbank;
    }

    private static void datenbankInitialisieren() throws SQLException {
        try (Statement statement = datenbank.createStatement()) {
            statement.execute("""
                CREATE TABLE IF NOT EXISTS produkte(
                    id INT PRIMARY KEY,
                    name VARCHAR(120) NOT NULL,
                    kategorie VARCHAR(40) NOT NULL,
                    preis DECIMAL(10,2) NOT NULL,
                    bestand INT NOT NULL,
                    bewertung DECIMAL(2,1) NOT NULL
                )
            """);
            statement.execute("""
                CREATE TABLE IF NOT EXISTS bestellungen(
                    id IDENTITY PRIMARY KEY,
                    kunde VARCHAR(120) NOT NULL,
                    summe DECIMAL(10,2) NOT NULL,
                    status VARCHAR(30) NOT NULL,
                    erstellt_am TIMESTAMP NOT NULL
                )
            """);
        }
    }

    private static void testdatenAnlegen() throws SQLException {
        try (Statement statement = datenbank.createStatement()) {
            statement.execute("DELETE FROM produkte");
            statement.execute("DELETE FROM bestellungen");
        }

        Object[][] produkte = {
            {1, "Kabellose Kopfhörer", "Elektronik", 89.99, 15, 4.6},
            {2, "Mechanische Tastatur", "Elektronik", 119.00, 8, 4.8},
            {3, "Laufschuhe Pro", "Sport", 74.50, 12, 4.4},
            {4, "Kaffeemaschine", "Haushalt", 59.90, 5, 4.2},
            {5, "Praxishandbuch Softwaretest", "Bücher", 39.99, 20, 4.9},
            {6, "Smartwatch Active", "Elektronik", 149.99, 7, 4.5}
        };

        try (PreparedStatement statement = datenbank.prepareStatement(
                "INSERT INTO produkte(id,name,kategorie,preis,bestand,bewertung) VALUES(?,?,?,?,?,?)")) {
            for (Object[] produkt : produkte) {
                for (int index = 0; index < produkt.length; index++) {
                    statement.setObject(index + 1, produkt[index]);
                }
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private static void verarbeiten(HttpExchange exchange) throws IOException {
        try {
            String pfad = exchange.getRequestURI().getPath();
            if (pfad.startsWith("/api/")) {
                apiVerarbeiten(exchange, pfad);
            } else {
                statischeDateiSenden(exchange, pfad);
            }
        } catch (Exception exception) {
            jsonSenden(exchange, 500, "{\"meldung\":\"Interner Serverfehler\"}");
        } finally {
            exchange.close();
        }
    }

    private static void apiVerarbeiten(HttpExchange exchange, String pfad) throws Exception {
        String methode = exchange.getRequestMethod();

        if (pfad.equals("/api/anmeldung") && methode.equals("POST")) {
            String inhalt = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            String email = textFeld(inhalt, "email");
            String passwort = textFeld(inhalt, "passwort");
            if (TEST_EMAIL.equals(email) && TEST_PASSWORD.equals(passwort)) {
                jsonSenden(exchange, 200,
                        "{\"token\":\"demo-token\",\"name\":\"Ahmed QA\"}");
            } else {
                jsonSenden(exchange, 401,
                        "{\"meldung\":\"Ungültige E-Mail-Adresse oder ungültiges Passwort\"}");
            }
            return;
        }

        if (pfad.equals("/api/produkte") && methode.equals("GET")) {
            jsonSenden(exchange, 200, produkteAlsJson());
            return;
        }

        if (pfad.matches("/api/produkte/\\d+") && methode.equals("GET")) {
            int id = Integer.parseInt(pfad.substring(pfad.lastIndexOf('/') + 1));
            String produkt = produktAlsJson(id);
            if (produkt == null) {
                jsonSenden(exchange, 404, "{\"meldung\":\"Produkt wurde nicht gefunden\"}");
            } else {
                jsonSenden(exchange, 200, produkt);
            }
            return;
        }

        if (pfad.equals("/api/bestellungen") && methode.equals("POST")) {
            String inhalt = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            String kunde = textFeld(inhalt, "kunde");
            double summe = zahlenFeld(inhalt, "summe");
            if (kunde.isBlank() || summe <= 0) {
                jsonSenden(exchange, 400,
                        "{\"meldung\":\"Kunde und positive Bestellsumme sind erforderlich\"}");
                return;
            }
            try (PreparedStatement statement = datenbank.prepareStatement(
                    "INSERT INTO bestellungen(kunde,summe,status,erstellt_am) VALUES(?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, kunde);
                statement.setDouble(2, summe);
                statement.setString(3, "BESTÄTIGT");
                statement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                statement.executeUpdate();
                try (ResultSet schluessel = statement.getGeneratedKeys()) {
                    schluessel.next();
                    jsonSenden(exchange, 201, String.format(Locale.US,
                            "{\"id\":%d,\"status\":\"BESTÄTIGT\",\"summe\":%.2f}",
                            schluessel.getLong(1), summe));
                }
            }
            return;
        }

        if (pfad.equals("/api/bestellungen") && methode.equals("GET")) {
            jsonSenden(exchange, 200, bestellungenAlsJson());
            return;
        }

        jsonSenden(exchange, 404, "{\"meldung\":\"Endpunkt wurde nicht gefunden\"}");
    }

    private static String produkteAlsJson() throws SQLException {
        List<String> eintraege = new ArrayList<>();
        try (Statement statement = datenbank.createStatement();
             ResultSet result = statement.executeQuery("SELECT * FROM produkte ORDER BY id")) {
            while (result.next()) {
                eintraege.add(produktZeileAlsJson(result));
            }
        }
        return "[" + String.join(",", eintraege) + "]";
    }

    private static String produktAlsJson(int id) throws SQLException {
        try (PreparedStatement statement = datenbank.prepareStatement("SELECT * FROM produkte WHERE id=?")) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                return result.next() ? produktZeileAlsJson(result) : null;
            }
        }
    }

    private static String produktZeileAlsJson(ResultSet result) throws SQLException {
        return String.format(Locale.US,
                "{\"id\":%d,\"name\":\"%s\",\"kategorie\":\"%s\",\"preis\":%.2f,\"bestand\":%d,\"bewertung\":%.1f}",
                result.getInt("id"), jsonText(result.getString("name")),
                jsonText(result.getString("kategorie")), result.getDouble("preis"),
                result.getInt("bestand"), result.getDouble("bewertung"));
    }

    private static String bestellungenAlsJson() throws SQLException {
        List<String> eintraege = new ArrayList<>();
        try (Statement statement = datenbank.createStatement();
             ResultSet result = statement.executeQuery("SELECT * FROM bestellungen ORDER BY id")) {
            while (result.next()) {
                eintraege.add(String.format(Locale.US,
                        "{\"id\":%d,\"kunde\":\"%s\",\"summe\":%.2f,\"status\":\"%s\"}",
                        result.getLong("id"), jsonText(result.getString("kunde")),
                        result.getDouble("summe"), jsonText(result.getString("status"))));
            }
        }
        return "[" + String.join(",", eintraege) + "]";
    }

    private static String textFeld(String json, String feld) {
        Matcher matcher = Pattern.compile("\\\"" + Pattern.quote(feld) + "\\\"\\s*:\\s*\\\"([^\\\"]*)\\\"")
                .matcher(json);
        return matcher.find() ? matcher.group(1) : "";
    }

    private static double zahlenFeld(String json, String feld) {
        Matcher matcher = Pattern.compile("\\\"" + Pattern.quote(feld) + "\\\"\\s*:\\s*(-?\\d+(?:\\.\\d+)?)")
                .matcher(json);
        return matcher.find() ? Double.parseDouble(matcher.group(1)) : 0;
    }

    private static String jsonText(String text) {
        return text.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private static void statischeDateiSenden(HttpExchange exchange, String pfad) throws IOException {
        String dateipfad = pfad.equals("/") ? "/web/index.html" : "/web" + pfad;
        try (InputStream stream = ShopSphereServer.class.getResourceAsStream(dateipfad)) {
            if (stream == null) {
                exchange.sendResponseHeaders(404, -1);
                return;
            }
            byte[] daten = stream.readAllBytes();
            String typ = pfad.endsWith(".css") ? "text/css"
                    : pfad.endsWith(".js") ? "application/javascript" : "text/html";
            exchange.getResponseHeaders().set("Content-Type", typ + "; charset=utf-8");
            exchange.sendResponseHeaders(200, daten.length);
            exchange.getResponseBody().write(daten);
        }
    }

    private static void jsonSenden(HttpExchange exchange, int status, String json) throws IOException {
        byte[] daten = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        exchange.sendResponseHeaders(status, daten.length);
        exchange.getResponseBody().write(daten);
    }
}
