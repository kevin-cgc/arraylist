package arraylist;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutionException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

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

		Map<Pokemon, JLabel> pokemonJLabelWeakMap = new WeakHashMap<Pokemon, JLabel>();

		public PokemonListPanel() {
			add(noPokemonLabel);
			setBackground(Color.decode("#97c7e6"));
			setPreferredSize(new Dimension(900, 2500));
		}

		class PokemonImageLoader extends SwingWorker<ImageIcon, Object> {
			Pokemon pokemon;
			JLabel pJLabel;

			public PokemonImageLoader(Pokemon p, JLabel pJLabel) {
				this.pokemon = p;
				this.pJLabel = pJLabel;
			}

			@Override
			public ImageIcon doInBackground() throws InterruptedException, ExecutionException {
				return this.pokemon.getImage();
			}

			@Override
			protected void done() {
				try {
					pJLabel.setIcon(get());
				} catch (Exception ignore) {
				}
			}
		}

		public void updateWithBackpack(PokemonBackpack pb) {
			removeAll();

			if (pb.getBackpackSize() == 0) {
				add(noPokemonLabel);
			} else {
				ArrayList<Pokemon> bAL = pb.getBackpackArrayList();
				for (int i = 0; i < bAL.size(); i++) {
					Pokemon p = bAL.get(i);

					if (!pokemonJLabelWeakMap.containsKey(p)) pokemonJLabelWeakMap.put(p, new JLabel());
					JLabel pJLabel = pokemonJLabelWeakMap.get(p);

					pJLabel.setText(""+p.getName()+", "+p.getType());
					pJLabel.setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6));

					pJLabel.setIcon(p.getImageIcon());

					if (!p.imageLoaded() && !p.getMakingRequest()) {
						(new PokemonImageLoader(p, pJLabel)).execute();
					}

					pJLabel.setHorizontalTextPosition(JLabel.CENTER);
					pJLabel.setVerticalTextPosition(JLabel.BOTTOM);

					pJLabel.setBackground(Color.WHITE);
					add(pJLabel);
				}
			}

			revalidate();
			repaint();
		}
	}

	private static class MainPokemonPanel extends JPanel {
		private JLabel nameLabel = new JLabel("Pokemon Name");
		private JTextField nameText = new JTextField(15); // enter Pokemon name
		private JLabel typeLabel = new JLabel("Pokemon Type");
		private JTextField typeText = new JTextField(15); // enter Pokemon type
		private JLabel indexName = new JLabel("Index: ");
		private JTextField indexText = new JTextField(3);
		private JLabel indexLabel = new JLabel();

		private JButton appendButton = new JButton("add pokemon to end"); // buttons
		private JButton insertNameSortedButton = new JButton("insert pokemon sorted by name");
		private JButton insertTypeSortedButton = new JButton("insert pokemon sorted by type");
		private JButton removeButton = new JButton("remove pokemon from end");
		private JButton emptyBackpack = new JButton("remove all pokemon");
		private JButton backpackSizeButton = new JButton("get backpack size");
		private JButton getNameListButton = new JButton("get name list");
		private JButton sortNameButton = new JButton("sort pokemon by name");
		private JButton sortTypeButton = new JButton("sort pokemon by type");
		private JButton addRandomButton = new JButton("add random pokemon");
		private JButton indexButton = new JButton("get pokemon at index");
		private JButton removeNameButton = new JButton("remove all with name");
		private JButton removeTypeButton = new JButton("remove all with type");
		private JButton removeNameTypeButton = new JButton("remove all with name & type");

		private JTextArea consoleText = new JTextArea(30, 18);

		PokemonListPanel pokemonListPanel = new PokemonListPanel();

		PokemonBackpack pb = new PokemonBackpack();

		// Constructor: Sets up the Panel
		public MainPokemonPanel() {

			for (JLabel textField : (new JLabel[]{nameLabel, typeLabel, indexName, indexLabel})) {
				textField.setForeground(Color.WHITE);
			}

			for (JTextField textField : (new JTextField[]{nameText, typeText, indexText})) {
				textField.setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));
				textField.setForeground(Color.BLACK);
  				textField.setBackground(Color.WHITE);
			}

			ButtonListener bl = new ButtonListener();
			for (JButton button : (new JButton[]{
				appendButton,
				insertNameSortedButton,
				insertTypeSortedButton,
				removeButton,
				emptyBackpack,
				backpackSizeButton,
				getNameListButton,
				sortNameButton,
				sortTypeButton,
				addRandomButton,
				indexButton,
				removeNameButton,
				removeTypeButton,
				removeNameTypeButton
			})) {
				button.addActionListener(bl);
				button.setBorderPainted(false);
				button.setFocusPainted(false);
				button.setForeground(Color.BLACK);
  				button.setBackground(Color.WHITE);
			}


			consoleText.setLineWrap(true);

			JScrollPane backpackScrollPane = new JScrollPane(pokemonListPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			backpackScrollPane.setPreferredSize(new Dimension(1100, 500));




			add(nameLabel);
			add(nameText);

			add(typeLabel);
			add(typeText);


			add(backpackScrollPane);

			JScrollPane consoleScrollPane = new JScrollPane(consoleText);
			consoleScrollPane.setPreferredSize(new Dimension(250, 500));
			add(consoleScrollPane);


			JPanel indexPanel = new JPanel();
			indexPanel.setBackground(Color.decode("#29353d"));
			indexPanel.setPreferredSize(new Dimension(280, 300));
			add(indexPanel);
			indexPanel.add(indexName);
			indexPanel.add(indexLabel);
			indexPanel.add(indexText);
			indexPanel.add(indexButton);


			add(appendButton);
			add(insertNameSortedButton);
			add(insertTypeSortedButton);
			add(backpackSizeButton);
			add(getNameListButton);
			add(sortNameButton);
			add(sortTypeButton);
			add(removeButton);
			add(emptyBackpack);
			add(removeNameButton);
			add(removeTypeButton);
			add(removeNameTypeButton);
			add(addRandomButton);


			setBackground(Color.decode("#29353d"));
			setPreferredSize(new Dimension(1700, 650));
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
				} else if (event.getSource() == insertNameSortedButton) {
					String pokemonName = nameText.getText();
					String pokemonType = typeText.getText();

					Pokemon p = new Pokemon(pokemonName, pokemonType);

					pb.sortedInsertByNameIntoBackpack(p);
				} else if (event.getSource() == insertTypeSortedButton) {
					String pokemonName = nameText.getText();
					String pokemonType = typeText.getText();

					Pokemon p = new Pokemon(pokemonName, pokemonType);

					pb.sortedInsertByTypeIntoBackpack(p);
				} else if (event.getSource() == removeButton) {
					pb.removeLastPokemonFromBackpack();
				}
				else if(event.getSource() == emptyBackpack) {
					pb.emptyBackpack();
				}
				else if(event.getSource() == backpackSizeButton){
					consoleText.setText("backpack size: " + pb.getBackpackSize());
				}
				else if(event.getSource() == getNameListButton){
					consoleText.setText("get name list: [\n" + String.join("\n", pb.createNameList())+"\n]");
				}
				else if(event.getSource() == sortNameButton) {
					pb.sortByName();
				}
				else if(event.getSource()== sortTypeButton) {
					pb.sortByType();
				}
				else if(event.getSource() == addRandomButton){
					Random gen = new Random();
					String[][] pokemonPosibilities = {{ "cofagrigus", "ghost" },{ "yamask", "ghost" },{ "rotom", "electric/ghost" },{ "rotom wash", "electric/water" },{ "mow rotom", "electric/grass" },{ "jellicent", "water/ghost" },{ "chandelure", "fire/ghost" },{ "tropius", "grass" },{ "dusknoir", "ghost" },{ "snorlax", "normal" },{ "gliscor", "flying/ground" },{ "uxie", "mind" },{ "caterpie", "bug" },{ "blastoise", "water" },{ "tyranitar", "rock/dark" },{ "mespirit", "physics" },{ "infernape", "fire/brawler" },{ "meowstic", "mind" },{ "groudon", "ground" },{ "alakazam", "mind" },{ "ferrothorn", "grass/steel" },{ "breloom", "grass/brawler" },{ "vivillon", "bug/bird" },{ "ambipom", "normal" },{ "dusclops", "ghost" },{ "terrakion", "rock/brawler" },{ "mawile", "fairy/metal" }, {"whimsicott", "grass/fairy"}, {"donphan", "ground"}, {"chansey", "normal"}};
					for (int i = 0; i < gen.nextInt(23)+1; i++) {
						String[] pnt = pokemonPosibilities[gen.nextInt(pokemonPosibilities.length)];
						Pokemon p = new Pokemon(pnt[0], pnt[1]);

						pb.appendPokemonToBackpack(p);
					}
				}
				else if (event.getSource() == indexButton) {
					try {
						Pokemon pai = pb.getPokemonAtIndex(Integer.parseInt(indexText.getText()));
						indexLabel.setText("<html>pokemon[" + indexText.getText() + "]: " + pai.getName() + "<br> Type: " + pai.getType() + "</html>");
						indexLabel.setIcon(pai.getImage());
					} catch (Exception e) {
						consoleText.setText(e.toString());
					}
				} else if (event.getSource() == removeNameButton) {
					pb.removeAllPokemonWithName(nameText.getText());
				} else if (event.getSource() == removeTypeButton) {
					pb.removeAllPokemonWithType(typeText.getText());
				} else if (event.getSource() == removeNameTypeButton) {
					pb.removeAllPokemonWithNameAndType(nameText.getText(), typeText.getText());
				}


				pokemonListPanel.updateWithBackpack(pb);

			}// end of ActionPerformed method

		}// end of ButtonListener

	}

}