package hu.bme.iit.szlab4.continuity;

import java.awt.Color;
import java.awt.Graphics;

public class DAjto extends DPalyaelem{
	// A kirajzoland� ajt�
	private Ajto elem;
	
	@Override
	// Kirajzol�s
	public void draw(Graphics g, Pozicio balfelso){
		// A kirajzol�s hely�nek kisz�mol�s�hoz kell
		Tile t = new Tile(100);
		
		// A kirajzoland� ajt� bal fels� sarka
		Pozicio poz = new Pozicio(balfelso.x+elem.getKoord().x-elem.getMeretH()/2, balfelso.y+(t.getMagassag()-elem.getKoord().y)-elem.getMeretV()/2);
		
		g.setColor(Color.red);
		g.fillRect(poz.x, poz.y, elem.getMeretH()+1, elem.getMeretV()+1);
		g.setColor(Color.white);
		int kilincsX = balfelso.x+elem.getKoord().x;
		int kilincsY = balfelso.y+(t.getMagassag()-elem.getKoord().y)-elem.getMeretV()/12;
		g.fillOval(kilincsX,kilincsY,8,8);
	}
	
	public DAjto(Ajto a){
		elem = a;
	}
}
