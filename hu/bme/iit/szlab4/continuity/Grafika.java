package hu.bme.iit.szlab4.continuity;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Grafika{

	private JFrame frmWallMaster;

	// Referencia a panelra, amin majd a többi panel fog elhelyezkedni
	private static JPanel panel;
	// Referencia a külsõ menû panelre
	private KulsoMenu kulsoMenu;
	// Referencia a belsõ menû panelre
	private BelsoMenu belsoMenu;
	// referencia a játék panelra
	private JatekPanel jatekPanel;
	// referencia a készítõk panelra
	private KeszitokPanel keszitokPanel;
	// referencia a Control objektumra
	private static Control control;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Grafika window = new Grafika();
					window.frmWallMaster.setVisible(true);
					panel.setFocusable(true);
					panel.addKeyListener(control);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Grafika() {
		control = new Control(this);
		initialize();
	}

	public void setdpalya(DPalya dp){
		jatekPanel.setDPalya(dp);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// segédtile hogy megtudjuk egy tile szélességét/magasságát
		Tile helpt = new Tile(50);
		int gap = 5;
		
		frmWallMaster = new JFrame();	
		frmWallMaster.setTitle("Wall Master");
		frmWallMaster.setBounds(2,15, 3*gap+2*helpt.getSzelesseg()+5, 3*gap+2*helpt.getMagassag()+50);
		frmWallMaster.setResizable(false);
		frmWallMaster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWallMaster.getContentPane().setLayout(new BorderLayout());
		
		panel = new JPanel();
		frmWallMaster.getContentPane().add(panel, BorderLayout.CENTER);
		
		kulsoMenu = new KulsoMenu(this);
		panel.setLayout(new BorderLayout());
		panel.add(kulsoMenu, BorderLayout.CENTER);
		kulsoMenu.setVisible(true);
		
		belsoMenu = new BelsoMenu(this);
		
		jatekPanel = new JatekPanel(this);
		
		keszitokPanel = new KeszitokPanel(this);
	}
		
	

	
	// átveszi a belsõ menü gombjait és lekezeli õket
	public void belsoMenu(Allapot allapot){
		if (allapot == Allapot.mentkilep){
			panel.remove(belsoMenu);
			panel.add(kulsoMenu, BorderLayout.CENTER);
			panel.revalidate();
			frmWallMaster.repaint();
			try {
				control.run(allapot);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(frmWallMaster,"Fájl írási hiba!", "Hiba", JOptionPane.ERROR_MESSAGE);
			}
		}else{
			panel.remove(belsoMenu);
			panel.add(jatekPanel, BorderLayout.CENTER);
			panel.revalidate();
			frmWallMaster.repaint();
			try {
				control.run(allapot);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(frmWallMaster,"Nem tölthetõ be a kért pálya", "Hiba", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public void setDTileok(List<DTile> dt){
		jatekPanel.getdpalya().setdtileok(dt);
	}
	
	// ha a külsõ menübõl érkezett esemény
	public void kulsoMenu(Allapot allapot){
		if (allapot == Allapot.kilep){
			try {
				control.run(allapot);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.exit(0);
		}
		else if (allapot == Allapot.keszitok){
			panel.remove(kulsoMenu);
			panel.add(keszitokPanel, BorderLayout.CENTER);
			panel.revalidate();
			frmWallMaster.repaint();
			try {
				control.run(allapot);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			panel.remove(kulsoMenu);
			panel.add(jatekPanel, BorderLayout.CENTER);
			panel.revalidate();
			frmWallMaster.repaint();
			try {
				control.run(allapot);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(frmWallMaster,"Nem tölthetõ be a kért pálya", "Hiba", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}
	
	// ha a készítõk fülbõl érkezett esemény
	public void keszitokMenu(){
		panel.remove(keszitokPanel);
		panel.add(kulsoMenu, BorderLayout.CENTER);
		panel.revalidate();
		frmWallMaster.repaint();
	}
	
	// a metódus meghívásával megjelenítjuk a belsõ menüt a játék közben
	public void drawBelsoMenu(){
		panel.remove(jatekPanel);
		panel.add(belsoMenu, BorderLayout.CENTER);
		panel.revalidate();
		frmWallMaster.repaint();
	}
	
	// A JFrame újrarajzolása
	public void ujraPaint(){
		frmWallMaster.repaint();
	}
	
	// A metódus megjeleníti egy dialógus ablakot, ha végigjátszottuk az összes pályát
	public void jatekVege(){
		JOptionPane.showMessageDialog(frmWallMaster,"Gratulálok! A játéknak vége!", "Játék vége!", JOptionPane.PLAIN_MESSAGE);
	}
	
	
	// a Credits panel elõtérben van-e
	public boolean isKeszitokPanelShowing(){
		return keszitokPanel.isShowing();
	}
	
	// A fõmenü elõtérben van-e
	public boolean isKulsoMenuShowing(){
		return kulsoMenu.isShowing();
	}
}
