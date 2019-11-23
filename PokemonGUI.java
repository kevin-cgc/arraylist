package arraylist;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


/**
 * PokemonGUI
 */
public class PokemonGUI {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Pokemon Backpack");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		MainPokemonPanel panel = new MainPokemonPanel();
		frame.getContentPane().add(panel);

		frame.pack();
		frame.setVisible(true);
	}

	private static class PokemonListPanel extends JPanel {
		JLabel noPokemonLabel = new JLabel("no pokemon in backpack");

		public PokemonListPanel() {
			add (noPokemonLabel);
			setBackground(Color.LIGHT_GRAY);
			setPreferredSize(new Dimension(1000, 9000));
		}

		public void updateWithBackpack(PokemonBackpack pb) {
			removeAll();
			
			if (pb.getBackpackSize() == 0) {
				add(noPokemonLabel);
			} else {
				ArrayList<Pokemon> bAL = pb.getBackpackArrayList();
				for (int i = 0; i < bAL.size(); i++) {
					Pokemon p = bAL.get(i);
					JLabel pJLabel = new JLabel(""+p.getName()+", "+p.getType());
					
					pJLabel.setIcon(p.getImageIcon());

					pJLabel.setHorizontalTextPosition(JLabel.CENTER);
					pJLabel.setVerticalTextPosition(JLabel.BOTTOM);
										
					pJLabel.setBackground(Color.WHITE);
					pJLabel.setMaximumSize(new Dimension(50, 50));
					add(pJLabel);
				}
			}

			revalidate();
			repaint();
		}
	}

	private static class MainPokemonPanel extends JPanel {
		private JLabel nameLabel = new JLabel("Pokemon Name");
		private JTextField nameText = new JTextField(10); // enter Pokemon name
		private JLabel typeLabel = new JLabel("Pokemon Type");
		private JTextField typeText = new JTextField(10); // enter Pokemon type

		private JButton appendButton = new JButton("add pokemon to end"); // buttons
		private JButton removeButton = new JButton("remove pokemon from end");

		PokemonListPanel pokemonListPanel = new PokemonListPanel();

		PokemonBackpack pb = new PokemonBackpack();

		// Constructor: Sets up the Panel
		public MainPokemonPanel() {
			
			add(nameLabel);
			add(nameText);
			add(typeLabel);
			add(typeText);
			add(appendButton);
			add(removeButton);
			add(pokemonListPanel);

			// Default values

			// connect event handler to event source
			appendButton.addActionListener(new ButtonListener());
			removeButton.addActionListener(new ButtonListener());

			// configure panel.
			nameLabel.setForeground(Color.WHITE);
			setBackground(Color.DARK_GRAY);
			setPreferredSize(new Dimension(1250, 600));

		}

		// private internal class that is a listener for button push (action) events.
		// also called an event handler
		private class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {

				if (event.getSource() == appendButton) {
					String pokemonName = nameText.getText();
					String pokemonType = typeText.getText();

					Pokemon p = new Pokemon(pokemonName, pokemonType);

					pb.appendPokemonToBackpack(p);
				} else if (event.getSource() == removeButton) {
					pb.removeLastPokemonFromBackpack();
				}

				pokemonListPanel.updateWithBackpack(pb);

			}// end of ActionPerformed method

		}// end of ButtonListener

	}

}