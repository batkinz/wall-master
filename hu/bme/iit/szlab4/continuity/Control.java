package hu.bme.iit.szlab4.continuity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;

public class Control implements KeyListener, ActionListener {
	// a palya obejktum
	private static Palya palya;
	// grafika oszt�ly a kirajzol�s/friss�t�s inicializ�l�s�hoz
	private Grafika grafika;
	// TICK, 40 ms
	private Timer timer = new Timer(40,this);
	
	public Control(Grafika graf){
		grafika = graf;
		palya = new Palya();
	}
	
	// a grafika h�vja meg, a men�ben kattintott �llapottal
	public void run(Allapot allapot) throws Exception {
		DPalya tmpDPalya;
		switch(allapot){
			// mentett j�t�k ind�t�sa
			case jatek:
				tmpDPalya = palya.betoltes();
				grafika.setdpalya(tmpDPalya);
				timer.start();
				break;
			// j�t�k kezd�se el�lr�l
			case ujJatek:
				tmpDPalya = palya.ujJatek();
				grafika.setdpalya(tmpDPalya);
				timer.start();
				break;
			// visszat�r�s a bels� men�b�l a j�t�kba
			case folytat:
				timer.start();
				break;
			// aktu�lis p�lya �jrakezd�se
			case ujrakezd:
				tmpDPalya = palya.resetPalya();
				grafika.setdpalya(tmpDPalya);
				timer.start();
				break;
			// az aktu�lis p�lya �tugr�sa, a k�vetkez� ind�t�sa
			case atugras:
				tmpDPalya = palya.kovPalya();
				grafika.setdpalya(tmpDPalya);
				timer.start();
				break;
			// ment�s �s kil�p�s
			case mentkilep:
				palya.mentes();
				break;
			default:
				break;
		}
	}
	
	// A Billenty� strukt�ra
	private Billentyu bill = new Billentyu();
	// ugr�s eml�kez� bit
	private boolean j1ugrik=false;
	private boolean j2ugrik=false;

	@Override
	public void keyPressed(KeyEvent arg0) {
		// a lenyomott karakter
		char tmpKar = arg0.getKeyChar();
		int tmpCode = arg0.getKeyCode();
		
		// kil�p�s
		if(tmpCode==KeyEvent.VK_ESCAPE && !grafika.isKeszitokPanelShowing()
				&& !grafika.isKulsoMenuShowing()){
			timer.stop();
			grafika.drawBelsoMenu();
			return;
		}
		
		// 1. j�t�kos ir�ny�t�sa (nyilak)
		
		// ugr�s csak egyszer
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
		
		// 2. j�t�kos ir�ny�t�sa (WASD)
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
	
		// 1. j�t�kos ir�ny�t�sa (nyilak)
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
		
		// 2. j�t�kos ir�ny�t�sa (WASD)
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
		// 1. ugr�s ut�n false-ra �ll�tjuk az �rt�k�t
		bill.j1_fel=false;
		bill.j2_fel=false;
	}
}
