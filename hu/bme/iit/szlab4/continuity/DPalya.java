package hu.bme.iit.szlab4.continuity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DPalya {
	private List<DTile> dtileok = new ArrayList<DTile>();
	private List<DJatekos> djatekosok = new ArrayList<DJatekos>();
	
	// dtileok setter
	public void setdtileok(List<DTile> ujdtileok){
		dtileok = ujdtileok;
	}
	
	// dtileok getter
	public List<DTile> getdtileok(){
		return dtileok;
	}
	
	// djatekosok getter
	public List<DJatekos> getdjatekosok() {
		return djatekosok;
	}

	// djatekosok setter
	public void setdjatekosok(List<DJatekos> djatekosok) {
		this.djatekosok = djatekosok;
	}
		
}
