

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;


public class PokemonScraper {
    public static void scrapePokemon() throws IOException {
        Document doc = Jsoup.connect("https://bulbapedia.bulbagarden.net/wiki/LPBBS").get();
        Element table = (Element) doc.getElementsByTag("tbody").get(1);
        Elements pokemons = table.children();
        FileOutputStream fos = new FileOutputStream("pokemans.json");
        ObjectOutputStream output = new ObjectOutputStream(fos);
        pokemons.remove(0);
        output.writeInt(pokemons.size());


        for (Element pokemon : pokemons) {
            Document typesDoc;
            Elements stats = pokemon.children();


            int pokedexNum = Integer.parseInt(((Element) stats.get(0)).text());
            String name = ((Element) stats.get(2)).text();
            int hp = Integer.parseInt(((Element) stats.get(3)).text());
            int atk = Integer.parseInt(stats.get(4).text());
            int def = Integer.parseInt(((Element) stats.get(5)).text());
            int spa = Integer.parseInt(((Element) stats.get(6)).text());
            int spd = Integer.parseInt(((Element) stats.get(7)).text());
            int spe = Integer.parseInt(((Element) stats.get(8)).text());

            if (name.contains(" (")) {
                typesDoc = Jsoup.connect("https://bulbapedia.bulbagarden.net/wiki/" + name.substring(0, name.indexOf(" (")).replace(' ', '_') + "_(Pokémon)").get();
            } else {
                typesDoc = Jsoup.connect("https://bulbapedia.bulbagarden.net/wiki/" + name.replace(' ', '_') + "_(Pokémon)").get();
            }
            Element typeTable = ((Element) typesDoc.getElementsByAttributeValue("title", "type").get(0)).parent().parent();
            Elements types = typeTable.getElementsByAttributeStarting("href");

            String[] finalTypes = new String[2];
            finalTypes[0] = null;
            finalTypes[1] = null;

            if (!((Element) types.get(2)).text().equals("Unknown")) {
                finalTypes[0] = ((Element) types.get(1)).text();
                finalTypes[1] = ((Element) types.get(2)).text();
            } else {
                finalTypes[0] = ((Element) types.get(1)).text();
            }

            BasePokemon basePokemon = new BasePokemon(name, pokedexNum, hp, atk, def, spa, spd, spe, finalTypes);


            output.writeObject(basePokemon);
            System.out.println(name);
        }
        output.close();
        fos.close();
    }

    public static ArrayList<Move> scrapePossibleMoves(BasePokemon pokemon) throws IOException, ClassNotFoundException {
        ArrayList<Move> toReturn = new ArrayList<>();
        Document doc = Jsoup.connect("https://www.pikalytics.com/pokedex/ss/" + pokemon.name).get();
        Elements moves = doc.getElementsByClass("pokedex-move-entry-new");
        ArrayList<Move> realMoves = loadMoves();
        for(Element move : moves){
            if(move.child(0).text().equals("Other")){
                return toReturn;
            } else {
                toReturn.add(
                        findMoveinMovesList(move.child(0).text(),realMoves)
                );
            }
        }
        return toReturn;
    }

    public static ArrayList<BasePokemon> loadPokemon() throws IOException, ClassNotFoundException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        ObjectInputStream in = new ObjectInputStream(classLoader.getResource("pokemans.json").openStream());
        int size = in.readInt();
        ArrayList<BasePokemon> pokemans = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            pokemans.add((BasePokemon) in.readObject());
        }
        return pokemans;
    }

    public static void scrapeMoves() throws IOException {
        Document doc = Jsoup.connect("https://bulbapedia.bulbagarden.net/wiki/List_of_moves").get();
        Element table = (Element) doc.getElementsByTag("tbody").get(1);

        FileOutputStream fos = new FileOutputStream("dem_moves.json");
        ObjectOutputStream output = new ObjectOutputStream(fos);

        Elements moves = table.children();
        moves.remove(0);

        output.writeInt(moves.size());

        for (Element move : moves) {
            int power, accuracy;
            Elements attributes = move.children();
            String name = ((Element) attributes.get(1)).text();
            String type = ((Element) attributes.get(2)).text();
            String category = ((Element) attributes.get(3)).text();

            try {
                power = Integer.parseInt(((Element) attributes.get(6)).text().replace("%", "").replace("*", ""));
            } catch (Exception e) {
                power = 0;
                System.err.println(name + " | " + name);
            }

            try {
                accuracy = Integer.parseInt(((Element) attributes.get(7)).text().replace("%", "").replace("*", ""));
            } catch (Exception e) {
                accuracy = 100;
                System.err.println(name + " | " + name);
            }
            Move mov = new Move(name, type, category, power, accuracy);
            output.writeObject(mov);
        }
        output.close();
        fos.close();
    }

    public static ArrayList<Pokemon> scrapePikalytics(BasePokemon pokemon) throws IOException {
        Document doc = Jsoup.connect("https://www.pikalytics.com/pokedex/ss/" + pokemon.name).get();
        Element table = doc.getElementsByClass("pokemon-stat-container").last();
        Elements spreads = table.getElementsByClass("pokedex-move-entry-new");
        ArrayList<Pokemon> toReturn = new ArrayList<>();
        for (Element spread : spreads) {
            Elements attributes = spread.children();
            String nature = ((Element) attributes.get(0)).text();
            int[] stats = new int[6];
            for (int i = 0; i < stats.length; i++) {
                stats[i] = Integer.parseInt(((Element) attributes.get(i + 1)).text().replace("/", ""));
            }
            double popularity = Double.parseDouble(((Element) attributes.get(7)).text().replace("%", ""));
            int[] ivs = {31, 31, 31, 31, 31, 31};
            toReturn.add(new Pokemon(pokemon, stats, ivs, 50, nature, popularity));
        }
        return toReturn;
    }

    public static ArrayList<Move> loadMoves() throws IOException, ClassNotFoundException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        ObjectInputStream in = new ObjectInputStream(classLoader.getResource("dem_moves.json").openStream());
        int size = in.readInt();
        ArrayList<Move> moves = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            moves.add((Move) in.readObject());
        }
        return moves;
    }

    public static ArrayList<Pokemon> scrapeFromShowdownFormat(String s, ArrayList<BasePokemon> basePokemons) throws IOException, ClassNotFoundException {
        String[] lines = s.split("\n");
        ArrayList<Move> allMoves = loadMoves();
        ArrayList<Pokemon> toReturn = new ArrayList<>();
        int lineCount = lines.length;
        for (int i = 0; i < lineCount; i++) {
            String name;

            if (lines[i].indexOf(' ') == -1) {
                name = lines[i].substring(0, lines[i].indexOf(' '));
            } else {
                name = lines[i];
            }
            i += 2;
            int level = Integer.parseInt(lines[i].split(" ")[0]);
            i++;
            if (lines[i].contains("Shiny")) i++;
            int[] evs = {0, 0, 0, 0, 0, 0};
            if (lines[i].contains("EVs")) {
                String[] modifiers = lines[i].substring(lines[i].indexOf(' ')).split(" ");
                for (String modifier : modifiers) {
                    String[] data = modifier.split(" ");
                    evs[((Integer) Pokemon.namesToStats.get(data[1])).intValue()] = Integer.parseInt(data[0]);
                }
                i++;
            }
            String nature = "";
            if (lines[i].contains("Nature")) {
                nature = lines[i].substring(0, lines[i].indexOf(' '));
                i++;
            }
            int[] ivs = {31, 31, 31, 31, 31, 31};
            if (lines[i].contains("IVs")) {
                String[] modifiers = lines[i].substring(lines[i].indexOf(' ')).split(" ");
                for (String modifier : modifiers) {
                    String[] data = modifier.split(" ");
                    evs[((Integer) Pokemon.namesToStats.get(data[1])).intValue()] = Integer.parseInt(data[0]);
                }
                i++;
            }
            Move[] moves = new Move[4];
            int subI = 0;
            while (lines[i].charAt(0) == '-') {
                moves[subI] = findMoveinMovesList(lines[i].substring(2), allMoves);
                subI++;
                i++;
            }

            Pokemon pokemon = new Pokemon(findPokemoninPokemonList(name, basePokemons), evs, ivs, level, nature);


            pokemon.moves = moves;
            toReturn.add(pokemon);
        }

        return toReturn;
    }

    public static Move findMoveinMovesList(String name, ArrayList<Move> moves) {
        int i = 0;
        while (!((Move) moves.get(i)).name.equals(name)) {
            i++;
        }
        return moves.get(i);
    }

    public static BasePokemon findPokemoninPokemonList(String name, ArrayList<BasePokemon> pokmeons) {
        int i = 0;
        while (!((BasePokemon) pokmeons.get(i)).name.equals(name)) {
            i++;
        }
        return pokmeons.get(i);
    }

    public static void main(String[] args) {
        try {
            for(Move m : scrapePossibleMoves(findPokemoninPokemonList("Rillaboom",loadPokemon()))){
                System.out.println(m.name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
