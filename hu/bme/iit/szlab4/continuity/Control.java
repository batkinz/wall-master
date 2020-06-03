package hu.bme.iit.szlab4.continuity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;

public class Control implements KeyListener, ActionListener {
	// a palya obejktum
	private static Palya palya;
	// grafika osztály a kirajzolás/frissítés inicializálásához
	private Grafika grafika;
	// TICK, 40 ms
	private Timer timer = new Timer(40,this);
	
	public Control(Grafika graf){
		grafika = graf;
		palya = new Palya();
	}
	
	// a grafika hívja meg, a menüben kattintott állapottal
	public void run(Allapot allapot) throws Exception {
		DPalya tmpDPalya;
		switch(allapot){
			// mentett játék indítása
			case jatek:
				tmpDPalya = palya.betoltes();
				grafika.setdpalya(tmpDPalya);
				timer.start();
				break;
			// játék kezdése elölről
			case ujJatek:
				tmpDPalya = palya.ujJatek();
				grafika.setdpalya(tmpDPalya);
				timer.start();
				break;
			// visszatérés a belső menüből a játékba
			case folytat:
				timer.start();
				break;
			// aktuális pálya újrakezdése
			case ujrakezd:
				tmpDPalya = palya.resetPalya();
				grafika.setdpalya(tmpDPalya);
				timer.start();
				break;
			// az aktuális pálya átugrása, a következő indítása
			case atugras:
				tmpDPalya = palya.kovPalya();
				grafika.setdpalya(tmpDPalya);
				timer.start();
				break;
			// mentés és kilépés
			case mentkilep:
				palya.mentes();
				break;
			default:
				break;
		}
	}
	
	// A Billentyű struktúra
	private Billentyu bill = new Billentyu();
	// ugrás emlékező bit
	private boolean j1ugrik=false;
	private boolean j2ugrik=false;

	@Override
	public void keyPressed(KeyEvent arg0) {
		// a lenyomott karakter
		char tmpKar = arg0.getKeyChar();
		int tmpCode = arg0.getKeyCode();
		
		// kilépés
		if(tmpCode==KeyEvent.VK_ESCAPE && !grafika.isKeszitokPanelShowing()
				&& !grafika.isKulsoMenuShowing()){
			timer.stop();
			grafika.drawBelsoMenu();
			return;
		}
		
		// 1. játékos irányítása (nyilak)
		
		// ugrás csak egyszer
		if(tmpCode==KeyEvent.VK_UP && !j1ugrik){
			j1ugrik=true;
			bill.j1_fel=true;
		}
		if(tmpCode==KeyEvent.VK_LEFT){
			bill.j1_bal=true;
		}
		if(tmpCode==KeyEvent.VK_DOWN){
			bill.j1_le=true;
		}
		if(tmpCode==KeyEvent.VK_RIGHT){
			bill.j1_jobb=true;
		}
		if(tmpCode==KeyEvent.VK_SPACE){
			bill.j1_nezet=true;
		}
		
		// 2. játékos irányítása (WASD)
		switch(tmpKar){
			case 'w':
			case 'W':
				if(!j2ugrik){
					j2ugrik=true;
					bill.j2_fel=true;
				}
				break;
			case 'a':
			case 'A':
				bill.j2_bal=true;
				break;
			case 's':
			case 'S':
				bill.j2_le=true;
				break;
			case 'd':
			case 'D':
				bill.j2_jobb=true;
				break;
			case 'q':
			case 'Q':
				bill.j2_nezet=true;
				break;
			default:
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// a lenyomott karakter
		char tmpKar = arg0.getKeyChar();
		int tmpCode = arg0.getKeyCode();
	
		// 1. játékos irányítása (nyilak)
		if(tmpCode==KeyEvent.VK_UP){
			bill.j1_fel=false;
			j1ugrik=false;
		}
		if(tmpCode==KeyEvent.VK_LEFT){
			bill.j1_bal=false;
		}
		if(tmpCode==KeyEvent.VK_DOWN){
			bill.j1_le=false;
		}
		if(tmpCode==KeyEvent.VK_RIGHT){
			bill.j1_jobb=false;
		}
		
		// 2. játékos irányítása (WASD)
		switch(tmpKar){
			case 'w':
			case 'W':
				bill.j2_fel=false;
				j2ugrik=false;
				break;
			case 'a':
			case 'A':
				bill.j2_bal=false;
				break;
			case 's':
			case 'S':
				bill.j2_le=false;
				break;
			case 'd':
			case 'D':
				bill.j2_jobb=false;
				break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(palya.getVege()){
			DPalya tmpDPalya;
			try {
				if (palya.getPalyakSzama()==palya.getAktPalyaSzam()){
					grafika.jatekVege();
				}
				tmpDPalya=palya.kovPalya();
				grafika.setdpalya(tmpDPalya);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			bill = new Billentyu();
			return;
		}
		
		palya.mozgat(bill);
		palya.utkozesVizsgalat();
		bill.j1_nezet=false;
		bill.j2_nezet=false;
		// 1. ugrás után false-ra állítjuk az értékét
		bill.j1_fel=false;
		bill.j2_fel=false;
	}
}
