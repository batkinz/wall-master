package hu.bme.iit.szlab4.continuity;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class DTile {
	// kirajzoland� p�lyaelemeket tartalmaz� lista
	private List<DPalyaelem> dpalyaelemek = new ArrayList<DPalyaelem>();
	private Tile tile;
	
	public DTile(Tile t){
		tile = t;
		DPalyaelem tempDP = null;
		for (int i = 0; i < t.getListaHossz() ; i++){
			if (t.getPalyaelem(i).getClass().equals(Fal.class)){
				// Ha a vizsg�lt elem FAL
				tempDP = new DFal((Fal)t.getPalyaelem(i));				
			} else if (t.getPalyaelem(i).getClass().equals(Ajto.class)){
				// Ha a vizsg�lt elem AJT�
				tempDP = new DAjto((Ajto)t.getPalyaelem(i));	
			} else if (t.getPalyaelem(i).getClass().equals(Kulcs.class)){
				// Ha a vizsg�lt elem KULCS
				tempDP = new DKulcs((Kulcs)t.getPalyaelem(i));
			}
		
			dpalyaelemek.add(tempDP);
		}
	}
	
	
	// kirajzol�s
	public void draw(Graphics g, int koz){
		
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
		// Kirajzoland� tile bal fels� pontj�nak GRAFIKUS koordin�t�i
		Pozicio balfelso = new Pozicio(gap+tilepoz.x*(tileszeles+gap),gap+tilepoz.y*(tilemagas+gap));

		// Tile kirajzol�sa
		if (!tile.getUres()){
			g.setColor(Color.white);
			g.fillRect(balfelso.x, balfelso.y, tileszeles, tilemagas);
		}
		
		// v�gigmegy�nk a p�lyaelemeken
		for (int i = 0 ; i < dpalyaelemek.size() ; i++){
			// kirajzoltatjuk �ket sorban
			dpalyaelemek.get(i).draw(g, balfelso);
		}
		
	}
}
