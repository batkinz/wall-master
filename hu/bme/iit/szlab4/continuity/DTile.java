package hu.bme.iit.szlab4.continuity;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class DTile {
	// kirajzolandó pályaelemeket tartalmazó lista
	private List<DPalyaelem> dpalyaelemek = new ArrayList<DPalyaelem>();
	private Tile tile;
	
	public DTile(Tile t){
		tile = t;
		DPalyaelem tempDP = null;
		for (int i = 0; i < t.getListaHossz() ; i++){
			if (t.getPalyaelem(i).getClass().equals(Fal.class)){
				// Ha a vizsgált elem FAL
				tempDP = new DFal((Fal)t.getPalyaelem(i));				
			} else if (t.getPalyaelem(i).getClass().equals(Ajto.class)){
				// Ha a vizsgált elem AJTÓ
				tempDP = new DAjto((Ajto)t.getPalyaelem(i));	
			} else if (t.getPalyaelem(i).getClass().equals(Kulcs.class)){
				// Ha a vizsgált elem KULCS
				tempDP = new DKulcs((Kulcs)t.getPalyaelem(i));
			}
		
			dpalyaelemek.add(tempDP);
		}
	}
	
	
	// kirajzolás
	public void draw(Graphics g, int koz){
		
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
		// Kirajzolandó tile bal felső pontjának GRAFIKUS koordinátái
		Pozicio balfelso = new Pozicio(gap+tilepoz.x*(tileszeles+gap),gap+tilepoz.y*(tilemagas+gap));

		// Tile kirajzolása
		if (!tile.getUres()){
			g.setColor(Color.white);
			g.fillRect(balfelso.x, balfelso.y, tileszeles, tilemagas);
		}
		
		// végigmegyünk a pályaelemeken
		for (int i = 0 ; i < dpalyaelemek.size() ; i++){
			// kirajzoltatjuk őket sorban
			dpalyaelemek.get(i).draw(g, balfelso);
		}
		
	}
}
