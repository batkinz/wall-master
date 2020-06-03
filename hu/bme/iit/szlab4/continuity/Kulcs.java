package hu.bme.iit.szlab4.continuity;

import java.util.ArrayList;

public class Kulcs extends Palyaelem{
	private boolean felveheto;
	// az �sszes kulcsok sz�ma
	private static int kulcsSzam=0;
	// h�nyadik kulcs az aktu�lis p�ld�ny
	private final int sajatSzam;
	
	public Kulcs(){
		kulcsSzam++;
		// term�szetes sz�moz�s 1-t�l
		sajatSzam = kulcsSzam;
		felveheto = true;
	}
	
	// ha felvehet� a kulcs, megh�vja a j�t�kos kucsfelvesz fv-�t
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
