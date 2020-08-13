import java.util.Map;


public class Pokemon {
    public static class Stats {
        public static int HP = 0, ATK = 1, DEF = 2, SPA = 3, SPD = 4, SPE = 5;
    }

    public static Map<String, Integer> namesToStats = Map.of("Hp", Integer.valueOf(0), "Atk", Integer.valueOf(1), "Def", Integer.valueOf(2), "Spa", Integer.valueOf(3), "Spd", Integer.valueOf(4), "Spe", Integer.valueOf(5));
    public BasePokemon base;
    public int[] stats = new int[]{0, 0, 0, 0, 0, 0};
    public int[] evs;
    public int[] ivs;
    public int[] natureEffects;
    public String nature;
    public int level;
    public double popularity;
    public String item, ability; //I would use an int and an enum, but it's fucking java and terrible. this also saves time on the encode
    public Move[] moves = new Move[4];
    public static Map<String, int[]> natures = Map.ofEntries(
            Map.entry("Hardy", new int[]{10, 10, 10, 10, 10, 10
            }), Map.entry("Lonely", new int[]{10, 11, 9, 10, 10, 10
            }), Map.entry("Brave", new int[]{10, 11, 10, 10, 10, 9
            }), Map.entry("Adamant", new int[]{10, 11, 10, 9, 10, 10
            }), Map.entry("Naughty", new int[]{10, 11, 10, 10, 9, 10
            }), Map.entry("Bold", new int[]{10, 9, 11, 10, 10, 10
            }), Map.entry("Docile", new int[]{10, 10, 10, 10, 10, 10
            }), Map.entry("Relaxed", new int[]{10, 10, 11, 10, 10, 9
            }), Map.entry("Impish", new int[]{10, 10, 11, 9, 10, 10
            }), Map.entry("Lax", new int[]{10, 10, 11, 10, 10, 10}),
            Map.entry("Timid", new int[]{10, 9, 10, 10, 10, 11
            }), Map.entry("Hasty", new int[]{10, 10, 9, 10, 10, 11
            }), Map.entry("Serious", new int[]{10, 10, 10, 10, 10, 10
            }), Map.entry("Jolly", new int[]{10, 10, 10, 9, 10, 11
            }), Map.entry("Naive", new int[]{10, 10, 10, 10, 9, 11
            }), Map.entry("Modest", new int[]{10, 9, 10, 11, 10, 10
            }), Map.entry("Mild", new int[]{10, 10, 9, 11, 10, 10
            }), Map.entry("Quiet", new int[]{10, 10, 10, 11, 10, 9
            }), Map.entry("Bashful", new int[]{10, 10, 10, 10, 10, 10
            }), Map.entry("Rash", new int[]{10, 10, 10, 11, 9, 10}),
            Map.entry("Calm", new int[]{10, 9, 10, 10, 11, 10
            }), Map.entry("Gentle", new int[]{10, 10, 9, 10, 11, 10
            }), Map.entry("Sassy", new int[]{10, 10, 10, 10, 11, 9
            }), Map.entry("Careful", new int[]{10, 10, 10, 9, 11, 10
            }), Map.entry("Quirky", new int[]{10, 10, 10, 10, 10, 10}));


    String[] types = new String[2];

    public Pokemon(BasePokemon species, int[] evs, int[] ivs, int level, String nature) {
        this.nature = nature;
        this.natureEffects = natures.get(nature);


        this.base = species;
        this.level = level;
        this.ivs = ivs;
        this.evs = evs;
        this.stats[Stats.HP] = (ivs[Stats.HP] + 2 * species.stats[Stats.HP] + evs[Stats.HP] / 4) * level / 100 + 10 + level;
        for (int i = 1; i < this.stats.length; i++)
            this.stats[i] = ((ivs[i] + 2 * species.stats[i] + evs[i] / 4) * level / 100 + 5) * this.natureEffects[i] / 10;
    }

    public Pokemon(BasePokemon species, int[] evs, int[] ivs, int level, String nature, double popularity) {
        this.nature = nature;
        this.natureEffects = natures.get(nature);


        this.base = species;
        this.level = level;
        this.ivs = ivs;
        this.evs = evs;
        this.stats[Stats.HP] = (ivs[Stats.HP] + 2 * species.stats[Stats.HP] + evs[Stats.HP] / 4) * level / 100 + 10 + level;
        for (int i = 1; i < this.stats.length; i++) {
            this.stats[i] = ((ivs[i] + 2 * species.stats[i] + evs[i] / 4) * level / 100 + 5) * this.natureEffects[i] / 10;
        }
        this.popularity = popularity;
    }
}


/* Location:              /home/alex/IdeaProjects/2020july4pres/!/Pokemon.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */