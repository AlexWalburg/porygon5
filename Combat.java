

import java.io.IOException;
import java.util.ArrayList;


public class Combat {
    public static int[] attack(Pokemon attacker, Pokemon attacked, Move move, Context context) {
        double damage;
        int oldpower = move.power;
        //todo refactor into a hashmap, use the exisitng one and call it once at the start, have it also apply a multiplier at the end
        //only necessary if this isn't performant
        if(attacked.item.equals("Assault Vest")){
            attacked.stats[Pokemon.Stats.SPD]*=3;
            attacked.stats[Pokemon.Stats.SPD]/=2;
        } else if(attacked.item.equals("Electric Seed") && context.terrain.equals("Electric Terrain")){
            attacked.stats[Pokemon.Stats.DEF]*=3;
            attacked.stats[Pokemon.Stats.DEF]/=2;
        } else if(attacked.item.equals("Grassy Seed") && context.terrain.equals("Grassy Terrain")){
            attacked.stats[Pokemon.Stats.DEF]*=3;
            attacked.stats[Pokemon.Stats.DEF]/=2;
        } else if(attacked.item.equals("Misty Seed") && context.terrain.equals("Misty Terrain")){
            attacked.stats[Pokemon.Stats.DEF]*=3;
            attacked.stats[Pokemon.Stats.DEF]/=2;
        } else if(attacked.item.equals("Psychic Seed") && context.terrain.equals("Psychic Terrain")){
            attacked.stats[Pokemon.Stats.DEF]*=3;
            attacked.stats[Pokemon.Stats.DEF]/=2;
        }
        if(attacker.item.equals("Choice Band")){
            attacker.stats[Pokemon.Stats.ATK]*=3;
            attacker.stats[Pokemon.Stats.ATK]/=2;
        } else if(attacker.item.equals("Choice Scarf")){
            attacker.stats[Pokemon.Stats.SPA]*=3;
            attacker.stats[Pokemon.Stats.SPA]/=2;
        } else if(attacker.item.equals("Muscle Band")){
            move.power*=11;
            move.power/=10;
        } else if((attacker.item.equals("Twisted Spoon") || attacker.item.equals("Odd Incense")) && move.type.equals("Psychic")){
            move.power*=6;
            move.power/=5;
        } else if((attacker.item.equals("Hard Stone") || attacker.item.equals("Rock Incense")) && move.type.equals("Psychic")){
            move.power*=6;
            move.power/=5;
        } else if((attacker.item.equals("Miracle Seed") || attacker.item.equals("Rose Incense")) && move.type.equals("Psychic")){
            move.power*=6;
            move.power/=5;
        } else if((attacker.item.equals("Mystic Water") || attacker.item.equals("Sea Incense")) && move.type.equals("Psychic")){
            move.power*=6;
            move.power/=5;
        } else if(attacker.item.equals("Wise Glasses") && move.category.equals("Special")){
            move.power*=11;
            move.power/=10;
        }
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
        if(attacker.item.equals("Expert Belt")) {
            damage *= 1.2;
        } else if(attacker.item.equals("Life Orb")){
            damage*=5324.0/4096.9;
        }
        if(attacked.item.equals("Air Balloon") && move.type.equals("Ground")){
            damage=0;
        }
        int[] damageRange = {(int) (85.0D * damage / 100.0D), (int) damage};
        move.power=oldpower;
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

    public static Move getBestMove(Pokemon attacker, Pokemon attacked, ArrayList<Move> moves, Context context){
        int[] mostDamage = {0,0};
        Move bestMove = null;
        for(Move move : moves){
            int[] damageCandidate = attack(attacker,attacked,move,context);
            if(damageCandidate[0] > mostDamage[0]){
                mostDamage = damageCandidate;
                bestMove = move;
            }
        }
        return bestMove;
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