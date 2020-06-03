package hu.bme.iit.szlab4.continuity;

import java.awt.Color;
import java.awt.Graphics;

public class DKulcs extends DPalyaelem{
	// A kirajzolandó kulcs
	private Kulcs elem;
	
	@Override
	// Kirajzolás
	public void draw(Graphics g, Pozicio balfelso) {
		// A kirajzolás helyének kiszámolásához kell
		Tile t = new Tile(100);
		
		// A kirajzolandó kulcs bal felsõ sarka
		Pozicio poz = new Pozicio(balfelso.x+elem.getKoord().x-elem.getMeretH()/2, balfelso.y+(t.getMagassag()-elem.getKoord().y)-elem.getMeretV()/2);
		// Ha felvehetõ a kulcs
		if (elem.getFelveheto()){
			// Akkor zöld
			g.setColor(Color.green);
		}
		// Ha nem vehetõ fel
		else {
			// Szürke
			g.setColor(Color.gray);
		}
		g.fillRect(poz.x, poz.y, elem.getMeretH()+1, elem.getMeretV()+1);
	}
	
	public DKulcs(Kulcs k){
		elem = k;
	}

}
