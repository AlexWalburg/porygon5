

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
    JButton makeResults;
    ArrayList<BasePokemon> basePokemons;
    ArrayList<Move> moves;
    Context context;

    public GUI() throws IOException, ClassNotFoundException {
        setLayout(new GridLayout(1, 4));
        moves = PokemonScraper.loadMoves();
        basePokemons = PokemonScraper.loadPokemon();
        context = new Context();

        yourPokemon = new PokemonMaker(basePokemons, moves);
        theirPokemons = new EnemyPokemonsMaker(basePokemons);
        contextMaker = new JPanel();
        results = new GroupResults();
        makeResults = new JButton("Do All the calculations");
        makeResults.addActionListener(this);

        contextMaker.add(makeResults);

        add(yourPokemon);
        add(contextMaker);
        add(theirPokemons);
        add(results);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        JFrame window = new JFrame("Why am I doing this");
        GUI gui = new GUI();
        window.add(gui);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setVisible(true);
    }


    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(makeResults)) {
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
        }
    }
}


/* Location:              /home/alex/IdeaProjects/2020july4pres/!/GUI.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */