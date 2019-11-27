package arraylist;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutionException;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

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
			setBackground(Color.LIGHT_GRAY);
			setPreferredSize(new Dimension(1000, 2500));
			// setMaximumSize(new Dimension(1000, 5000));
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
		private JTextField nameText = new JTextField(10); // enter Pokemon name
		private JLabel typeLabel = new JLabel("Pokemon Type");
		private JTextField typeText = new JTextField(10); // enter Pokemon type
		private JLabel sizeLabel = new JLabel();
		private JLabel indexName = new JLabel("Index");
		private JTextField indexText = new JTextField(3);
		private JLabel indexLabel = new JLabel();
		private JLabel indexImage = new JLabel();

		private JButton appendButton = new JButton("add pokemon to end"); // buttons
		private JButton removeButton = new JButton("remove pokemon from end");
		private JButton backpackSizeButton = new JButton("get Backpack size");
		private JButton sortButton = new JButton("name sort Pokemon");
		private JButton sortTypeButton = new JButton("type sort Pokemon");
		private JButton clearButton = new JButton("Clear BackPack");
		private JButton indexButton = new JButton("get pokemon at Index");
		private JButton removeNameButton = new JButton("Remove with name");
		private JButton removeTypeButton = new JButton("Remove with type");
		private JButton removeNameTypeButton = new JButton("Remove with name & type");
		

		PokemonListPanel pokemonListPanel = new PokemonListPanel();

		PokemonBackpack pb = new PokemonBackpack();

		// Constructor: Sets up the Panel
		public MainPokemonPanel() {

			add(nameLabel);
			add(nameText);
			add(typeLabel);
			add(typeText);
			add(indexName);
			add(indexText);
			add(appendButton);
			add(removeButton);
			add(removeNameButton);
			add(removeTypeButton);
			add(removeNameTypeButton);
			add(clearButton);
			add(sortButton);
			add(sortTypeButton);
			
			
			add(indexLabel);
			add(indexImage);
			add(indexButton);
			
			add(backpackSizeButton);
			add(sizeLabel);
			add(pokemonListPanel);

			JScrollPane pane = new JScrollPane(pokemonListPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			pane.setPreferredSize(new Dimension(1100, 500));
			add(pane);

			// Default values

			// connect event handler to event source
			appendButton.addActionListener(new ButtonListener());
			removeButton.addActionListener(new ButtonListener());
			backpackSizeButton.addActionListener(new ButtonListener());
			sortButton.addActionListener(new ButtonListener());
			sortTypeButton.addActionListener(new ButtonListener());
			clearButton.addActionListener(new ButtonListener());
			indexButton.addActionListener(new ButtonListener());
			removeNameButton.addActionListener(new ButtonListener());
			removeTypeButton.addActionListener(new ButtonListener());
			removeNameTypeButton.addActionListener(new ButtonListener());

			// configure panel.
			nameLabel.setForeground(Color.WHITE);
			typeLabel.setForeground(Color.WHITE);
			sizeLabel.setForeground(Color.WHITE);
			indexLabel.setForeground(Color.WHITE);
			indexName.setForeground(Color.WHITE);

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
				} 
				else if (event.getSource() == removeButton) {
					pb.removeLastPokemonFromBackpack();
				}
				else if(event.getSource() == backpackSizeButton){
					sizeLabel.setText("Size : "+pb.getBackpackSize());
				}
				else if(event.getSource() == sortButton){
					pb.sortByName();
				}
				else if(event.getSource()== sortTypeButton) {
					pb.sortByType();
				}
				else if(event.getSource()==clearButton) {
					pb.emptyBackpack();
				}
				else if(event.getSource()==indexButton) {
					indexLabel.setText("<html>pokemon["+indexText.getText()+"]: "+
				pb.getPokemonAtIndex(Integer.parseInt(indexText.getText())).getName()+
				"<br> Type: "+ pb.getPokemonAtIndex(Integer.parseInt(indexText.getText())).getType()+"</html>");
					try {
						indexImage.setIcon(pb.getPokemonAtIndex(Integer.parseInt(indexText.getText())).getImage());
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
				}
				else if(event.getSource()==removeNameButton) {
					pb.removeAllPokemonWithName(nameText.getText());
				}
				else if(event.getSource()==removeTypeButton) {
					pb.removeAllPokemonWithType(typeText.getText());
				}
				else if(event.getSource()==removeNameTypeButton) {
					pb.removeAllPokemonWithNameAndType(nameText.getText(), typeText.getText());
				}
				

				pokemonListPanel.updateWithBackpack(pb);

			}// end of ActionPerformed method

		}// end of ButtonListener

	}

}