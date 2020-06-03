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
	private final int szam; // Ebb�l a param�terb�l tudja majd a j�t�kos, hogy melyik billenty�kre kell reag�lnia.
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
		
	//Ez a f�ggv�ny kezeli a tile-ok k�z�tt t�rt�n� mozg�s�rt.
	public void atmegy(Tile t){
		//ha a c�ltile t�l�nk jobbra van ...
		if (t.getKoord().x>aktTile.getKoord().x)
		{
			//az �j tile bal oldal�ra ker�l�nk
			aktKoord.x = meret/2;			
		}
		//ha a c�ltile t�l�nk balra van ...
		else if(t.getKoord().x<aktTile.getKoord().x)
		{
			// az �j tile jobb oldal�ra ker�l�nk
			aktKoord.x = aktTile.getSzelesseg()-meret/2-1;
		}
		//Ha a c�ltile alattunk van ...
		else if(t.getKoord().y<aktTile.getKoord().y)
		{
			// az �j tile tetej�re ker�l�nk
			aktKoord.y = aktTile.getMagassag()-meret-1; // EZT JAV�TOTTUK MERT A J�T�KOS T�GLALAP ALAK�
		}
		//ha a c�ltile felett�nk van ...
		else if(t.getKoord().y>aktTile.getKoord().y)
		{
			// az �j tile alj�ra ker�l�nk
			aktKoord.y = meret+1;  // EZT JAV�TOTTUK MERT A J�T�KOS T�GLALAP ALAK�
		}
		// �j tile-referencia be�ll�t�sa
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
	//Ez a f�ggv�ny kezeli azt, ha a j�t�kos felvesz egy kulcsot
	public void kulcsFelvesz(){
		//megn�velj�k a kulcssz�mot eggyel
		kulcsSzam++;
	}
	//Ez a f�ggv�ny a j�t�kos hal�lakor h�v�dik
	public void meghal(){
		//Az �j aktu�lis tile a kezd�tile
		aktTile=kezdoTile;
		//Az �j aktu�lis koord a kezd�koord
		aktKoord=new Pozicio(kezdoKoord.x,kezdoKoord.y);
		ugras = 0;
		xSeb = 0;
		ugrik = false;
	}
	//Ez a f�ggv�ny mozgatja majd a j�t�kost a lenyomott billenty� f�ggv�ny�ben
	public void reszletesMozgat(Billentyu b){
		//jobbra mozg�s
		//a felt�tel: HA az 1-es j�t�kosr�l van sz�, akkor a j1_jobb gombot n�zz�k, ha a 2-esr�l, akkor a j2_jobbot
		if (((szam==1)&&(b.j1_jobb==true) || (szam==2)&&(b.j2_jobb)))
		{
			xSeb = lepes;
			// ha jobbra mozog a j�t�kos, akkor megn�velj�k az x koordin�t�j�nak �rt�k�t
			aktKoord.x+=xSeb;
			// ha oldalra kimenn�nk a tile-b�l...
			if(aktKoord.x+meret/2>aktTile.getSzelesseg() && aktTile.getKoord().x==1)
				aktKoord.x = aktTile.getSzelesseg()-meret/2+1;
			// M�DOS�TJUK A SZ�GET
			szog = (int) Math.toDegrees(Math.atan(ugras/xSeb));
			if(szog<0) szog += 360;
			
		}
		//balra mozg�s
		//a felt�tel: HA az 1-es j�t�kosr�l van sz�, akkor a j1_bal gombot n�zz�k, ha a 2-esr�l, akkor a j2_balt
		else if(((szam==1)&&(b.j1_bal==true) || (szam==2)&&(b.j2_bal)))
		{
			xSeb = -1*lepes;
			//ha balra mozog a j�t�kos, akkor cs�kkentj�k az x koordin�t�j�nak �rt�k�t
			aktKoord.x+=xSeb;
			if(aktKoord.x-meret/2<=0 && aktTile.getKoord().x==0)
				aktKoord.x = meret/2;
			// M�DOS�TJUK A SZ�GET
			szog = (int) Math.toDegrees(Math.atan(ugras/xSeb));
			if(szog<0) szog += 360;
		} else {
			xSeb = 0;
			aktKoord.x += xSeb; //jelz�sk�nt van csak itt, nem megy�nk se jobbra se balra
			//M�DOS�TJUK A SZ�GET
			if(ugras >= 0){	//csak az Y komponens �rv�nyes a sebess�gb�l
				szog = 90;	//ha az felfel� mutat...
			} else {
				szog = 270;	//ha az lefel� mutat...
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
	//Ez a f�ggv�ny �ll�tja be az ugr�s �rt�k�t a defaultra, amennyiben egy j�t�kos f�ldet �r.
	public void setToDefaultUgras(){
		ugras=defaultUgras;
	}
	//ugras setter
	public void setUgras(int ujUgras){
		ugras=ujUgras;
	}
	
	// Ez a f�ggv�ny kezeli az ugr�st
	public void ugras(Billentyu b){
		
		//a gravit�ci� �rt�ke
		int grav = 5;
		
		if(szam == 1){	//1. J�t�kos
				if(b.j1_fel && !ugrik){
					ugrik = true;
					ugras = defaultUgras;
				}
				//az ugr�s �rt�k�t cs�kkentj�k a gravit�ci� �ltal,
				// bizonyos hat�rokig
				if(ugras>=-20)
					ugras -= grav;
				//aktu�lis f�gg�leges ir�ny� koordin�t�hoz hozz�adjuk az ugr�s �rt�k�t
				aktKoord.y += ugras;
				ugrik = true;
				if (aktTile.getKoord().y==1 && aktKoord.y+meret>aktTile.getMagassag()){
					aktKoord.y = aktTile.getMagassag()-meret-1;
					ugras = 0;
				}
				// majd az el�z� x sebess�ggel �s a m�dos�tott y sebess�ggel �jrasz�moljuk az ir�nyt
				if(xSeb==0){
					if(ugras >= 0){	//csak az Y komponens �rv�nyes a sebess�gb�l
						szog = 90;	//ha az felfel� mutat...
					} else {
						szog = 270;	//ha az lefel� mutat...
					}
				}
				else {
					szog = (int) Math.toDegrees(Math.atan(ugras/(xSeb)));
					if(szog<0) szog += 360;
				}
		}
		
		if(szam == 2){	//2. J�t�kos
				if(b.j2_fel && !ugrik){
					ugrik = true;
					ugras = defaultUgras;
				}				
				
				//az ugr�s �rt�k�t cs�kkentj�k a gravit�ci� �ltal
				ugras -= grav;
				//aktu�lis f�gg�leges ir�ny� koordin�t�hoz hozz�adjuk az ugr�s �rt�k�t
				aktKoord.y += ugras;				
				
				ugrik = true;
				// majd az el�z� x sebess�ggel �s a m�dos�tott y sebess�ggel �jrasz�moljuk az ir�nyt
				if(xSeb==0){
					if(ugras >= 0){	//csak az Y komponens �rv�nyes a sebess�gb�l
						szog = 90;	//ha az felfel� mutat...
					} else {
						szog = 270;	//ha az lefel� mutat...
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
