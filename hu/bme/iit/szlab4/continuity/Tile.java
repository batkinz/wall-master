package hu.bme.iit.szlab4.continuity;

import java.util.ArrayList;
import java.util.List;

// T�ZK�
public class Tile {
	private boolean kezdo;
	private boolean ures;
	private final int szelesseg;
	private final int magassag;
	private final List<Palyaelem> palyaelemlista;
	private final Pozicio tilePoz;
    // h�nyadik tile az aktu�lis p�ld�ny
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

	// Poz�ci� getter
	public Pozicio getKoord() {
		return new Pozicio(tilePoz.x,tilePoz.y);
	}

	// p�lyaelem getter
	public Palyaelem getPalyaelem(int index) {
		return palyaelemlista.get(index);
	}

	// ures getter
	public boolean getUres() {
		return ures;
	}

	// p�lyaelem lista felt�lt� met�dus
	public void init(Palyaelem pe) {
		palyaelemlista.add(pe);
	}

	// ha egy j�t�kos metszi az adott tile-t, akkor ez a f�ggv�ny fog megh�v�dni
	// �s elv�gzi a megfelel� m�veleteket
	public boolean interakt(Jatekos j, boolean tileEgyforma) {
		// ha a k�t Tile megegyezik, akkor a j�t�kos �tmehet a m�sik Tile-ba
		if (tileEgyforma) {
			j.atmegy(this);
			return false;

			// ha a k�t tile nem egyezik meg, akkor kisz�moljuk, hogy melyik
			// ir�nyba mozgott a j�t�kos
		} else{
		
			Tile jAktTile = j.getAktTile();
			Pozicio jAktTilePoz = jAktTile.getKoord();
			int irany;
			if (jAktTilePoz.x == tilePoz.x) {
				// lefel� esett
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

			// ha lefel� esik, akkor a j�t�kosnak sajnos meg kell halnia, ehhez
			// true lesz a visszat�r�si �rt�k amivel majd a h�v� f�ggv�ny
			// foglalkozik
			if (irany == 270)
				return true;

			// egy�bk�nt ha m�s ir�nyba mozgott a j�t�kos, akkor mivel nem
			// egyezik meg a k�t tile akkor a j�t�kos korrekci�ra szorul.
			// �gy false a visszat�r�si �rt�k, mert nem halt meg a j�t�kos
			else {
				korrekcio(j);
				return false;
			}
		}
	}

	// a j�t�kos korrekci�ja ha olyan tile-al l�pett interaktusba, amelyikbe nem
	// mehet �t
	public void korrekcio(Jatekos j) {
		Pozicio jUjPoz = j.getAktKoord();
		Tile jAktTile = j.getAktTile();
		Pozicio jAktTilePoz = jAktTile.getKoord();
		// kisz�moljuk, hogy melyik ir�nyba mozgott a j�t�kos
		int irany;
		// ha megegyezik a k�t tile x koordin�t�ja, akkor csak az y-ban k�l�nb�zhetnek
		// �gy kiz�rhatunk k�t ir�nyt
		if (jAktTilePoz.x == tilePoz.x) {
			// lefel� esett
			if (jAktTilePoz.y > tilePoz.y)
				irany = 270;

			// felfele ugrott
			else
				irany = 90;
			
		// ha az x koordin�t�juk nem egyezett meg, akkor az y koordin�t�juk egyezik meg
		// �s �gy is ki tudtunk z�rni k�t ir�nyt
		} else {
			// jobbra mozgott
			if (jAktTilePoz.x < tilePoz.x)
				irany = 0;

			// balra mozgott
			else
				irany = 180;
		}

		int jMeret = j.getMeret()/2;
		// ha jobbra mozgott, akkor az x koordin�t�j�t max-ra korrig�ljuk
		if (irany == 0) {
			jUjPoz.x = szelesseg-jMeret-1; // mert 500 -1 - 3 = 496 - ra kell visszakorrg�lni
			j.setAktKoord(jUjPoz);

			// ha felfele ugrott, akkor az y koordin�t�j�t max-ra korrig�ljuk
			// �s az ugr�s v�ltoz�j�t 0-zuk, hogy innent�l kezdve ne menjen bele t�bbsz�r ebbe a tile-ba
		} else if (irany == 90) {
			jUjPoz.y = magassag-2*jMeret-1;
			j.setAktKoord(jUjPoz);
			j.setUgras(0);

			// ha balra mozgott, akkor az x koordin�t�j�t 0-ra korrig�ljuk
		} else if (irany == 180) {
			jUjPoz.x = jMeret;
			j.setAktKoord(jUjPoz);

			// a lefel� esett ir�nyt az�rt nem is vizsg�ljuk, mert ebben az esetben meg sem
			// h�v�dik a korrekci� f�ggv�ny
		}
	}

	// !!! ez az oszt�lydiagramon void volt !!!
	public boolean metszette(Jatekos j) {
		Pozicio jGlob = new Pozicio();
		Pozicio jPoz = j.getAktKoord();
		Tile jAktTile = j.getAktTile();
		if (jAktTile.equals(this)) return false;
		// lek�rdezz�k a j�t�kos m�ret�t, majd elfelezz�k, mert �gy tudjuk
		// ellen�rizni a pontos metsz�st
		int jMeret = j.getMeret()/2;
		// A j�t�kos koodin�tit �tv�ltjuk glob�lis koordin�t�kba.
		jGlob.x = jAktTile.getKoord().x*szelesseg + jPoz.x;
		jGlob.y = jAktTile.getKoord().y*magassag + jPoz.y;
		
		
		/*A j�t�kos a 4 koordin�t�j�t kisz�moljuk
		 * - alap�rtelmezetten a k�z�ps� 
		 * az �tl�that�s�g �rdek�ben:
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
		
		// bal als� sarka
		Pozicio jp1 = new Pozicio(jGlob.x-jMeret,jGlob.y-2*jMeret);
		// jobb als� sarka
		Pozicio jp2 = new Pozicio(jGlob.x+jMeret,jGlob.y-2*jMeret);
		// jobb fels� sarka
		Pozicio jp3 = new Pozicio(jGlob.x+jMeret,jGlob.y+2*jMeret);
		// bal fels� sarka
		Pozicio jp4 = new Pozicio(jGlob.x-jMeret,jGlob.y+2*jMeret);
		
		
		// mind a 4 pontot ellen�rizz�k, ha b�rmelyik pont benne ban a tile-ban 
		// akkor m�r metszette a j�t�kos a tile-t
		// jp1 ellen�rz�s
		if ((tilePoz.x*szelesseg <= jp1.x) && (jp1.x < tilePoz.x*szelesseg + szelesseg)) {
			if((tilePoz.y*magassag <= jp1.y) && (jp1.y < tilePoz.y*magassag + magassag)) return true;
		}

		// jp2 ellen�rz�s
		if ((tilePoz.x*szelesseg <= jp2.x) && (jp2.x < tilePoz.x*szelesseg + szelesseg)) {
			if((tilePoz.y*magassag <= jp2.y) && (jp2.y < tilePoz.y*magassag + magassag)) return true;
		}
		
		// jp3 ellen�rz�s
		if ((tilePoz.x*szelesseg <= jp3.x) && (jp3.x < tilePoz.x*szelesseg + szelesseg)) {
			if((tilePoz.y*magassag <= jp3.y) && (jp3.y < tilePoz.y*magassag + magassag)) return true;
		}
		
		// jp4 ellen�rz�s
		if ((tilePoz.x*szelesseg <= jp4.x) && (jp4.x < tilePoz.x*szelesseg + szelesseg)) {
			if((tilePoz.y*magassag <= jp4.y) && (jp4.y < tilePoz.y*magassag + magassag)) return true;
		}
		
		return false;
	}
	
	// sz�less�g getter
	public int getSzelesseg(){
		return szelesseg;
	}
	
	// magass�g getter
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