package hu.bme.iit.szlab4.continuity;

import java.awt.Color;
import java.awt.Graphics;

public class DKulcs extends DPalyaelem{
	// A kirajzoland� kulcs
	private Kulcs elem;
	
	@Override
	// Kirajzol�s
	public void draw(Graphics g, Pozicio balfelso) {
		// A kirajzol�s hely�nek kisz�mol�s�hoz kell
		Tile t = new Tile(100);
		
		// A kirajzoland� kulcs bal fels� sarka
		Pozicio poz = new Pozicio(balfelso.x+elem.getKoord().x-elem.getMeretH()/2, balfelso.y+(t.getMagassag()-elem.getKoord().y)-elem.getMeretV()/2);
		// Ha felvehet� a kulcs
		if (elem.getFelveheto()){
			// Akkor z�ld
			g.setColor(Color.green);
		}
		// Ha nem vehet� fel
		else {
			// Sz�rke
			g.setColor(Color.gray);
		}
		g.fillRect(poz.x, poz.y, elem.getMeretH()+1, elem.getMeretV()+1);
	}
	
	public DKulcs(Kulcs k){
		elem = k;
	}

}
