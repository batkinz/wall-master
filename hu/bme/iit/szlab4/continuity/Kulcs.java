package hu.bme.iit.szlab4.continuity;

import java.util.ArrayList;

public class Kulcs extends Palyaelem{
	private boolean felveheto;
	// az összes kulcsok száma
	private static int kulcsSzam=0;
	// hányadik kulcs az aktuális példány
	private final int sajatSzam;
	
	public Kulcs(){
		kulcsSzam++;
		// természetes számozás 1-tõl
		sajatSzam = kulcsSzam;
		felveheto = true;
	}
	
	// ha felvehetõ a kulcs, meghívja a játékos kucsfelvesz fv-ét
	@Override
	public boolean interakt(Jatekos j){
		if(felveheto){
			j.kulcsFelvesz();
			felveheto = false;
			j.setKezdoKoord(new Pozicio(palyaElemPoz.x,palyaElemPoz.y));
			j.setKezdoTile(j.getAktTile());
		}
		
		return false;		
	}
	
	@Override
	public void setPozicio(Pozicio poz){
		palyaElemPoz = new Pozicio(poz.x,poz.y);
	}
	
	public boolean getFelveheto(){
		return felveheto;
	}

}
