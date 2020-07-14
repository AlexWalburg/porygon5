

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;


public class GroupResults extends JPanel {
    JPanel panel = new JPanel();

    public GroupResults() {
        BoxLayout layout = new BoxLayout(this.panel, 1);
        this.panel.setLayout(layout);
        JScrollPane scrollPane = new JScrollPane(this.panel, 20, 31);
        setLayout(new BoxLayout(this, 1));
        add(Box.createVerticalStrut(20));
        add(new JLabel("Results"));
        add(scrollPane);
    }

    public void addResultsViewer(ResultsViewer resultsViewer) {
        this.panel.add(resultsViewer);
        revalidate();
        repaint();
    }

    public void clearResults() {
        this.panel.removeAll();
        revalidate();
        repaint();
    }

    public static void main(String[] args) throws IOException {
        JFrame window = new JFrame("test");
        ArrayList<BasePokemon> pokemans = null;
        ArrayList<Move> moves = null;
        try {
            pokemans = PokemonScraper.loadPokemon();
            moves = PokemonScraper.loadMoves();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int i = 0;
        for (; !((BasePokemon) pokemans.get(i)).name.equals("Togekiss"); i++) ;


        Pokemon goodBulbasaur = new Pokemon(pokemans.get(0), new int[]{0, 31, 0, 0, 0, 0}, new int[]{0, 31, 0, 0, 0, 0}, 100, "Brave");
        Move move = moves.get(0);
        Context context = new Context();
        ArrayList<ResultsViewer> resultsViewers = new ArrayList<>();
        for (Pokemon attacked : PokemonScraper.scrapePikalytics(pokemans.get(i))) {
            resultsViewers.add(new ResultsViewer(goodBulbasaur, attacked, move, context));
        }


        window.add(new GroupResults());

        window.setDefaultCloseOperation(3);
        window.setResizable(true);
        window.setVisible(true);
    }
}


/* Location:              /home/alex/IdeaProjects/2020july4pres/!/GroupResults.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */