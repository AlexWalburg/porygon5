

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
    JComboBox<String> weathers = new JComboBox<String>(new String[]{"--No Weather--","Rain","Heavy Rain", "Sunshine", "Harsh Sunshine", "Hail",
            "Sandstorm", "Strong Winds", "Fog"});

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

        contextMaker.setLayout(new BoxLayout(contextMaker,BoxLayout.Y_AXIS));
        contextMaker.add(Box.createVerticalStrut(20));
        contextMaker.add(title);
        contextMaker.add(weathers);
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
            context.weather=(String)weathers.getSelectedItem();
            results.clearResults();
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
            context.weather=(String)weathers.getSelectedItem();
            results.clearResults();
            ArrayList<BasePokemon> attackers = theirPokemons.getChosenPokemon();
            Pokemon attacked = yourPokemon.makePokemon();

            for (BasePokemon attackerBase : attackers) {
                try {
                    moves = PokemonScraper.scrapePossibleMoves(attackerBase);
                    for (Pokemon attacker : PokemonScraper.scrapePikalytics(attackerBase)) {
                        Move move = Combat.getBestMove(attacker,attacked,moves,context);
                        results.addResultsViewer(new ResultsViewer(attacker, attacked, move, context,true));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch(ClassNotFoundException e ){
                    e.printStackTrace();
                }
            }
            revalidate();
            repaint();
        }
    }
}


/* Location:              /home/alex/IdeaProjects/2020july4pres/!/GUI.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */