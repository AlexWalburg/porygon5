import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;


public class PokemonMaker extends JPanel {
    public JPanel evsAndIvs;
    public JPanel evs;
    public JPanel ivs;
    public JPanel labels;
    public JPanel statChanges;
    public JComboBox<BasePokemon> basePokemonJComboBox;
    public JComboBox<Move> moveJComboBox;
    public JComboBox<String> natures;
    public JLabel name;
    public JTextField[] evBoxes;
    public JTextField[] ivBoxes;
    public JSpinner[] statChangesBoxes = new JSpinner[5];
    JSpinner dynamaxLevel = new JSpinner(new SpinnerNumberModel(-1,-1,10,1));
    public JComboBox<String> itemJComboBox = new JComboBox<>(new String[]{"No Item","Abomasite","Absolite","Absorb Bulb","Adrenaline Orb","Aerodactylite","Aggronite","Air Balloon","Alakazite","Altarianite","Ampharosite","Amulet Coin","Assault Vest","Audinite","Banettite","Beedrillite","Berry Juice","Berserk Gene","Big Root","Binding Band","Black Sludge","Blastoisinite","Blazikenite","Blue Orb","Blunder Policy","Bright Powder","Bug Memory","Burn Drive","Cameruptite","Cell Battery","Charizardite X","Charizardite Y","Chill Drive","Choice Band","Choice Scarf","Choice Specs","Damp Rock","Dark Memory","Destiny Knot","Diancite","Douse Drive","Dragon Memory","Eject Button","Eject Pack","Electric Memory","Electric Seed","Expert Belt","Fairy Memory","Fighting Memory","Fire Memory","Flame Orb","Float Stone","Flying Memory","Focus Band","Focus Sash","Full Incense","Galladite","Garchompite","Gardevoirite","Gengarite","Ghost Memory","Glalitite","Grass Memory","Grassy Seed","Grip Claw","Ground Memory","Gyaradosite","Heat Rock","Heavy-Duty Boots","Heracronite","Houndoominite","Ice Memory","Icy Rock","Iron Ball","Kangaskhanite","King's Rock","Lagging Tail","Latiasite","Latiosite","Lax Incense","Leftovers","Life Orb","Light Clay","Lopunnite","Lucarionite","Luck Incense","Luminous Moss","Macho Brace","Manectite","Mawilite","Medichamite","Mental Herb","Metagrossite","Metronome (item)","Mewtwonite X","Mewtwonite Y","Misty Seed","Muscle Band","Odd Incense","Pidgeotite","Pinsirite","Poison Memory","Power Anklet","Power Band","Power Belt","Power Bracer","Power Herb","Power Lens","Power Weight","Protective Pads","Psychic Memory","Psychic Seed","Quick Claw","Razor Claw","Razor Fang","Red Card","Red Orb","Ring Target","Rock Incense","Rock Memory","Rocky Helmet","Room Service","Rose Incense","Sablenite","Safety Goggles","Salamencite","Sceptilite","Scizorite","Scope Lens","Sea Incense","Sharpedonite","Shed Shell","Shell Bell","Shock Drive","Slowbronite","Smoke Ball","Smooth Rock","Snowball","Steel Memory","Steelixite","Sticky Barb","Swampertite","Terrain Extender","Throat Spray","Toxic Orb","Tyranitarite","Utility Umbrella","Venusaurite","Water Memory","Wave Incense","Weakness Policy","White Herb","Wide Lens","Wise Glasses","Zoom Lens"});
    public JComboBox<String> abilityJComboBox = new JComboBox<>(new String[]{"No Ability","Adaptability","Aerilate","Aftermath","Air Lock","Analytic","Anger Point","Anticipation","Arena Trap","Aroma Veil","Aura Break","Bad Dreams","Ball Fetch","Battery","Battle Armor","Battle Bond","Beast Boost","Berserk","Big Pecks","Blaze","Bulletproof","Cacophony","Cheek Pouch","Chlorophyll","Clear Body","Cloud Nine","Color Change","Comatose","Competitive","Compound Eyes","Contrary","Corrosion","Cotton Down","Cursed Body","Cute Charm","Damp","Dancer","Dark Aura","Dauntless Shield","Dazzling","Defeatist","Defiant","Delta Stream","Desolate Land","Disguise","Download","Drizzle","Drought","Dry Skin","Early Bird","Effect Spore","Electric Surge","Emergency Exit","Fairy Aura","Filter","Flame Body","Flare Boost","Flash Fire","Flower Gift","Flower Veil","Fluffy","Forecast","Forewarn","Friend Guard","Frisk","Full Metal Body","Fur Coat","Gale Wings","Galvanize","Gluttony","Gooey","Gorilla Tactics","Grass Pelt","Grassy Surge","Gulp Missile","Guts","Harvest","Healer","Heatproof","Heavy Metal","Honey Gather","Huge Power","Hunger Switch","Hustle","Hydration","Hyper Cutter","Ice Body","Ice Face","Ice Scales","Illuminate","Illusion","Immunity","Imposter","Infiltrator","Innards Out","Inner Focus","Insomnia","Intimidate","Intrepid Sword","Iron Barbs","Iron Fist","Justified","Keen Eye","Klutz","Leaf Guard","Levitate","Libero","Light Metal","Lightning Rod","Limber","Liquid Ooze","Liquid Voice","Long Reach","Magic Bounce","Magic Guard","Magician","Magma Armor","Magnet Pull","Marvel Scale","Mega Launcher","Merciless","Mimicry","Minus","Mirror Armor","Misty Surge","Mold Breaker","Moody","Motor Drive","Moxie","Multiscale","Multitype","Mummy","Natural Cure","Neuroforce","Neutralizing Gas","No Guard","Normalize","Oblivious","Overcoat","Overgrow","Own Tempo","Parental Bond","Pastel Veil","Perish Body","Pickpocket","Pickup","Pixilate","Plus","Poison Heal","Poison Point","Poison Touch","Power Construct","Power of Alchemy","Power Spot","Prankster","Pressure","Primordial Sea","Prism Armor","Propeller Tail","Protean","Psychic Surge","Punk Rock","Pure Power","Queenly Majesty","Quick Draw","Quick Feet","Rain Dish","Rattled","Receiver","Reckless","Refrigerate","Regenerator","Ripen","Rivalry","RKS System","Rock Head","Rough Skin","Run Away","Sand Force","Sand Rush","Sand Spit","Sand Stream","Sand Veil","Sap Sipper","Schooling","Scrappy","Screen Cleaner","Serene Grace","Shadow Shield","Shadow Tag","Shed Skin","Sheer Force","Shell Armor","Shield Dust","Shields Down","Simple","Skill Link","Slow Start","Slush Rush","Sniper","Snow Cloak","Snow Warning","Solar Power","Solid Rock","Soul-Heart","Soundproof","Speed Boost","Stakeout","Stall","Stalwart","Stamina","Stance Change","Static","Steadfast","Steam Engine","Steelworker","Steely Spirit","Stench","Sticky Hold","Storm Drain","Strong Jaw","Sturdy","Suction Cups","Super Luck","Surge Surfer","Swarm","Sweet Veil","Swift Swim","Symbiosis","Synchronize","Tangled Feet","Tangling Hair","Technician","Telepathy","Teravolt","Thick Fat","Tinted Lens","Torrent","Tough Claws","Toxic Boost","Trace","Triage","Truant","Turboblaze","Unaware","Unburden","Unnerve","Unseen Fist","Victory Star","Vital Spirit","Volt Absorb","Wandering Spirit","Water Absorb","Water Bubble","Water Compaction","Water Veil","Weak Armor","White Smoke","Wimp Out","Wonder Guard","Wonder Skin","Zen Mode"});

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

        for(int i = 0; i<5; i++)
            statChangesBoxes[i] = new JSpinner(new SpinnerNumberModel(0,-6,6,1));

        statChanges = new JPanel();
        statChanges.setLayout(new GridLayout(2,5));
        statChanges.add(new JLabel("Atk"));
        statChanges.add(new JLabel("Def"));
        statChanges.add(new JLabel("Spa"));
        statChanges.add(new JLabel("Spd"));
        statChanges.add(new JLabel("Spe"));
        for(int i = 0; i < statChangesBoxes.length; i++){
            statChanges.add(statChangesBoxes[i]);
            statChanges.setMaximumSize(new Dimension(statChanges.getMaximumSize().width,statChanges.getMinimumSize().height));
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
        abilityJComboBox.setMaximumSize(new Dimension(abilityJComboBox.getMaximumSize().width,abilityJComboBox.getMinimumSize().height));

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
        add(statChanges);
        add(Box.createVerticalStrut(10));
        add(itemJComboBox);
        add(Box.createVerticalStrut(10));
        add(abilityJComboBox);
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

        int[] statStagesValues = new int[5];
        for(int i = 0; i < statStagesValues.length; i++){
            statStagesValues[i]=(Integer)statChangesBoxes[i].getValue();
        }

        Pokemon pikachu = new Pokemon(base, evs, ivs, 50, nature,dynamaxLv,statStagesValues);
        pikachu.moves[0] = (Move) this.moveJComboBox.getSelectedItem();
        pikachu.item = (String) itemJComboBox.getSelectedItem();
        pikachu.ability = (String) abilityJComboBox.getSelectedItem();
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