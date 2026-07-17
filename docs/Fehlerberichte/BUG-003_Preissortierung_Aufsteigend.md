# BUG-003 – Preissortierung aufsteigend funktioniert nicht

| Feld | Wert |
|---|---|
| Status | Offen |
| Schweregrad | Mittel |
| Priorität | Hoch |
| Version | ShopSphere 1.0 |
| Automatisierter Test | `preissortierungAufsteigend` |

## Schritte zur Reproduktion
1. ShopSphere öffnen und mit dem Testkonto anmelden.
2. In der Produktübersicht „Preis: aufsteigend“ auswählen.

## Erwartetes Ergebnis
Das günstigste Produkt steht zuerst.

## Tatsächliches Ergebnis
Die ursprüngliche Reihenfolge bleibt bestehen.

## Reproduzierbarkeit
100 Prozent.

## Technische Ursache
Nach Änderung des Auswahlfelds wird die Render-Funktion in Version 1.0 nicht
aufgerufen.

## Nachweis
Der fehlgeschlagene Selenium-Test erzeugt automatisch einen Screenshot im
Ordner `screenshots/`.
