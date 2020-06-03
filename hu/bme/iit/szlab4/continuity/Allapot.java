package hu.bme.iit.szlab4.continuity;

/*
 * K�ls� men�b�l �rkez� parancsok:
 * 		jatek: 		Mentett j�t�k ind�t�sa
 * 		ujJatek: 	�j j�t�k ind�t�sa
 * 		keszitok:	K�sz�t�k panel megjelen�t�se - Grafika oszt�lyban kezelve
 * 		Kilep:		Kil�p�s ment�s n�lk�l - Grafika oszt�lyban kezelve
 * 
 * Bels� men�b�l �rkez� parancsok:
 * 		folytat:	Bels� men� elt�ntet�se, j�t�k folytat�sa
 * 		ujrakezd:	Az adott p�lya �jrakezd�se
 * 		atugras:	P�lya �tugr�sa
 * 		mentkilep:	J�t�k ment�se �s kil�p�s a f�men�be
 */

public enum Allapot {
	jatek, ujJatek, keszitok, kilep, 
	folytat, ujrakezd, atugras, mentkilep
}
