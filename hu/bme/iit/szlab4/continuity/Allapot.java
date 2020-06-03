package hu.bme.iit.szlab4.continuity;

/*
 * Külsõ menübõl érkezõ parancsok:
 * 		jatek: 		Mentett játék indítása
 * 		ujJatek: 	Új játék indítása
 * 		keszitok:	Készítõk panel megjelenítése - Grafika osztályban kezelve
 * 		Kilep:		Kilépés mentés nélkül - Grafika osztályban kezelve
 * 
 * Belsõ menübõl érkezõ parancsok:
 * 		folytat:	Belsõ menü eltüntetése, játék folytatása
 * 		ujrakezd:	Az adott pálya újrakezdése
 * 		atugras:	Pálya átugrása
 * 		mentkilep:	Játék mentése és kilépés a fõmenübe
 */

public enum Allapot {
	jatek, ujJatek, keszitok, kilep, 
	folytat, ujrakezd, atugras, mentkilep
}
