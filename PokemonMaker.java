import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;


public class PokemonMaker extends JPanel {
    public JPanel evsAndIvs;
    public JPanel evs;
    public JPanel ivs;
    public JPanel labels;
    public JComboBox<BasePokemon> basePokemonJComboBox;
    public JComboBox<Move> moveJComboBox;
    public JComboBox<String> natures;
    public JLabel name;
    public JTextField[] evBoxes;
    public JTextField[] ivBoxes;

    static class verifyIvs extends InputVerifier {
        public boolean verify(JComponent jComponent) {
            String s = ((JTextField) jComponent).getText();
            try {
                int i = Integer.parseInt(s);
                return (i >= 0 && i <= 31);
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }

    class verifyEvs extends InputVerifier {
        private JTextField[] boxes;

        public verifyEvs(JTextField[] boxes) {
            this.boxes = boxes;
        }

        public boolean verify(JComponent jComponent) {
            int sum = 0;
            for (JTextField j : this.boxes) {
                try {
                    sum += Integer.parseInt(j.getText());
                } catch (NumberFormatException e) {
                    return false;
                }
            }
            return (sum <= 510);
        }
    }

    public PokemonMaker(ArrayList<BasePokemon> basePokemons, ArrayList<Move> moves) {
        this.evsAndIvs = new JPanel();
        this.evs = new JPanel();
        this.ivs = new JPanel();
        this.labels = new JPanel();
        this.name = new JLabel("Your pokemon");
        this.name.setAlignmentX(0.5F);
        this.natures = new JComboBox<>();
        for (String s : Pokemon.natures.keySet()) {
            this.natures.addItem(s);
        }
        this.basePokemonJComboBox = new JComboBox<>();
        for (BasePokemon p : basePokemons) {
            this.basePokemonJComboBox.addItem(p);
        }
        this.moveJComboBox = new JComboBox<>();
        for (Move m : moves) {
            this.moveJComboBox.addItem(m);
        }
        this.evBoxes = new JTextField[6];
        for (int i = 0; i < this.evBoxes.length; i++) {
            this.evBoxes[i] = new JTextField("0");
        }
        verifyEvs verifer = new verifyEvs(this.evBoxes);
        int j;
        for (j = 0; j < this.evBoxes.length; j++) {
            this.evBoxes[j].setInputVerifier(verifer);
        }
        this.ivBoxes = new JTextField[6];
        for (j = 0; j < this.ivBoxes.length; j++) {
            this.ivBoxes[j] = new JTextField("31");
            this.ivBoxes[j].setInputVerifier(new verifyIvs());
        }

        setLayout(new BoxLayout(this, 1));
        this.evsAndIvs.setLayout(new GridLayout(1, 3, 10, 10));
        this.labels.setLayout(new GridLayout(7, 1));
        this.ivs.setLayout(new GridLayout(7, 1));
        this.evs.setLayout(new GridLayout(7, 1));
        this.ivs.add(new JLabel("Ivs"));
        this.evs.add(new JLabel("Evs"));
        for (j = 0; j < this.ivBoxes.length; j++) {
            this.ivs.add(this.ivBoxes[j]);
        }
        for (j = 0; j < this.evBoxes.length; j++) {
            this.evs.add(this.evBoxes[j]);
        }
        labels.add(new JLabel(""));
        labels.add(new JLabel("HP"));
        labels.add(new JLabel("ATK"));
        labels.add(new JLabel("DEF"));
        labels.add(new JLabel("SPA"));
        labels.add(new JLabel("SPD"));
        labels.add(new JLabel("SPE"));

        this.evsAndIvs.add(labels);
        this.evsAndIvs.add(this.evs);
        this.evsAndIvs.add(this.ivs);

        add(Box.createVerticalStrut(20));
        add(this.name);
        add(Box.createVerticalStrut(10));
        add(this.basePokemonJComboBox);
        add(Box.createVerticalStrut(10));
        add(this.natures);
        add(Box.createVerticalStrut(10));
        add(this.moveJComboBox);
        add(Box.createVerticalStrut(10));
        add(this.evsAndIvs);
        add(Box.createVerticalStrut(10));
    }

    public Pokemon makePokemon() {
        BasePokemon base = (BasePokemon) this.basePokemonJComboBox.getSelectedItem();
        String nature = (String) this.natures.getSelectedItem();
        int[] ivs = new int[6];
        for (int i = 0; i < ivs.length; i++) {
            ivs[i] = Integer.parseInt(this.ivBoxes[i].getText());
        }
        int[] evs = new int[6];
        for (int j = 0; j < evs.length; j++) {
            evs[j] = Integer.parseInt(this.evBoxes[j].getText());
        }

        Pokemon pikachu = new Pokemon(base, evs, ivs, 50, nature);
        pikachu.moves[0] = (Move) this.moveJComboBox.getSelectedItem();
        return pikachu;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        JFrame window = new JFrame("Please help me they've kidnapped me and forced me to code");
        window.add(new PokemonMaker(PokemonScraper.loadPokemon(), PokemonScraper.loadMoves()));
        window.setResizable(true);
        window.setDefaultCloseOperation(3);
        window.setVisible(true);
    }
}


/* Location:              /home/alex/IdeaProjects/2020july4pres/!/PokemonMaker.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */