

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;


public class EnemyPokemonsMaker extends JPanel implements ActionListener {
    public JScrollPane combatantsList;
    public JPanel innerList;
    public JButton addButton;
    public JButton subtractButton;
    public BasePokemon[] basePokemons;
    public JButton exportToFile, importFromFile;
    public static JFileChooser fc = new JFileChooser();


    public EnemyPokemonsMaker(ArrayList<BasePokemon> basePokemons) {

        this.basePokemons = basePokemons.<BasePokemon>toArray(new BasePokemon[0]);
        this.addButton = new JButton("Add a pokemon");
        this.addButton.addActionListener(this);
        this.subtractButton = new JButton("Remove a pokemon");
        this.subtractButton.addActionListener(this);
        this.innerList = new JPanel();
        this.innerList.setLayout(new BoxLayout(this.innerList, 1));

        this.combatantsList = new JScrollPane(this.innerList, 22, 31);

        setLayout(new BoxLayout(this, 1));

        add(Box.createVerticalStrut(20));

        JLabel title = new JLabel("Opponent's pokemon");

        title.setAlignmentX(0.5F);

        add(title);

        add(this.combatantsList);

        JPanel panel = new JPanel();

        BoxLayout horizontal = new BoxLayout(panel, 0);

        panel.setLayout(horizontal);

        panel.add(this.addButton);

        panel.add(this.subtractButton);

        add(panel);

        importFromFile = new JButton("Import from a file");
        importFromFile.addActionListener(this);

        exportToFile = new JButton("Export to a file");
        exportToFile.addActionListener(this);

        JPanel fileIOButtons = new JPanel();
        fileIOButtons.setLayout(new BoxLayout(fileIOButtons,BoxLayout.X_AXIS));
        fileIOButtons.add(exportToFile);
        fileIOButtons.add(importFromFile);

        add(fileIOButtons);

    }

    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(this.addButton)) {
            JComboBox<BasePokemon> thing = new JComboBox<>(this.basePokemons);
            thing.setMaximumSize(new Dimension(thing.getMaximumSize().width,thing.getMinimumSize().height));
            this.innerList.add(thing);

            revalidate();
            repaint();
        } else if (actionEvent.getSource().equals(this.subtractButton)) {
            this.innerList.remove(this.innerList.getComponentCount() - 1);
            revalidate();
            repaint();
        } else if(actionEvent.getSource().equals(importFromFile)){
            int returnVal = fc.showOpenDialog(this);
            if(returnVal==JFileChooser.APPROVE_OPTION){
                try {
                    ArrayList<BasePokemon> pokemons = PokemonScraper.importListOfPokemon(fc.getSelectedFile());
                    innerList.removeAll();
                    for(BasePokemon p : pokemons){
                        JComboBox<BasePokemon> thing = new JComboBox<>(this.basePokemons);
                        thing.setMaximumSize(thing.getMinimumSize());
                        int i = 0;
                        while(!basePokemons[i].name.equals(p.name)) i++; //names are a unique identifier for pokemon
                        thing.setSelectedIndex(i);
                        innerList.add(thing);
                    }
                    revalidate();
                    repaint();
                } catch (IOException | ClassNotFoundException e) {
                    JOptionPane.showMessageDialog(this,"Could not read from file!","Warning",JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if(actionEvent.getSource().equals(exportToFile)){
            int returnVal = fc.showSaveDialog(this);
            if(returnVal==JFileChooser.APPROVE_OPTION){
                try {
                    PokemonScraper.exportListOfPokemon(fc.getSelectedFile(),getChosenPokemon());
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this,"Could not save to file!","Warning",JOptionPane.ERROR_MESSAGE);
                }
            }
        }

    }


    public ArrayList<BasePokemon> getChosenPokemon() {

        int max = this.innerList.getComponentCount();

        ArrayList<BasePokemon> toReturn = new ArrayList<>();

        for (int i = 0; i < max; i++) {

            toReturn.add((BasePokemon) ((JComboBox) this.innerList
                    .getComponent(i)).getSelectedItem());

        }


        return toReturn;

    }


    public static void main(String[] args) throws IOException, ClassNotFoundException {

        JFrame window = new JFrame("Please help me they've kidnapped me and forced me to code");

        window.add(new EnemyPokemonsMaker(PokemonScraper.loadPokemon()));

        window.setResizable(true);

        window.setDefaultCloseOperation(3);

        window.setVisible(true);

    }
}


