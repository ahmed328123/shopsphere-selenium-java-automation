# ShopSphere – Selenium-Java-Testautomatisierung

## Version 5.1 – Stabilisierung der UI-Klicks

Diese Version behebt die nicht beabsichtigten
`ElementClickInterceptedException`-Fehler beim Hinzufügen eines Produkts:

- Produktkarten bewegen sich beim Hover nicht mehr.
- Elemente werden vor dem Klick in die Bildschirmmitte gescrollt.
- Der normale Selenium-Klick wird zuerst verwendet.
- Bei einer echten Überdeckung wird kontrolliert auf JavaScript-Klick
  zurückgefallen.
- Sichtbare Browserfenster werden maximiert.
- Die fünf dokumentierten Produktfehler bleiben bewusst bestehen.


Ein vollständig deutschsprachiges QA-Portfolio-Projekt mit moderner,
farbenfroher E-Commerce-Oberfläche. Es demonstriert UI-, API- und
Datenbanktests sowie professionelles Fehler- und Testreporting.

## Erwarteter Teststatus

| Kennzahl | Wert |
|---|---:|
| Automatisierte Tests | 35 |
| Erwartet bestanden | 30 |
| Erwartet fehlgeschlagen | 5 |
| Dokumentierte Fehler | 5 |

Die fünf Fehler sind absichtlich in ShopSphere Version 1.0 enthalten und durch
automatisierte Tests reproduzierbar. Alle Fehlerberichte sind unter
`docs/Fehlerberichte/` dokumentiert.

## Technologien

Java 21, Selenium WebDriver, TestNG, Maven, Page Object Model, REST Assured,
H2, JDBC, GitHub Actions und Jenkins.

## Voraussetzungen

- Java 21
- Apache Maven 3.9+
- Google Chrome oder Microsoft Edge

## Alle Tests sichtbar ausführen

```powershell
mvn clean test -Pfull "-Dheadless=false" "-Dbrowser=chrome" "-DslowMo=500" "-DkeepOpen=1000"
```

Erwartet: 30 bestanden und 5 fehlgeschlagen. Wegen der bekannten Fehler zeigt
Maven am Ende `BUILD FAILURE`. Das ist für Version 1.0 korrekt.

## Vollständigen Lauf abschließen und Fehler im Bericht behalten

```powershell
mvn clean test -Pfull "-Dheadless=false" "-Dbrowser=chrome" "-DslowMo=500" "-DkeepOpen=1000" "-Dmaven.test.failure.ignore=true"
```

## Nur Smoke-Tests

```powershell
mvn clean test -Psmoke "-Dheadless=false" "-Dbrowser=chrome" "-DslowMo=500"
```

## Nur bekannte Fehler reproduzieren

```powershell
mvn clean test -Pbekannte-fehler "-Dheadless=false" "-Dbrowser=chrome" "-DslowMo=700" "-Dmaven.test.failure.ignore=true"
```

## Headless-Ausführung

```powershell
mvn clean test -Pfull "-Dheadless=true" "-Dbrowser=chrome" "-Dmaven.test.failure.ignore=true"
```

## Testberichte

- Maven/TestNG: `target/surefire-reports/`
- Deutsche Zusammenfassung: `reports/Testlauf_Zusammenfassung.md`
- Screenshots fehlgeschlagener UI-Tests: `screenshots/`

## Testkonto

```text
E-Mail: qa@shopsphere.local
Passwort: ShopSphere-QA-2026!A7x9
```

## Bekannte Fehler

| ID | Test | Status |
|---|---|---|
| BUG-001 | kategoriefilterElektronik | Offen |
| BUG-002 | kategoriefilterSport | Offen |
| BUG-003 | preissortierungAufsteigend | Offen |
| BUG-004 | preissortierungAbsteigend | Offen |
| BUG-005 | bewertungssortierungAbsteigend | Offen |

## Projektstruktur

```text
src/main/java                 Lokaler Server und REST-API
src/main/resources/web        Moderne deutsche Benutzeroberfläche
src/test/java/.../pages       Page Objects
src/test/java/.../tests       UI-, API- und Datenbanktests
docs/Fehlerberichte           Detaillierte Bug Reports
reports                       Deutsche Testlauf-Zusammenfassung
screenshots                   Screenshots bei UI-Fehlern
```

## Autor

Ahmed Ali  
Software Tester | QA Engineer | Test Automation Engineer
