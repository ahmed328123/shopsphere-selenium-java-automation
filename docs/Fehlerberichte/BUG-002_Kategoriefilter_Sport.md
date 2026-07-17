# BUG-002 – Kategoriefilter „Sport“ wird nicht angewendet

| Feld | Wert |
|---|---|
| Status | Offen |
| Schweregrad | Mittel |
| Priorität | Hoch |
| Version | ShopSphere 1.0 |
| Automatisierter Test | `kategoriefilterSport` |

## Schritte zur Reproduktion
1. ShopSphere öffnen und mit dem Testkonto anmelden.
2. In der Produktübersicht „Sport“ auswählen.

## Erwartetes Ergebnis
Ein Sportprodukt wird angezeigt.

## Tatsächliches Ergebnis
Alle sechs Produkte bleiben sichtbar.

## Reproduzierbarkeit
100 Prozent.

## Technische Ursache
Nach Änderung des Auswahlfelds wird die Render-Funktion in Version 1.0 nicht
aufgerufen.

## Nachweis
Der fehlgeschlagene Selenium-Test erzeugt automatisch einen Screenshot im
Ordner `screenshots/`.
