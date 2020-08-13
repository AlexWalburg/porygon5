import java.util.HashMap;
import java.util.Map;


public class TypeAdvantages {
    private static final HashMap<String, String[]> weaknesses = new HashMap<>();
    static{
        weaknesses.put("Normal", new String[]{"Fighting"});
        weaknesses.put("Fighting", new String[]{"Flying", "Psychic", "Fairy"});
        weaknesses.put("Flying", new String[]{"Rock", "Electric", "Ice"});
        weaknesses.put("Poison", new String[]{"Ground", "Psychic"});
        weaknesses.put("Ground", new String[]{"Water", "Grass", "Ice"});
        weaknesses.put("Rock", new String[]{"Fighting", "Ground", "Steel", "Water", "Grass"});
        weaknesses.put("Bug", new String[]{"Flying", "Rock", "Fire"});
        weaknesses.put("Ghost", new String[]{"Ghost", "Dark"});
        weaknesses.put("Steel", new String[]{"Fighting", "Ground", "Fire"});
        weaknesses.put("Fire", new String[]{"Ground", "Rock", "Water"});
        weaknesses.put("Water", new String[]{"Grass", "Electric"});
        weaknesses.put("Grass", new String[]{"Flying", "Poison", "Bug", "Fire", "Ice"});
        weaknesses.put("Electric", new String[]{"Ground"});
        weaknesses.put("Psychic", new String[]{"Bug", "Ghost", "Dark"});
        weaknesses.put("Ice", new String[]{"Fighting", "Rock", "Steel", "Fire"});
        weaknesses.put("Dragon", new String[]{"Ice", "Dragon", "Fairy"});
        weaknesses.put("Dark", new String[]{"Fighting", "Bug", "Fairy"});
        weaknesses.put("Fairy", new String[]{"Poison", "Steel"});
    }

    private static final HashMap<String, String[]> resists = new HashMap<>();
    static {
        resists.put("Fighting", new String[]{"Rock", "Bug"});
        resists.put("Flying", new String[]{"Bug", "Grass", "Dark"});
        resists.put("Poison", new String[]{"Fighting", "Poison", "Bug", "Grass", "Fairy"});
        resists.put("Ground", new String[]{"Poison", "Rock"});
        resists.put("Rock", new String[]{"Normal", "Flying", "Poison", "Fire"});
        resists.put("Bug", new String[]{"Fighting", "Ground", "Grass"});
        resists.put("Ghost", new String[]{"Poison", "Bug"});
        resists.put("Steel", new String[]{"Normal", "Flying", "Rock", "Bug", "Steel", "Grass", "Psychic", "Ice", "Dragon", "Fairy"});
        resists.put("Fire", new String[]{"Bug", "Steel", "Grass", "Ice", "Fairy"});
        resists.put("Water", new String[]{"Steel", "Fire", "Water", "Ice"});
        resists.put("Grass", new String[]{"Ground", "Water", "Grass", "Electric"});
        resists.put("Electric", new String[]{"Flying", "Steel", "Electric"});
        resists.put("Psychic", new String[]{"Fighting", "Psychic"});
        resists.put("Ice", new String[]{"Ice"});
        resists.put("Dragon", new String[]{"Fire", "Water", "Grass", "Electric"});
        resists.put("Dark", new String[]{"Ghost", "Dark"});
        resists.put("Fairy", new String[]{"Fighting", "Bug", "Dark"});
    }

    private static final HashMap<String, String[]> immune = new HashMap<>();
    static{
        immune.put("Ghost", new String[]{"Normal", "Fighting"});
        immune.put("Steel", new String[]{"Poison"});
        immune.put("Dark", new String[]{"Psychic"});
        immune.put("Normal", new String[]{"Ghost"});
        immune.put("Ground", new String[]{"Electric"});
        immune.put("Fairy", new String[]{"Dragon"});
        immune.put("Flying", new String[]{"Ground"});
    }

    public static double generateMultiplier(Move move, BasePokemon attacked) {
        for (String type : attacked.types) {
            if (type != null)
                for (String weakness : immune.getOrDefault(type, new String[0])) {
                    if (weakness.equals(move.type)) return 0.0D;
                }
        }
        double multiplier = 1.0D;
        for (String type : attacked.types) {
            if (type != null)
                for (String weakness : weaknesses.getOrDefault(type, new String[0])) {
                    if (weakness.equals(move.type)) multiplier *= 2.0D;
                }
        }
        for (String type : attacked.types) {
            if (type != null)
                for (String weakness : resists.getOrDefault(type, new String[0])) {
                    if (weakness.equals(move.type)) multiplier /= 2.0D;
                }
        }
        return multiplier;
    }

    public static boolean isSuperEffective(BasePokemon attacked, Move move) {
        for (String s : weaknesses.getOrDefault(attacked, new String[0])) {
            if (move.type.equals(s)) return true;
        }
        return false;
    }
}
