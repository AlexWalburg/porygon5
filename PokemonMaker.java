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
    JSpinner dynamaxLevel = new JSpinner(new SpinnerNumberModel(-1,-1,10,1));
    public JComboBox<String> itemJComboBox = new JComboBox<>(new String[]{"Abomasite","Absolite","Absorb Bulb","Adrenaline Orb","Aerodactylite","Aggronite","Air Balloon","Alakazite","Altarianite","Ampharosite","Amulet Coin","Assault Vest","Audinite","Banettite","Beedrillite","Berry Juice","Berserk Gene","Big Root","Binding Band","Black Sludge","Blastoisinite","Blazikenite","Blue Orb","Blunder Policy","Bright Powder","Bug Memory","Burn Drive","Cameruptite","Cell Battery","Charizardite X","Charizardite Y","Chill Drive","Choice Band","Choice Scarf","Choice Specs","Damp Rock","Dark Memory","Destiny Knot","Diancite","Douse Drive","Dragon Memory","Eject Button","Eject Pack","Electric Memory","Electric Seed","Expert Belt","Fairy Memory","Fighting Memory","Fire Memory","Flame Orb","Float Stone","Flying Memory","Focus Band","Focus Sash","Full Incense","Galladite","Garchompite","Gardevoirite","Gengarite","Ghost Memory","Glalitite","Grass Memory","Grassy Seed","Grip Claw","Ground Memory","Gyaradosite","Heat Rock","Heavy-Duty Boots","Heracronite","Houndoominite","Ice Memory","Icy Rock","Iron Ball","Kangaskhanite","King's Rock","Lagging Tail","Latiasite","Latiosite","Lax Incense","Leftovers","Life Orb","Light Clay","Lopunnite","Lucarionite","Luck Incense","Luminous Moss","Macho Brace","Manectite","Mawilite","Medichamite","Mental Herb","Metagrossite","Metronome (item)","Mewtwonite X","Mewtwonite Y","Misty Seed","Muscle Band","Odd Incense","Pidgeotite","Pinsirite","Poison Memory","Power Anklet","Power Band","Power Belt","Power Bracer","Power Herb","Power Lens","Power Weight","Protective Pads","Psychic Memory","Psychic Seed","Quick Claw","Razor Claw","Razor Fang","Red Card","Red Orb","Ring Target","Rock Incense","Rock Memory","Rocky Helmet","Room Service","Rose Incense","Sablenite","Safety Goggles","Salamencite","Sceptilite","Scizorite","Scope Lens","Sea Incense","Sharpedonite","Shed Shell","Shell Bell","Shock Drive","Slowbronite","Smoke Ball","Smooth Rock","Snowball","Steel Memory","Steelixite","Sticky Barb","Swampertite","Terrain Extender","Throat Spray","Toxic Orb","Tyranitarite","Utility Umbrella","Venusaurite","Water Memory","Wave Incense","Weakness Policy","White Herb","Wide Lens","Wise Glasses","Zoom Lens"});

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

    static class verifyEvs extends InputVerifier {
        private final JTextField[] boxes;

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

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
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

        Box dynamax = Box.createHorizontalBox();
        dynamaxLevel.setMaximumSize(new Dimension(dynamaxLevel.getMaximumSize().width,dynamaxLevel.getMinimumSize().height));
        dynamax.add(new JLabel("Dynamax level(-1 is not dynamaxed)"));
        dynamax.add(dynamaxLevel);

        itemJComboBox.setMaximumSize(new Dimension(itemJComboBox.getMaximumSize().width,itemJComboBox.getMinimumSize().height));

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
        add(dynamax);
        add(Box.createVerticalStrut(10));
        add(itemJComboBox);
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

        int dynamaxLv = (Integer)dynamaxLevel.getValue();

        Pokemon pikachu = new Pokemon(base, evs, ivs, 50, nature,dynamaxLv);
        pikachu.moves[0] = (Move) this.moveJComboBox.getSelectedItem();
        pikachu.item = (String) itemJComboBox.getSelectedItem();
        return pikachu;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        JFrame window = new JFrame("Please help me they've kidnapped me and forced me to code");
        window.add(new PokemonMaker(PokemonScraper.loadPokemon(), PokemonScraper.loadMoves()));
        window.setResizable(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);
    }
}


/* Location:              /home/alex/IdeaProjects/2020july4pres/!/PokemonMaker.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */