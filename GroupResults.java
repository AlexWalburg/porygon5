

import javax.swing.*;
import java.awt.*;
import java.awt.desktop.OpenFilesEvent;
import java.awt.desktop.OpenFilesHandler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class GroupResults extends JPanel implements ActionListener{
    JPanel panel = new JPanel();
    JButton export = new JButton("Export");
    final JFileChooser fc = new JFileChooser();
    GUI above;

    public GroupResults(GUI above) {
        this.above = above;
        BoxLayout layout = new BoxLayout(this.panel, BoxLayout.PAGE_AXIS);
        this.panel.setLayout(layout);
        JScrollPane scrollPane = new JScrollPane(this.panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(Box.createVerticalStrut(20));
        JLabel label = new JLabel("Results");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(label);
        add(scrollPane);
        export.addActionListener(this);
        export.setAlignmentX(Component.RIGHT_ALIGNMENT);
        add(export);
        //Desktop.getDesktop().setOpenFileHandler(this);

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

    public static void main(String[] args) throws IOException, ClassNotFoundException {
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


        window.add(new GroupResults(new GUI()));

        window.setDefaultCloseOperation(3);
        window.setResizable(true);
        window.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        int returnVal = fc.showSaveDialog(this);

        if(returnVal==JFileChooser.APPROVE_OPTION){
            File f = fc.getSelectedFile();
            openFile(f);
        }
    }

    public void openFile(File f) {
        Pokemon pokemon = above.yourPokemon.makePokemon();
        Move move = pokemon.moves[0];
        try {
            FileWriter fw = new FileWriter(f);
            for(Object o : panel.getComponents()){
                ResultsViewer c = (ResultsViewer)o;
                fw.write(pokemon.base.name + "," + pokemon.stats[move.category.equals("Physical") ? Pokemon.Stats.ATK : Pokemon.Stats.SPA] + ",");
                fw.write(move.name);
                fw.write(",");
                String[] toWrite = c.simple.getText().split(" ");
                for(String s: toWrite){
                    if(s.equals("From:")) continue;
                    if(s.contains("/")){
                        for(String s1 : s.split("/")){
                            fw.write(s1);
                            fw.write(",");
                        }
                    } else {
                        fw.write(s + ",");
                    }
                }
                fw.write("\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}