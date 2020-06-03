package hu.bme.iit.szlab4.continuity;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class KeszitokPanel extends JPanel {
	
	// Referencia a fõablakra
	private final Grafika graf;

	/**
	 * Create the panel.
	 */
	public KeszitokPanel(Grafika grafika) {
		graf = grafika;
		setLayout(null);
		
		JLabel lblWallMasterJtk = new JLabel("Wall Master j\u00E1t\u00E9k k\u00E9sz\u00EDt\u0151i:");
		lblWallMasterJtk.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblWallMasterJtk.setBounds(251, 71, 288, 34);
		add(lblWallMasterJtk);
		
		JLabel lblNewLabel = new JLabel("Major Bence");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(337, 116, 115, 34);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Makkos Bence");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(332, 161, 126, 27);
		add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("T\u00FCk\u00F6r Ferenc");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(336, 199, 118, 27);
		add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("T\u0171zk\u0151 Andr\u00E1s");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_3.setBounds(334, 237, 121, 27);
		add(lblNewLabel_3);
		
		JLabel lblWinchesterUnited = new JLabel("Winchester United 2012");
		lblWinchesterUnited.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblWinchesterUnited.setBounds(310, 324, 169, 25);
		add(lblWinchesterUnited);
		
		JButton btnNewButton = new JButton("F\u0151men\u00FC");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graf.keszitokMenu();
			}
		});
		btnNewButton.setBounds(10, 508, 101, 34);
		add(btnNewButton);

	}
}
