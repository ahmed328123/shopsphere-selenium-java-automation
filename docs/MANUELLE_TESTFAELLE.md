# Manuelle Testfälle

| ID | Bereich | Szenario | Erwartetes Ergebnis |
|---|---|---|---|
| TC-001 | Anmeldung | Gültiges Testkonto | Produktkatalog erscheint |
| TC-002 | Anmeldung | Falsches Passwort | Deutsche Fehlermeldung erscheint |
| TC-003 | Suche | Tastatur suchen | Ein passendes Produkt erscheint |
| TC-004 | Filter | Elektronik auswählen | Drei Produkte erscheinen |
| TC-005 | Filter | Sport auswählen | Ein Produkt erscheint |
| TC-006 | Sortierung | Preis aufsteigend | Günstigstes Produkt steht zuerst |
| TC-007 | Warenkorb | Produkt hinzufügen | Warenkorbanzahl steigt |
| TC-008 | Checkout | Pflichtfelder leer | Validierung erscheint |
| TC-009 | API | Unbekannte Produkt-ID | HTTP 404 |
| TC-010 | Datenbank | Positive Bestände | Keine ungültigen Datensätze |
