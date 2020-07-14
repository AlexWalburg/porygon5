

import java.io.IOException;
import java.util.ArrayList;


public class Combat {
    public static int[] attack(Pokemon attacker, Pokemon attacked, Move move, Context context) {
        double damage;
        if (move.category.equals("Physical")) {
            damage = (2.0D * attacker.level / 5.0D + 2.0D) * move.power * attacker.stats[Pokemon.Stats.ATK] / attacked.stats[Pokemon.Stats.DEF];
        } else {
            damage = (2.0D * attacker.level / 5.0D + 2.0D) * move.power * attacker.stats[Pokemon.Stats.SPA] / attacked.stats[Pokemon.Stats.SPD];
        }

        damage /= 50.0D;
        damage += 2.0D;
        if (move.type.equals(attacker.base.types[0]) || move.type.equals(attacker.base.types[1])) {
            damage *= 1.5D;
        }
        damage *= context.generateMultiplier(attacker,move,attacked);
        damage *= TypeAdvantages.generateMultiplier(move, attacked.base);
        int[] damageRange = {(int) (85.0D * damage / 100.0D), (int) damage};
        return damageRange;
    }

    public static double[] percentToKill(int[] damageRange, int numberOfHitsToGoTo, int pokemonHealth) {
        double[] toReturn = new double[numberOfHitsToGoTo];
        for (int i = 0; i < numberOfHitsToGoTo; i++) {
            double totalSize = ((i + 1) * (damageRange[1] - damageRange[0]));
            double activeRange = ((i + 1) * damageRange[1] - pokemonHealth);
            toReturn[i] = activeRange / totalSize;
        }
        return toReturn;
    }

    public static int maxNumberOfHitsToKill(int[] damageRange, int pokemonHealth) {
        if(damageRange[0]!=0) {
            return (int) Math.ceil(pokemonHealth / damageRange[0]);
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {
        try {
            ArrayList<BasePokemon> pokemans = PokemonScraper.loadPokemon();
            ArrayList<Move> moves = PokemonScraper.loadMoves();

            Pokemon goodBulbasaur = new Pokemon(pokemans.get(0), new int[]{0, 31, 0, 0, 0, 0}, new int[]{0, 31, 0, 0, 0, 0}, 100, "Brave");
            Pokemon bulbasaur = new Pokemon(pokemans.get(0), new int[]{0, 0, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0, 0}, 50, "Serious");
            Move move = moves.get(0);
            for (Pokemon attacked : PokemonScraper.scrapePikalytics(pokemans.get(2))) {
                int[] damage = attack(goodBulbasaur, attacked, move, new Context());
                formatPrint(attacked, damage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void formatPrint(Pokemon pokemon, int[] damageRange) {
        System.out.print(pokemon.base.name + ": ");
        System.out.print(pokemon.nature + " ");
        for (int j : pokemon.evs) {
            System.out.print("" + j + "/");
        }
        int max = pokemon.stats[Pokemon.Stats.HP] / damageRange[1] + 1;
        double[] percents = percentToKill(damageRange, max, pokemon.stats[Pokemon.Stats.HP]);
        for (int i = 0; i < max; i++) {
            System.out.print("" + i + 1 + ": ");
            System.out.print("" + damageRange[0] * (i + 1) + "-");
            System.out.println("" + damageRange[1] * (i + 1) + " ");
            System.out.println("" + percents[i] * 100.0D + "%");
        }
    }
}


/* Location:              /home/alex/IdeaProjects/2020july4pres/!/Combat.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */