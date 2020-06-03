package hu.bme.iit.szlab4.continuity;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class KulsoMenu extends JPanel {
	
	// Játék gomb
	private final JButton btnJatek;
	// Új játék gomb
	private final JButton btnUjJatek;
	// Keszitok gomb
	private final JButton btnKeszitok;
	// Kilépés gomb
	private final JButton btnKilep;
	// Referencia a fõablakra
	private final Grafika graf;
	private final JLabel label;
	
	/**
	 * Create the panel.
	 */
	public KulsoMenu(Grafika mainFrame) {
		graf = mainFrame;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{300, 190, 0};
		gridBagLayout.rowHeights = new int[]{52, 31, 35, 42, 42, 42, 36, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		label = new JLabel("Wall Master j\u00E1t\u00E9k");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.gridwidth = 2;
		gbc_label.anchor = GridBagConstraints.NORTH;
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridx = 0;
		gbc_label.gridy = 1;
		add(label, gbc_label);
		label.setFont(new Font("Tahoma", Font.PLAIN, 25));
		
		btnJatek = new JButton("J\u00E1t\u00E9k");
		GridBagConstraints gbc_btnJatek = new GridBagConstraints();
		gbc_btnJatek.ipadx = 30;
		gbc_btnJatek.gridwidth = 2;
		gbc_btnJatek.fill = GridBagConstraints.VERTICAL;
		gbc_btnJatek.insets = new Insets(0, 0, 5, 0);
		gbc_btnJatek.gridx = 0;
		gbc_btnJatek.gridy = 3;
		add(btnJatek, gbc_btnJatek);
		btnJatek.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				graf.kulsoMenu(Allapot.jatek);
			}
		});
		
		btnUjJatek = new JButton("\u00DAj j\u00E1t\u00E9k");
		GridBagConstraints gbc_btnUjJatek = new GridBagConstraints();
		gbc_btnUjJatek.ipadx = 20;
		gbc_btnUjJatek.gridwidth = 2;
		gbc_btnUjJatek.fill = GridBagConstraints.VERTICAL;
		gbc_btnUjJatek.insets = new Insets(0, 0, 5, 0);
		gbc_btnUjJatek.gridx = 0;
		gbc_btnUjJatek.gridy = 4;
		add(btnUjJatek, gbc_btnUjJatek);
		btnUjJatek.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graf.kulsoMenu(Allapot.ujJatek);
			}
		});
		
		btnKeszitok = new JButton("K\u00E9szit\u0151k");
		GridBagConstraints gbc_btnKeszitok = new GridBagConstraints();
		gbc_btnKeszitok.fill = GridBagConstraints.VERTICAL;
		gbc_btnKeszitok.ipadx = 13;
		gbc_btnKeszitok.gridwidth = 2;
		gbc_btnKeszitok.insets = new Insets(0, 0, 5, 0);
		gbc_btnKeszitok.gridx = 0;
		gbc_btnKeszitok.gridy = 5;
		add(btnKeszitok, gbc_btnKeszitok);
		btnKeszitok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graf.kulsoMenu(Allapot.keszitok);
			}
		});
		
		btnKilep = new JButton("Kil\u00E9p");
		GridBagConstraints gbc_btnKilep = new GridBagConstraints();
		gbc_btnKilep.ipadx = 34;
		gbc_btnKilep.gridwidth = 2;
		gbc_btnKilep.fill = GridBagConstraints.VERTICAL;
		gbc_btnKilep.gridx = 0;
		gbc_btnKilep.gridy = 6;
		add(btnKilep, gbc_btnKilep);
		btnKilep.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graf.kulsoMenu(Allapot.kilep);
			}
		});		
	}
}
