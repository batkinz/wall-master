package hu.bme.iit.szlab4.continuity;

import java.awt.Color;
import java.awt.Graphics;

public class DJatekos {

	// Kirajzoland� j�t�kos
	private Jatekos jatekos;
	
	public DJatekos(Jatekos j){
		jatekos=j;
	}
	
	// J�t�kos kirajzol�sa
	public void draw(Graphics g, int koz) {
		// A j�t�kos aktu�lis tile-a
		Tile tile = jatekos.getAktTile();
		// A kirajzoland� tile LOGIKAI poz�ci�ja
		Pozicio tilepoz = tile.getKoord();
		// y koordin�t�k invert�l�sa
		tilepoz.y = 1-tilepoz.y;
		// Tile sz�less�ge
		int tileszeles = tile.getSzelesseg();
		// Tile magass�ga
		int tilemagas = tile.getMagassag();
		// Tile-ok k�z�tti r�s
		int gap = koz;
		// Kirajzoland� j�t�kos Tile-j�nak bal fels� pontj�nak GRAFIKUS koordin�t�i
		Pozicio jatekospoz = new Pozicio(gap+tilepoz.x*(tileszeles+gap),gap+tilepoz.y*(tilemagas+gap));
		// A kirajzoland� j�t�kos koordin�t�i
		jatekospoz.x+=jatekos.getAktKoord().x;
		jatekospoz.y+=jatekos.getAktTile().getMagassag();
		jatekospoz.y-=jatekos.getAktKoord().y;
		// A kirajzoland� j�t�kos bal fels� sarka
		jatekospoz.x-=jatekos.getMeret()/2;
		jatekospoz.y-=jatekos.getMeret();
		
		String nezet = "�ttekint� n�zet";
		if (jatekos.getReszletes()) {
			nezet = "R�szletes n�zet";
		}
		
		// 1-es j�t�kos
		if (jatekos.getSzam()==1){
			// K�K
			g.setColor(Color.blue);
			g.fillRect(jatekospoz.x,jatekospoz.y,jatekos.getMeret()+1,jatekos.getMeret()*2+1);
			g.setColor(Color.black);
			g.drawString("J�t�kos 1: " + nezet, gap, 4*gap+tilemagas*2+10);
		}else{
		// 2-es j�t�kos
			// LILA
			g.setColor(Color.magenta);
			g.fillRect(jatekospoz.x,jatekospoz.y,jatekos.getMeret()+1,jatekos.getMeret()*2+1);	
			g.setColor(Color.black);
			g.drawString("J�t�kos 2: " + nezet, 2*gap+1*tileszeles, 4*gap+tilemagas*2+10);
		}
	}
	
}
