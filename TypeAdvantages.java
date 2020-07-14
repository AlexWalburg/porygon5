import java.util.Map;


public class TypeAdvantages {
    private static final Map<String, String[]> weaknesses = (Map) Map.ofEntries((Map.Entry<String, String>[]) new Map.Entry[]{
            Map.entry("Normal", new String[]{"Fighting"
            }), Map.entry("Fighting", new String[]{"Flying", "Psychic", "Fairy"
    }), Map.entry("Flying", new String[]{"Rock", "Electric", "Ice"
    }), Map.entry("Poison", new String[]{"Ground", "Psychic"
    }), Map.entry("Ground", new String[]{"Water", "Grass", "Ice"
    }), Map.entry("Rock", new String[]{"Fighting", "Ground", "Steel", "Water", "Grass"
    }), Map.entry("Bug", new String[]{"Flying", "Rock", "Fire"
    }), Map.entry("Ghost", new String[]{"Ghost", "Dark"
    }), Map.entry("Steel", new String[]{"Fighting", "Ground", "Fire"
    }), Map.entry("Fire", new String[]{"Ground", "Rock", "Water"}),
            Map.entry("Water", new String[]{"Grass", "Electric"
            }), Map.entry("Grass", new String[]{"Flying", "Poison", "Bug", "Fire", "Ice"
    }), Map.entry("Electric", new String[]{"Ground"
    }), Map.entry("Psychic", new String[]{"Bug", "Ghost", "Dark"
    }), Map.entry("Ice", new String[]{"Fighting", "Rock", "Steel", "Fire"
    }), Map.entry("Dragon", new String[]{"Ice", "Dragon", "Fairy"
    }), Map.entry("Dark", new String[]{"Fighting", "Bug", "Fairy"
    }), Map.entry("Fairy", new String[]{"Poison", "Steel"})});

    private static final Map<String, String[]> resists = (Map<String, String[]>) Map.ofEntries(new Map.Entry[]{
            Map.entry("Fighting", new String[]{"Rock", "Bug"
            }), Map.entry("Flying", new String[]{"Bug", "Grass", "Dark"
    }), Map.entry("Poison", new String[]{"Fighting", "Poison", "Bug", "Grass", "Fairy"
    }), Map.entry("Ground", new String[]{"Poison", "Rock"
    }), Map.entry("Rock", new String[]{"Normal", "Flying", "Poison", "Fire"
    }), Map.entry("Bug", new String[]{"Fighting", "Ground", "Grass"
    }), Map.entry("Ghost", new String[]{"Poison", "Bug"
    }), Map.entry("Steel", new String[]{"Normal", "Flying", "Rock", "Bug", "Steel", "Grass", "Psychic", "Ice", "Dragon", "Fairy"
    }), Map.entry("Fire", new String[]{"Bug", "Steel", "Grass", "Ice", "Fairy"
    }), Map.entry("Water", new String[]{"Steel", "Fire", "Water", "Ice"}),
            Map.entry("Grass", new String[]{"Ground", "Water", "Grass", "Electric"
            }), Map.entry("Electric", new String[]{"Flying", "Steel", "Electric"
    }), Map.entry("Psychic", new String[]{"Fighting", "Psychic"
    }), Map.entry("Ice", new String[]{"Ice"
    }), Map.entry("Dragon", new String[]{"Fire", "Water", "Grass", "Electric"
    }), Map.entry("Dark", new String[]{"Ghost", "Dark"
    }), Map.entry("Fairy", new String[]{"Fighting", "Bug", "Dark"})});

    private static final Map<String, String[]> immune = (Map) Map.ofEntries((Map.Entry<String, String>[]) new Map.Entry[]{
            Map.entry("Ghost", new String[]{"Normal", "Fighting"
            }), Map.entry("Steel", new String[]{"Poison"
    }), Map.entry("Dark", new String[]{"Psychic"
    }), Map.entry("Normal", new String[]{"Ghost"
    }), Map.entry("Ground", new String[]{"Electric"
    }), Map.entry("Fairy", new String[]{"Dragon"
    }), Map.entry("Flying", new String[]{"Ground"})});

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
}
