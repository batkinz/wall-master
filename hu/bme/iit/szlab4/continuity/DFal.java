package hu.bme.iit.szlab4.continuity;

import java.awt.Color;
import java.awt.Graphics;

public class DFal extends DPalyaelem{
	// A kirajzolandó fal
	private Fal elem;
	
	@Override
	// Kirajzolás
	public void draw(Graphics g, Pozicio balfelso) {
		// A kirajzolás helyének kiszámolásához kell
		Tile t = new Tile(100);
		
		// A kirajzolandó fal bal felsõ sarka
		Pozicio poz = new Pozicio(balfelso.x+elem.getKoord().x-elem.getMeretH()/2, balfelso.y+(t.getMagassag()-elem.getKoord().y)-elem.getMeretV()/2);
		
		g.setColor(Color.black);
		g.fillRect(poz.x, poz.y, elem.getMeretH()+1, elem.getMeretV()+1);
	}
	
	public DFal(Fal f){
		elem = f;
	}
}
