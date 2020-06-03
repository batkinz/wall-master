package hu.bme.iit.szlab4.continuity;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

public class JatekPanel extends JPanel {

	//JLabel j1Allapot;
	//JLabel j2Allapot;
	
	private final Grafika sajatFrame;
	
	// Kirajzolandó pálya
	private DPalya dpalya = new DPalya();
	
	/**
	 * Create the panel.
	 */
	public JatekPanel(Grafika gr) {
		sajatFrame = gr;
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		//j1Allapot = new JLabel("New label");
		//panel.add(j1Allapot);
		
		//j2Allapot= new JLabel("New label");
		//panel.add(j2Allapot);

	}

	public void setDPalya(DPalya ujdpalya){
		dpalya = ujdpalya;
	}
	
	
	@Override
	public void paint(Graphics g){
		
		//	j1Allapot.setVisible(true);
		
		List<DJatekos> djatekosok = dpalya.getdjatekosok();
		List<DTile> dtileok = dpalya.getdtileok();
		
		/*	KIRAJZOLÁS-TESZTHEZ JÓL JÖN
		
		//Teszteléshez segítség
		Tile tile1 = new Tile(11);
		Tile tile2 = new Tile(12);
		Tile tile3 = new Tile(13);
		
		Kulcs k = new Kulcs();
		k.setPozicio(new Pozicio(60,60));
		k.setMeret_h(10);
		k.setMeret_v(8);
		tile1.init(k);
		
		Ajto a = new Ajto(5);
		a.setPozicio(new Pozicio(20,200));
		a.setMeret_h(4);
		a.setMeret_v(8);
		tile1.init(a);
		
		Fal f = new Fal();
		f.setPozicio(new Pozicio(250,50));
		f.setMeret_h(500);
		f.setMeret_v(20);
		tile1.init(f);
		
		tile1.setKezdo(true);
		tile1.setKoord(new Pozicio(0,1));
		tile2.setKoord(new Pozicio(1,1));
		tile3.setKoord(new Pozicio(0,0));
		
		DTile dtile1 = new DTile(tile1);
		DTile dtile2 = new DTile(tile2);
		DTile dtile3 = new DTile(tile3);
		
		dtileok.add(dtile1);
		dtileok.add(dtile2);
		dtileok.add(dtile3);
		
		Jatekos j1 = new Jatekos(1);
		Jatekos j2 = new Jatekos(2);
		j2.setReszletes(false);
		j1.setReszletes(true);
		
		DJatekos dj1 = new DJatekos(j1);
		DJatekos dj2 = new DJatekos(j2);
		
		j1.setAktKoord(new Pozicio(100,100));
		j2.setAktKoord(new Pozicio(200,100));
		j1.setAktTile(tile1);
		j2.setAktTile(tile1);
		
		djatekosok.add(dj1);
		djatekosok.add(dj2);
		
		//dpalya.setdjatekosok(djatekosok);
		//dpalya.setdtileok(dtileok);
		
		// EDDIG
		*/
		Tile temp = new Tile(10);
		
		// Egy tile szélessége
		int tileszeles = temp.getSzelesseg();
		// Egy tile magassága
		int tilemagas = temp.getMagassag();
		// Tile-ok közötti rés
		int gap = 5;
		
		// Pálya kirajzolása
		// A pálya háttere: SÁRGA
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 2*tileszeles + 3*gap, 2* tilemagas + 3*gap);
		
		// Végigmegyünk a tile-okon
		for (int i = 0; i < dtileok.size() ; i++){
			// Kirajzoljuk őket sorban
			dtileok.get(i).draw(g,gap);
		}
		
		// Végigmegyünk a játékosokon
		for (int i = 0; i < djatekosok.size() ; i++){
			// Kirajzoljuk őket sorban
			djatekosok.get(i).draw(g, gap);
		}
		
		// Kilógó pályaelemekrészek levágása
		g.setColor(Color.yellow);
		g.fillRect(0,0,2*tileszeles+3*gap,gap);
		g.fillRect(0,0,gap,2*tilemagas+3*gap);
		g.fillRect(0,2*tilemagas+2*gap,2*tileszeles+3*gap,gap);
		g.fillRect(0,tilemagas+gap,2*tileszeles+3*gap,gap);
		g.fillRect(tileszeles+gap,0,gap,2*tilemagas+3*gap);
		g.fillRect(tileszeles*2+2*gap,0,gap, 2*tilemagas+3*gap);
		revalidate();
		sajatFrame.ujraPaint();
	}

	// dpalya getter
	public DPalya getdpalya() {
		return dpalya;
	}
	
}
