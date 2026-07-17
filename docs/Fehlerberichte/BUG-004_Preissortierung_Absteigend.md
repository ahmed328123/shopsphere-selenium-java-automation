# BUG-004 – Preissortierung absteigend funktioniert nicht

| Feld | Wert |
|---|---|
| Status | Offen |
| Schweregrad | Mittel |
| Priorität | Hoch |
| Version | ShopSphere 1.0 |
| Automatisierter Test | `preissortierungAbsteigend` |

## Schritte zur Reproduktion
1. ShopSphere öffnen und mit dem Testkonto anmelden.
2. In der Produktübersicht „Preis: absteigend“ auswählen.

## Erwartetes Ergebnis
Das teuerste Produkt steht zuerst.

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
