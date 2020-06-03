package hu.bme.iit.szlab4.continuity;

import java.awt.Color;
import java.awt.Graphics;

public class DJatekos {

	// Kirajzolandó játékos
	private Jatekos jatekos;
	
	public DJatekos(Jatekos j){
		jatekos=j;
	}
	
	// Játékos kirajzolása
	public void draw(Graphics g, int koz) {
		// A játékos aktuális tile-a
		Tile tile = jatekos.getAktTile();
		// A kirajzolandó tile LOGIKAI pozíciója
		Pozicio tilepoz = tile.getKoord();
		// y koordináták invertálása
		tilepoz.y = 1-tilepoz.y;
		// Tile szélessége
		int tileszeles = tile.getSzelesseg();
		// Tile magassága
		int tilemagas = tile.getMagassag();
		// Tile-ok közötti rés
		int gap = koz;
		// Kirajzolandó játékos Tile-jának bal felső pontjának GRAFIKUS koordinátái
		Pozicio jatekospoz = new Pozicio(gap+tilepoz.x*(tileszeles+gap),gap+tilepoz.y*(tilemagas+gap));
		// A kirajzolandó játékos koordinátái
		jatekospoz.x+=jatekos.getAktKoord().x;
		jatekospoz.y+=jatekos.getAktTile().getMagassag();
		jatekospoz.y-=jatekos.getAktKoord().y;
		// A kirajzolandó játékos bal felső sarka
		jatekospoz.x-=jatekos.getMeret()/2;
		jatekospoz.y-=jatekos.getMeret();
		
		String nezet = "Áttekintő nézet";
		if (jatekos.getReszletes()) {
			nezet = "Részletes nézet";
		}
		
		// 1-es játékos
		if (jatekos.getSzam()==1){
			// KÉK
			g.setColor(Color.blue);
			g.fillRect(jatekospoz.x,jatekospoz.y,jatekos.getMeret()+1,jatekos.getMeret()*2+1);
			g.setColor(Color.black);
			g.drawString("Játékos 1: " + nezet, gap, 4*gap+tilemagas*2+10);
		}else{
		// 2-es játékos
			// LILA
			g.setColor(Color.magenta);
			g.fillRect(jatekospoz.x,jatekospoz.y,jatekos.getMeret()+1,jatekos.getMeret()*2+1);	
			g.setColor(Color.black);
			g.drawString("Játékos 2: " + nezet, 2*gap+1*tileszeles, 4*gap+tilemagas*2+10);
		}
	}
	
}
