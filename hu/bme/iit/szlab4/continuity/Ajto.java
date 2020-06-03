package hu.bme.iit.szlab4.continuity;

public class Ajto extends Palyaelem{
	
	private int zarakSzama;
	
	public Ajto(int zarakSzama){
		this.zarakSzama = zarakSzama;
	}
	
	// ha a z�rak sz�ma megegyezik a j�t�kos
	// kulcsainak sz�m�val, a j�t�kos kimehet
	public boolean interakt(Jatekos j){
		if(j.getKulcsSzam()==zarakSzama){
			return true;
		}
		return false;		
	}
	
}
