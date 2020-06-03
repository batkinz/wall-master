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

public class BelsoMenu extends JPanel {
	// Játék folytatása gomb
	private final JButton btnFolytatas;
	// Pálya újrakezdése gomb
	private final JButton btnUjrakezd;
	// Pálya átugrása gomb
	private final JButton btnAtugras;
	// Mentés és kilépés gomb
	private final JButton btnMentKilep;
	// Referencia a főablakra
	private final Grafika graf;
	private final JLabel lblNewLabel;

	/**
	 * Create the panel.
	 */
	public BelsoMenu(Grafika mainFrame) {
		graf = mainFrame;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{295, 199, 0};
		gridBagLayout.rowHeights = new int[]{50, 31, 37, 42, 42, 42, 39, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		lblNewLabel = new JLabel("A j\u00E1t\u00E9k sz\u00FCnetelve");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.anchor = GridBagConstraints.NORTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		add(lblNewLabel, gbc_lblNewLabel);
		
		btnAtugras = new JButton("P\u00E1lya \u00E1tugrasa");
		btnAtugras.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graf.belsoMenu(Allapot.atugras);
			}
		});
		
		btnUjrakezd = new JButton("P\u00E1lya \u00FAjrakezd\u00E9se");
		btnUjrakezd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graf.belsoMenu(Allapot.ujrakezd);
			}
		});
		
		btnFolytatas = new JButton("Folytat\u00E1s");
		btnFolytatas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				graf.belsoMenu(Allapot.folytat);
			}
		});
		GridBagConstraints gbc_btnFolytatas = new GridBagConstraints();
		gbc_btnFolytatas.ipadx = 53;
		gbc_btnFolytatas.gridwidth = 2;
		gbc_btnFolytatas.fill = GridBagConstraints.VERTICAL;
		gbc_btnFolytatas.insets = new Insets(0, 0, 5, 0);
		gbc_btnFolytatas.gridx = 0;
		gbc_btnFolytatas.gridy = 3;
		add(btnFolytatas, gbc_btnFolytatas);
		GridBagConstraints gbc_btnUjrakezd = new GridBagConstraints();
		gbc_btnUjrakezd.gridwidth = 2;
		gbc_btnUjrakezd.fill = GridBagConstraints.VERTICAL;
		gbc_btnUjrakezd.insets = new Insets(0, 0, 5, 0);
		gbc_btnUjrakezd.gridx = 0;
		gbc_btnUjrakezd.gridy = 4;
		add(btnUjrakezd, gbc_btnUjrakezd);
		GridBagConstraints gbc_btnAtugras = new GridBagConstraints();
		gbc_btnAtugras.ipadx = 19;
		gbc_btnAtugras.gridwidth = 2;
		gbc_btnAtugras.fill = GridBagConstraints.VERTICAL;
		gbc_btnAtugras.insets = new Insets(0, 0, 5, 0);
		gbc_btnAtugras.gridx = 0;
		gbc_btnAtugras.gridy = 5;
		add(btnAtugras, gbc_btnAtugras);
		
		btnMentKilep = new JButton("Ment\u00E9s es kil\u00E9p\u00E9s");
		btnMentKilep.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graf.belsoMenu(Allapot.mentkilep);
			}
		});
		GridBagConstraints gbc_btnMentKilep = new GridBagConstraints();
		gbc_btnMentKilep.ipadx = 1;
		gbc_btnMentKilep.gridwidth = 2;
		gbc_btnMentKilep.fill = GridBagConstraints.VERTICAL;
		gbc_btnMentKilep.gridx = 0;
		gbc_btnMentKilep.gridy = 6;
		add(btnMentKilep, gbc_btnMentKilep);		
	}
}
