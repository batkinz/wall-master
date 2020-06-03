package hu.bme.iit.szlab4.continuity;

public class Ajto extends Palyaelem{
	
	private int zarakSzama;
	
	public Ajto(int zarakSzama){
		this.zarakSzama = zarakSzama;
	}
	
	// ha a zárak száma megegyezik a játékos
	// kulcsainak számával, a játékos kimehet
	public boolean interakt(Jatekos j){
		if(j.getKulcsSzam()==zarakSzama){
			return true;
		}
		return false;		
	}
	
}
