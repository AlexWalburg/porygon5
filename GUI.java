

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;


public class GUI
        extends JPanel implements ActionListener {
    PokemonMaker yourPokemon;
    EnemyPokemonsMaker theirPokemons;
    GroupResults results;
    JPanel contextMaker;
    JButton makeResults, makeReverseResults;
    ArrayList<BasePokemon> basePokemons;
    ArrayList<Move> moves;
    Context context;
    JLabel title = new JLabel("Misc controls");
    JComboBox<String> weathers = new JComboBox<>(new String[]{"--No Weather--","Rain","Heavy Rain", "Sunshine", "Harsh Sunshine", "Hail",
            "Sandstorm", "Strong Winds", "Fog"});
    JComboBox<String> terrains = new JComboBox<>(new String[]{"No Terrain","Misty Terrain", "Psychic Terrain", "Grassy Terrain", "Electric Terrain"});
    JToggleButton hitsManyPokemon = new JToggleButton("Hits Multiple Pokemon?"),
            lightScreen = new JToggleButton("Light Screen?"),
            reflect = new JToggleButton("Reflect?"),
            auroraVeil = new JToggleButton("Aurora Veil?"),
            crit = new JToggleButton("Crit?");
    JSpinner numHits = new JSpinner(new SpinnerNumberModel(1,1,10,1));


    public GUI() throws IOException, ClassNotFoundException {
        setLayout(new GridLayout(1, 4));
        moves = PokemonScraper.loadMoves();
        basePokemons = PokemonScraper.loadPokemon();
        context = new Context();

        yourPokemon = new PokemonMaker(basePokemons, moves);
        theirPokemons = new EnemyPokemonsMaker(basePokemons);
        contextMaker = new JPanel();
        results = new GroupResults(this);
        makeResults = new JButton("<< attacks >>");
        makeResults.addActionListener(this);
        makeReverseResults = new JButton(">> attacks <<");
        makeReverseResults.addActionListener(this);

        weathers.setMaximumSize(new Dimension(weathers.getMaximumSize().width,weathers.getMinimumSize().height));
        terrains.setMaximumSize(new Dimension(terrains.getMaximumSize().width,terrains.getMinimumSize().height));
        Box numHitsHolder = Box.createHorizontalBox(); numHitsHolder.add(new Label("Number of hits")); numHitsHolder.add(numHits);
        numHitsHolder.setMaximumSize(new Dimension(numHitsHolder.getMaximumSize().width,numHitsHolder.getMinimumSize().height));

        contextMaker.setLayout(new BoxLayout(contextMaker,BoxLayout.Y_AXIS));
        contextMaker.add(Box.createVerticalStrut(20));
        contextMaker.add(title);
        contextMaker.add(weathers);
        contextMaker.add(terrains);
        contextMaker.add(hitsManyPokemon);
        contextMaker.add(lightScreen);
        contextMaker.add(reflect);
        contextMaker.add(auroraVeil);
        contextMaker.add(numHitsHolder);
        contextMaker.add(crit);
        contextMaker.add(makeResults);
        contextMaker.add(makeReverseResults);


        add(yourPokemon);
        add(contextMaker);
        add(theirPokemons);
        add(results);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        JFrame window = new JFrame("Porygon5");
        GUI gui = new GUI();
        window.add(gui);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setVisible(true);
    }


    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(makeResults)) {
            loadStuffIntoContext();
            ArrayList<BasePokemon> attackeds = theirPokemons.getChosenPokemon();
            Pokemon attacker = yourPokemon.makePokemon();
            Move move = attacker.moves[0];
            for (BasePokemon attackedBase : attackeds) {
                try {
                    for (Pokemon attacked : PokemonScraper.scrapePikalytics(attackedBase)) {
                        results.addResultsViewer(new ResultsViewer(attacker, attacked, move, context));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            revalidate();
            repaint();
        } else if(actionEvent.getSource().equals(makeReverseResults)){
            loadStuffIntoContext();
            ArrayList<BasePokemon> attackers = theirPokemons.getChosenPokemon();
            Pokemon attacked = yourPokemon.makePokemon();

            for (BasePokemon attackerBase : attackers) {
                try {
                    moves = PokemonScraper.scrapePossibleMoves(attackerBase);
                    for (Pokemon attacker : PokemonScraper.scrapePikalytics(attackerBase)) {
                        Move move = Combat.getBestMove(attacker,attacked,moves,context);
                        results.addResultsViewer(new ResultsViewer(attacker, attacked, move, context,true));
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            revalidate();
            repaint();
        }
    }
    public void loadStuffIntoContext(){
        context.weather=(String)weathers.getSelectedItem();
        context.terrain=(String)terrains.getSelectedItem();
        context.doubles=hitsManyPokemon.isSelected();
        context.lightScreen=lightScreen.isSelected();
        context.reflect=reflect.isSelected();
        context.numHits=(Integer)numHits.getValue();
        context.crit=crit.isSelected();
        results.clearResults();

    }
}
