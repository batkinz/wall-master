package hu.bme.iit.szlab4.continuity;

import java.util.ArrayList;
import java.util.List;

// TÛZKÕ
public class Tile {
	private boolean kezdo;
	private boolean ures;
	private final int szelesseg;
	private final int magassag;
	private final List<Palyaelem> palyaelemlista;
	private final Pozicio tilePoz;
    // hányadik tile az aktuális példány
    private final int sajatSzam;

	// Tile konstruktor
	public Tile(int szam){
		ures = false;
		sajatSzam = szam;
		palyaelemlista = new ArrayList<Palyaelem>();
		tilePoz = new Pozicio();
		szelesseg = 500;
		magassag = 300;
	}
	
	// kezdo setter
	public void setKezdo(boolean k) {
		kezdo = k;
	}

	// listahossz getter
	public int getListaHossz() {
		return palyaelemlista.size();
	}

	// kezdo getter
	public boolean getKezdo() {
		return kezdo;
	}

	// Pozíció getter
	public Pozicio getKoord() {
		return new Pozicio(tilePoz.x,tilePoz.y);
	}

	// pályaelem getter
	public Palyaelem getPalyaelem(int index) {
		return palyaelemlista.get(index);
	}

	// ures getter
	public boolean getUres() {
		return ures;
	}

	// pályaelem lista feltöltõ metódus
	public void init(Palyaelem pe) {
		palyaelemlista.add(pe);
	}

	// ha egy játékos metszi az adott tile-t, akkor ez a függvény fog meghívódni
	// és elvégzi a megfelelõ mûveleteket
	public boolean interakt(Jatekos j, boolean tileEgyforma) {
		// ha a két Tile megegyezik, akkor a játékos átmehet a másik Tile-ba
		if (tileEgyforma) {
			j.atmegy(this);
			return false;

			// ha a két tile nem egyezik meg, akkor kiszámoljuk, hogy melyik
			// irányba mozgott a játékos
		} else{
		
			Tile jAktTile = j.getAktTile();
			Pozicio jAktTilePoz = jAktTile.getKoord();
			int irany;
			if (jAktTilePoz.x == tilePoz.x) {
				// lefelé esett
				if (jAktTilePoz.y > tilePoz.y)
					irany = 270;

				// felfele ugrott
				else
					irany = 90;
			} else {
				// jobbra mozgott
				if (jAktTilePoz.x < tilePoz.x)
					irany = 0;

				// balra mozgott
				else
					irany = 180;
			}

			// ha lefelé esik, akkor a játékosnak sajnos meg kell halnia, ehhez
			// true lesz a visszatérési érték amivel majd a hívó függvény
			// foglalkozik
			if (irany == 270)
				return true;

			// egyébként ha más irányba mozgott a játékos, akkor mivel nem
			// egyezik meg a két tile akkor a játékos korrekcióra szorul.
			// Így false a visszatérési érték, mert nem halt meg a játékos
			else {
				korrekcio(j);
				return false;
			}
		}
	}

	// a játékos korrekciója ha olyan tile-al lépett interaktusba, amelyikbe nem
	// mehet át
	public void korrekcio(Jatekos j) {
		Pozicio jUjPoz = j.getAktKoord();
		Tile jAktTile = j.getAktTile();
		Pozicio jAktTilePoz = jAktTile.getKoord();
		// kiszámoljuk, hogy melyik irányba mozgott a játékos
		int irany;
		// ha megegyezik a két tile x koordinátája, akkor csak az y-ban különbözhetnek
		// így kizárhatunk két irányt
		if (jAktTilePoz.x == tilePoz.x) {
			// lefelé esett
			if (jAktTilePoz.y > tilePoz.y)
				irany = 270;

			// felfele ugrott
			else
				irany = 90;
			
		// ha az x koordinátájuk nem egyezett meg, akkor az y koordinátájuk egyezik meg
		// és így is ki tudtunk zárni két irányt
		} else {
			// jobbra mozgott
			if (jAktTilePoz.x < tilePoz.x)
				irany = 0;

			// balra mozgott
			else
				irany = 180;
		}

		int jMeret = j.getMeret()/2;
		// ha jobbra mozgott, akkor az x koordinátáját max-ra korrigáljuk
		if (irany == 0) {
			jUjPoz.x = szelesseg-jMeret-1; // mert 500 -1 - 3 = 496 - ra kell visszakorrgálni
			j.setAktKoord(jUjPoz);

			// ha felfele ugrott, akkor az y koordinátáját max-ra korrigáljuk
			// és az ugrás változóját 0-zuk, hogy innentõl kezdve ne menjen bele többször ebbe a tile-ba
		} else if (irany == 90) {
			jUjPoz.y = magassag-2*jMeret-1;
			j.setAktKoord(jUjPoz);
			j.setUgras(0);

			// ha balra mozgott, akkor az x koordinátáját 0-ra korrigáljuk
		} else if (irany == 180) {
			jUjPoz.x = jMeret;
			j.setAktKoord(jUjPoz);

			// a lefelé esett irányt azért nem is vizsgáljuk, mert ebben az esetben meg sem
			// hívódik a korrekció függvény
		}
	}

	// !!! ez az osztálydiagramon void volt !!!
	public boolean metszette(Jatekos j) {
		Pozicio jGlob = new Pozicio();
		Pozicio jPoz = j.getAktKoord();
		Tile jAktTile = j.getAktTile();
		if (jAktTile.equals(this)) return false;
		// lekérdezzük a játékos méretét, majd elfelezzük, mert így tudjuk
		// ellenõrizni a pontos metszést
		int jMeret = j.getMeret()/2;
		// A játékos koodinátit átváltjuk globális koordinátákba.
		jGlob.x = jAktTile.getKoord().x*szelesseg + jPoz.x;
		jGlob.y = jAktTile.getKoord().y*magassag + jPoz.y;
		
		
		/*A játékos a 4 koordinátáját kiszámoljuk
		 * - alapértelmezetten a középsõ 
		 * az átláthatóság érdekében:
		 *  
		 *  jp4------jp3
		 *  |        |
		 *  |        |
		 *  |        |
		 *  |        |
		 *  |        |
		 *  jp1------jp2
		 *  
		 *  
		 */
		
		// bal alsó sarka
		Pozicio jp1 = new Pozicio(jGlob.x-jMeret,jGlob.y-2*jMeret);
		// jobb alsó sarka
		Pozicio jp2 = new Pozicio(jGlob.x+jMeret,jGlob.y-2*jMeret);
		// jobb felsõ sarka
		Pozicio jp3 = new Pozicio(jGlob.x+jMeret,jGlob.y+2*jMeret);
		// bal felsõ sarka
		Pozicio jp4 = new Pozicio(jGlob.x-jMeret,jGlob.y+2*jMeret);
		
		
		// mind a 4 pontot ellenõrizzük, ha bármelyik pont benne ban a tile-ban 
		// akkor már metszette a játékos a tile-t
		// jp1 ellenõrzés
		if ((tilePoz.x*szelesseg <= jp1.x) && (jp1.x < tilePoz.x*szelesseg + szelesseg)) {
			if((tilePoz.y*magassag <= jp1.y) && (jp1.y < tilePoz.y*magassag + magassag)) return true;
		}

		// jp2 ellenõrzés
		if ((tilePoz.x*szelesseg <= jp2.x) && (jp2.x < tilePoz.x*szelesseg + szelesseg)) {
			if((tilePoz.y*magassag <= jp2.y) && (jp2.y < tilePoz.y*magassag + magassag)) return true;
		}
		
		// jp3 ellenõrzés
		if ((tilePoz.x*szelesseg <= jp3.x) && (jp3.x < tilePoz.x*szelesseg + szelesseg)) {
			if((tilePoz.y*magassag <= jp3.y) && (jp3.y < tilePoz.y*magassag + magassag)) return true;
		}
		
		// jp4 ellenõrzés
		if ((tilePoz.x*szelesseg <= jp4.x) && (jp4.x < tilePoz.x*szelesseg + szelesseg)) {
			if((tilePoz.y*magassag <= jp4.y) && (jp4.y < tilePoz.y*magassag + magassag)) return true;
		}
		
		return false;
	}
	
	// szélesség getter
	public int getSzelesseg(){
		return szelesseg;
	}
	
	// magasság getter
	public int getMagassag(){
		return magassag;
	}
	
	// Pozicio setter
	public void setKoord(Pozicio p) {
		tilePoz.x = p.x;
		tilePoz.y = p.y;
	}
	
	public void setUres(){
		ures = true;
	}
	
	public int getSajatSzam(){
		return sajatSzam;
	}
}