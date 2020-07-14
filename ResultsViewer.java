

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class ResultsViewer extends JPanel implements ActionListener {
    public boolean extended = false;

    public ResultsViewer(Pokemon attacker, Pokemon attacked, Move move, Context context) {
        setLayout(new BoxLayout(this, 1));

        int[] damageRange = Combat.attack(attacker, attacked, move, context);
        int max = Combat.maxNumberOfHitsToKill(damageRange, attacked.stats[Pokemon.Stats.HP]);
        double[] percents = Combat.percentToKill(damageRange, max, attacked.stats[Pokemon.Stats.HP]);

        StringBuilder nameOfButton = new StringBuilder(30);
        nameOfButton.append(attacked.base.name);
        nameOfButton.append(": ");
        for (int j : attacked.evs) {
            nameOfButton.append(j);
            nameOfButton.append('/');
        }
        nameOfButton.append(" " + max + "hit KO");

        this.simple = new JButton(nameOfButton.toString());
        StringBuilder fullText = new StringBuilder();

        DecimalFormat df = new DecimalFormat("###.##%");
        int hp = attacked.stats[Pokemon.Stats.HP];
        for (int i = 0; i < max; i++) {
            fullText.append(i + 1);
            fullText.append(": ");
            fullText.append(damageRange[0] * (i + 1));
            fullText.append('-');
            fullText.append(damageRange[1] * (i + 1));
            fullText.append(", ");
            fullText.append('(');
            fullText.append(df.format(damageRange[0] / hp * (i + 1)));
            fullText.append('-');
            fullText.append(df.format(damageRange[1] / hp * (i + 1)));
            fullText.append(") ");
            fullText.append(df.format(percents[i]));
            fullText.append('\n');
        }
        this.detailed = new JTextArea(fullText.toString());

        this.simple.addActionListener(this::actionPerformed);
        this.simple.setAlignmentX(0.0F);
        this.detailed.setAlignmentX(0.0F);
        this.detailed.setEditable(false);
        this.detailed.setLayout(new BorderLayout());

        add(this.simple);
    }

    public JButton simple;
    public JTextArea detailed;

    public void actionPerformed(ActionEvent actionEvent) {
        if (this.extended) {
            remove(this.detailed);
        } else {
            add(this.detailed);
        }
        this.extended = !this.extended;
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        JFrame window = new JFrame("test");
        ArrayList<BasePokemon> pokemans = null;
        ArrayList<Move> moves = null;
        try {
            pokemans = PokemonScraper.loadPokemon();
            moves = PokemonScraper.loadMoves();
        } catch (Exception e) {
            e.printStackTrace();
        }


        Pokemon goodBulbasaur = new Pokemon(pokemans.get(0), new int[]{0, 31, 0, 0, 0, 0}, new int[]{0, 31, 0, 0, 0, 0}, 100, "Brave");
        Pokemon bulbasaur = new Pokemon(pokemans.get(0), new int[]{0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0}, 50, "Serious");
        Move move = moves.get(0);
        window.add(new ResultsViewer(goodBulbasaur, bulbasaur, move, new Context()));
        window.setDefaultCloseOperation(3);
        window.setResizable(true);
        window.setVisible(true);
    }
}


/* Location:              /home/alex/IdeaProjects/2020july4pres/!/ResultsViewer.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */