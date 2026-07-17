# ShopSphere – Selenium-Java-Testautomatisierung

## Version 5.1 – Stabilisierung der UI-Klicks

Diese Version behebt die nicht beabsichtigten
`ElementClickInterceptedException`-Fehler beim Hinzufügen eines Produkts:

- Produktkarten bewegen sich beim Hover nicht mehr.
- Elemente werden vor dem Klick in die Bildschirmmitte gescrollt.
- Der normale Selenium-Klick wird zuerst verwendet.
- Bei einer echten Überdeckung wird kontrolliert auf JavaScript-Klick zurückgefallen.
- Sichtbare Browserfenster werden maximiert.
- Die fünf dokumentierten Produktfehler bleiben bewusst bestehen.

Ein vollständig deutschsprachiges QA-Portfolio-Projekt mit moderner,
farbenfroher E-Commerce-Oberfläche. Es demonstriert UI-, API- und
Datenbanktests sowie professionelles Fehler- und Testreporting.

---

# Erwarteter Teststatus

| Kennzahl | Wert |
|---|---:|
| Automatisierte Tests | 35 |
| Erwartet bestanden | 30 |
| Erwartet fehlgeschlagen | 5 |
| Dokumentierte Fehler | 5 |

Die fünf Fehler sind absichtlich in ShopSphere Version 1.0 enthalten und
werden durch automatisierte Regressionstests zuverlässig reproduziert.

Alle Fehlerberichte befinden sich unter

```
docs/Fehlerberichte/
```

---

# Technologien

- Java 21
- Selenium WebDriver
- TestNG
- Maven
- Page Object Model (POM)
- REST Assured
- H2 Database
- JDBC
- Git
- GitHub
- Jenkins

---

# Voraussetzungen

- Java 21
- Apache Maven 3.9+
- Google Chrome oder Microsoft Edge

---

# Alle Tests sichtbar ausführen

```powershell
mvn clean test -Pfull "-Dheadless=false" "-Dbrowser=chrome" "-DslowMo=500" "-DkeepOpen=1000"
```

Erwartetes Ergebnis:

- 30 Tests bestanden
- 5 Tests fehlgeschlagen

Da die bekannten Produktfehler absichtlich enthalten sind,
meldet Maven am Ende:

```
BUILD FAILURE
```

Dies ist das erwartete Verhalten.

---

# Vollständigen Lauf abschließen und Fehler dokumentieren

```powershell
mvn clean test -Pfull "-Dheadless=false" "-Dbrowser=chrome" "-DslowMo=500" "-DkeepOpen=1000" "-Dmaven.test.failure.ignore=true"
```

---

# Nur Smoke-Tests

```powershell
mvn clean test -Psmoke "-Dheadless=false" "-Dbrowser=chrome" "-DslowMo=500"
```

---

# Nur bekannte Fehler reproduzieren

```powershell
mvn clean test -Pbekannte-fehler "-Dheadless=false" "-Dbrowser=chrome" "-DslowMo=700" "-Dmaven.test.failure.ignore=true"
```

---

# Headless-Ausführung

```powershell
mvn clean test -Pfull "-Dheadless=true" "-Dbrowser=chrome" "-Dmaven.test.failure.ignore=true"
```

---

# Jenkins CI/CD

Das Projekt enthält eine vollständig konfigurierte Jenkins-Pipeline.

Die Pipeline führt automatisch folgende Schritte aus:

1. Repository auschecken
2. Maven Build starten
3. Alle automatisierten Tests ausführen
4. JUnit-Testberichte veröffentlichen
5. Screenshots archivieren
6. Testberichte archivieren

---

## Jenkins Build Status

Nach einem vollständigen Testlauf lautet der Jenkins-Status:

```
UNSTABLE
```

Dieser Status ist **erwartet**.

Die CI/CD-Pipeline funktioniert vollständig und ohne Konfigurationsfehler.

Der Status **UNSTABLE** entsteht ausschließlich dadurch,
dass die Anwendung derzeit fünf bekannte funktionale Fehler enthält.

Es handelt sich **nicht** um einen Fehler der Jenkins-Pipeline.

---

## Jenkins Testergebnis

| Ergebnis | Anzahl |
|---|---:|
| Gesamte Tests | 35 |
| Erfolgreich | 30 |
| Fehlgeschlagen | 5 |

---

# Bekannte Fehler

| Bug-ID | Test | Status |
|---|---|---|
| BUG-001 | kategoriefilterElektronik | Offen |
| BUG-002 | kategoriefilterSport | Offen |
| BUG-003 | preissortierungAufsteigend | Offen |
| BUG-004 | preissortierungAbsteigend | Offen |
| BUG-005 | bewertungssortierungAbsteigend | Offen |

Diese Fehler werden durch automatisierte Regressionstests
bei jedem Pipeline-Lauf reproduziert und dokumentiert.

---

# Testberichte

Nach jedem Testlauf werden folgende Artefakte erzeugt:

- Maven/TestNG-Berichte
- JUnit-Berichte
- Jenkins Test Report
- Screenshots fehlgeschlagener UI-Tests
- Deutsche Testlauf-Zusammenfassung

Speicherorte:

```
target/surefire-reports/
reports/
screenshots/
```

---

# Testkonto

```text
E-Mail: qa@shopsphere.local
Passwort: ShopSphere-QA-2026!A7x9
```

---

# Projektstruktur

```text
src/main/java                 Lokaler Server und REST-API
src/main/resources/web        Moderne deutsche Benutzeroberfläche
src/test/java/.../pages       Page Objects
src/test/java/.../tests       UI-, API- und Datenbanktests
docs/Fehlerberichte           Detaillierte Bug Reports
reports                       Deutsche Testlauf-Zusammenfassung
screenshots                   Screenshots bei UI-Fehlern
Jenkinsfile                   Jenkins Pipeline
README.md                     Projektdokumentation
```

---

# Autor

**Ahmed Ali**

Software Tester | QA Engineer | Test Automation Engineer