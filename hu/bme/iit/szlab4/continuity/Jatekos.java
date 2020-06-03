package hu.bme.iit.szlab4.continuity;

import java.util.ArrayList;

public class Jatekos {
	private boolean reszletes;
	private static int defaultUgras = 30;
	private int kulcsSzam;
	private final int meret = 20;
	private int szog;
	private int ugras;
	private int xSeb;
	private Pozicio aktKoord;
	private final Pozicio kezdoKoord;
	private Tile aktTile;
	private Tile kezdoTile;
	private final int szam; // Ebbõl a paraméterbõl tudja majd a játékos, hogy melyik billentyûkre kell reagálnia.
	private boolean ugrik;
	private final int lepes = 7;
	
	public Jatekos(int sorszam){
		szam=sorszam;
		ugras = 0;
		aktKoord = new Pozicio();
		kezdoKoord = new Pozicio();
		ugrik = false;
		reszletes = false;
	}
	
	public int getSzam(){
		return szam;
	}
	
	public void setUgrik(boolean b){
		ugrik = b;
	}
	
	public boolean getUgrik(){
		return ugrik;
	}
		
	//Ez a függvény kezeli a tile-ok között történõ mozgásért.
	public void atmegy(Tile t){
		//ha a céltile tõlünk jobbra van ...
		if (t.getKoord().x>aktTile.getKoord().x)
		{
			//az új tile bal oldalára kerülünk
			aktKoord.x = meret/2;			
		}
		//ha a céltile tõlünk balra van ...
		else if(t.getKoord().x<aktTile.getKoord().x)
		{
			// az új tile jobb oldalára kerülünk
			aktKoord.x = aktTile.getSzelesseg()-meret/2-1;
		}
		//Ha a céltile alattunk van ...
		else if(t.getKoord().y<aktTile.getKoord().y)
		{
			// az új tile tetejére kerülünk
			aktKoord.y = aktTile.getMagassag()-meret-1; // EZT JAVÍTOTTUK MERT A JÁTÉKOS TÉGLALAP ALAKÚ
		}
		//ha a céltile felettünk van ...
		else if(t.getKoord().y>aktTile.getKoord().y)
		{
			// az új tile aljára kerülünk
			aktKoord.y = meret+1;  // EZT JAVÍTOTTUK MERT A JÁTÉKOS TÉGLALAP ALAKÚ
		}
		// Új tile-referencia beállítása
		aktTile = t;
	}
	//ugras getter
	public int getUgras()
	{
		return ugras;
	}
	
	//defaultUgras getter
	public int getDefaultUgras()
	{
		return defaultUgras;
	}
	
	//AktKoord getter
	public Pozicio getAktKoord(){
		Pozicio koord = new Pozicio(aktKoord.x, aktKoord.y);	
		return koord;		
	}
	//aktTile getter
	public Tile getAktTile(){
		return aktTile;		
	}
	//szog getter
	public int getIrany(){
		return szog;		
	}
	//kulcsszam getter
	public int getKulcsSzam(){
		
		return kulcsSzam;		
	}
	//meret getter
	public int getMeret(){
		
		return meret;		
	}
	//reszletes getter
	public boolean getReszletes(){
		
		return reszletes;		
	}
	//Ez a függvény kezeli azt, ha a játékos felvesz egy kulcsot
	public void kulcsFelvesz(){
		//megnöveljük a kulcsszámot eggyel
		kulcsSzam++;
	}
	//Ez a függvény a játékos halálakor hívódik
	public void meghal(){
		//Az új aktuális tile a kezdõtile
		aktTile=kezdoTile;
		//Az új aktuális koord a kezdõkoord
		aktKoord=new Pozicio(kezdoKoord.x,kezdoKoord.y);
		ugras = 0;
		xSeb = 0;
		ugrik = false;
	}
	//Ez a függvény mozgatja majd a játékost a lenyomott billentyû függvényében
	public void reszletesMozgat(Billentyu b){
		//jobbra mozgás
		//a feltétel: HA az 1-es játékosról van szó, akkor a j1_jobb gombot nézzük, ha a 2-esrõl, akkor a j2_jobbot
		if (((szam==1)&&(b.j1_jobb==true) || (szam==2)&&(b.j2_jobb)))
		{
			xSeb = lepes;
			// ha jobbra mozog a játékos, akkor megnöveljük az x koordinátájának értékét
			aktKoord.x+=xSeb;
			// ha oldalra kimennénk a tile-ból...
			if(aktKoord.x+meret/2>aktTile.getSzelesseg() && aktTile.getKoord().x==1)
				aktKoord.x = aktTile.getSzelesseg()-meret/2+1;
			// MÓDOSÍTJUK A SZÖGET
			szog = (int) Math.toDegrees(Math.atan(ugras/xSeb));
			if(szog<0) szog += 360;
			
		}
		//balra mozgás
		//a feltétel: HA az 1-es játékosról van szó, akkor a j1_bal gombot nézzük, ha a 2-esrõl, akkor a j2_balt
		else if(((szam==1)&&(b.j1_bal==true) || (szam==2)&&(b.j2_bal)))
		{
			xSeb = -1*lepes;
			//ha balra mozog a játékos, akkor csökkentjük az x koordinátájának értékét
			aktKoord.x+=xSeb;
			if(aktKoord.x-meret/2<=0 && aktTile.getKoord().x==0)
				aktKoord.x = meret/2;
			// MÓDOSÍTJUK A SZÖGET
			szog = (int) Math.toDegrees(Math.atan(ugras/xSeb));
			if(szog<0) szog += 360;
		} else {
			xSeb = 0;
			aktKoord.x += xSeb; //jelzésként van csak itt, nem megyünk se jobbra se balra
			//MÓDOSÍTJUK A SZÖGET
			if(ugras >= 0){	//csak az Y komponens érvényes a sebességbõl
				szog = 90;	//ha az felfelé mutat...
			} else {
				szog = 270;	//ha az lefelé mutat...
			}
		}
	}
	//aktKoord setter
	public void setAktKoord(Pozicio p){
		aktKoord.x = p.x;
		aktKoord.y = p.y;
	}
	//szog setter
	public void setIrany(int irany){
		szog=irany;
	}
	//kezdoKoord setter
	public void setKezdoKoord(Pozicio p){
		kezdoKoord.x = p.x;
		kezdoKoord.y = p.y;
	}
	//kezdoTile setter
	public void setKezdoTile(Tile t){
		kezdoTile=t;
	}
	//reszletes setter
	public void setReszletes(boolean nezet){
		reszletes=nezet;
	}
	//Ez a függvény állítja be az ugrás értékét a defaultra, amennyiben egy játékos földet ér.
	public void setToDefaultUgras(){
		ugras=defaultUgras;
	}
	//ugras setter
	public void setUgras(int ujUgras){
		ugras=ujUgras;
	}
	
	// Ez a függvény kezeli az ugrást
	public void ugras(Billentyu b){
		
		//a gravitáció értéke
		int grav = 5;
		
		if(szam == 1){	//1. Játékos
				if(b.j1_fel && !ugrik){
					ugrik = true;
					ugras = defaultUgras;
				}
				//az ugrás értékét csökkentjük a gravitáció által,
				// bizonyos határokig
				if(ugras>=-20)
					ugras -= grav;
				//aktuális függõleges irányú koordinátához hozzáadjuk az ugrás értékét
				aktKoord.y += ugras;
				ugrik = true;
				if (aktTile.getKoord().y==1 && aktKoord.y+meret>aktTile.getMagassag()){
					aktKoord.y = aktTile.getMagassag()-meret-1;
					ugras = 0;
				}
				// majd az elõzõ x sebességgel és a módosított y sebességgel újraszámoljuk az irányt
				if(xSeb==0){
					if(ugras >= 0){	//csak az Y komponens érvényes a sebességbõl
						szog = 90;	//ha az felfelé mutat...
					} else {
						szog = 270;	//ha az lefelé mutat...
					}
				}
				else {
					szog = (int) Math.toDegrees(Math.atan(ugras/(xSeb)));
					if(szog<0) szog += 360;
				}
		}
		
		if(szam == 2){	//2. Játékos
				if(b.j2_fel && !ugrik){
					ugrik = true;
					ugras = defaultUgras;
				}				
				
				//az ugrás értékét csökkentjük a gravitáció által
				ugras -= grav;
				//aktuális függõleges irányú koordinátához hozzáadjuk az ugrás értékét
				aktKoord.y += ugras;				
				
				ugrik = true;
				// majd az elõzõ x sebességgel és a módosított y sebességgel újraszámoljuk az irányt
				if(xSeb==0){
					if(ugras >= 0){	//csak az Y komponens érvényes a sebességbõl
						szog = 90;	//ha az felfelé mutat...
					} else {
						szog = 270;	//ha az lefelé mutat...
					}
				}
				else {
					szog = (int) Math.toDegrees(Math.atan(ugras/(xSeb)));
					if(szog<0) szog += 360;
				}
		}
	}
	
	// xSeb getter
	public int getXSeb(){
		return xSeb;
	}
	
	// aktTile setter
	public void setAktTile(Tile t){
		aktTile = t;
	}
	
	// xSeb setter
	public void setXSeb(int x){
		xSeb = x;
	}
}
