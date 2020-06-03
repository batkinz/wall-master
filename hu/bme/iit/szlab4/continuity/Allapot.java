package hu.bme.iit.szlab4.continuity;

/*
 * Külső menüből érkező parancsok:
 * 		jatek: 		Mentett játék indítása
 * 		ujJatek: 	Új játék indítása
 * 		keszitok:	Készítők panel megjelenítése - Grafika osztályban kezelve
 * 		Kilep:		Kilépés mentés nélkül - Grafika osztályban kezelve
 * 
 * Belső menüből érkező parancsok:
 * 		folytat:	Belső menü eltüntetése, játék folytatása
 * 		ujrakezd:	Az adott pálya újrakezdése
 * 		atugras:	Pálya átugrása
 * 		mentkilep:	Játék mentése és kilépés a főmenübe
 */

public enum Allapot {
	jatek, ujJatek, keszitok, kilep, 
	folytat, ujrakezd, atugras, mentkilep
}
