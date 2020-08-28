import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class Pokemon {
    public static class Stats {
        public static int HP = 0, ATK = 1, DEF = 2, SPA = 3, SPD = 4, SPE = 5;
    }

    public static HashMap<String, Integer> namesToStats = new HashMap<>();
    static{
        namesToStats.put("Hp",0);
        namesToStats.put("Atk",1);
        namesToStats.put("Def",2);
        namesToStats.put("Spa",3);
        namesToStats.put("Spd",4);
        namesToStats.put("Spe",5);
    }

    public BasePokemon base;
    public int[] stats = new int[]{0, 0, 0, 0, 0, 0};
    public int[] statsBeforeStages = new int[]{0, 0, 0, 0, 0, 0};
    private int[] stages = new int[]{0, 0, 0, 0, 0, 0};
    public int[] evs;
    public int[] ivs;
    public int[] natureEffects;
    public String nature;
    public int level;
    public double popularity;
    public String item, ability; //I would use an int and an enum, but it's fucking java and the enums are terrible. this also saves time on the encode
    public Move[] moves = new Move[4];
    public static HashMap<String, int[]> natures;
    static {
        natures = new HashMap<>();
        natures.put("Hardy", new int[]{10, 10, 10, 10, 10, 10});
        natures.put("Lonely", new int[]{10, 11, 9, 10, 10, 10});
        natures.put("Brave", new int[]{10, 11, 10, 10, 10, 9});
        natures.put("Adamant", new int[]{10, 11, 10, 9, 10, 10});
        natures.put("Naughty", new int[]{10, 11, 10, 10, 9, 10});
        natures.put("Bold", new int[]{10, 9, 11, 10, 10, 10});
        natures.put("Docile", new int[]{10, 10, 10, 10, 10, 10});
        natures.put("Relaxed", new int[]{10, 10, 11, 10, 10, 9});
        natures.put("Impish", new int[]{10, 10, 11, 9, 10, 10});
        natures.put("Lax", new int[]{10, 10, 11, 10, 10, 10});
        natures.put("Timid", new int[]{10, 9, 10, 10, 10, 11});
        natures.put("Hasty", new int[]{10, 10, 9, 10, 10, 11});
        natures.put("Serious", new int[]{10, 10, 10, 10, 10, 10});
        natures.put("Jolly", new int[]{10, 10, 10, 9, 10, 11});
        natures.put("Naive", new int[]{10, 10, 10, 10, 9, 11});
        natures.put("Modest", new int[]{10, 9, 10, 11, 10, 10});
        natures.put("Mild", new int[]{10, 10, 9, 11, 10, 10});
        natures.put("Quiet", new int[]{10, 10, 10, 11, 10, 9});
        natures.put("Bashful", new int[]{10, 10, 10, 10, 10, 10});
        natures.put("Rash", new int[]{10, 10, 10, 11, 9, 10});
        natures.put("Calm", new int[]{10, 9, 10, 10, 11, 10});
        natures.put("Gentle", new int[]{10, 10, 9, 10, 11, 10});
        natures.put("Sassy", new int[]{10, 10, 10, 10, 11, 9});
        natures.put("Careful", new int[]{10, 10, 10, 9, 11, 10});
        natures.put("Quirky", new int[]{10, 10, 10, 10, 10, 10});
    }


    String[] types = new String[2];

    public Pokemon(BasePokemon species, int[] evs, int[] ivs, int level, String nature) {
        this.nature = nature;
        this.natureEffects = natures.get(nature);


        this.base = species;
        this.level = level;
        this.ivs = ivs;
        this.evs = evs;
        this.statsBeforeStages[Stats.HP] = (ivs[Stats.HP] + 2 * species.stats[Stats.HP] + evs[Stats.HP] / 4) * level / 100 + 10 + level;
        for (int i = 1; i < this.statsBeforeStages.length; i++)
            this.statsBeforeStages[i] = ((ivs[i] + 2 * species.stats[i] + evs[i] / 4) * level / 100 + 5) * this.natureEffects[i] / 10;

        updateStats();
    }
    public Pokemon(BasePokemon species, int[] evs, int[] ivs, int level, String nature, int dynamaxLevel, int[] stages) {
        this.nature = nature;
        this.natureEffects = natures.get(nature);

        for(int i = 0; i < stages.length; i++)
            this.stages[i+1] = stages[i];

        this.base = species;
        this.level = level;
        this.ivs = ivs;
        this.evs = evs;
        this.statsBeforeStages[Stats.HP] = (ivs[Stats.HP] + 2 * species.stats[Stats.HP] + evs[Stats.HP] / 4) * level / 100 + 10 + level;
        for (int i = 1; i < this.statsBeforeStages.length; i++)
            this.statsBeforeStages[i] = ((ivs[i] + 2 * species.stats[i] + evs[i] / 4) * level / 100 + 5) * this.natureEffects[i] / 10;

        //dynamax modification
        if(dynamaxLevel >= 0){
            int oldHp = this.statsBeforeStages[Stats.HP];
            this.statsBeforeStages[Stats.HP] = (int)(oldHp*1.5 + oldHp*0.05*dynamaxLevel);
        }

        updateStats();
    }

    public void setStatChange(int index, int stage){
        stages[index]=stage;
        if(stage>0){
            stats[index]=statsBeforeStages[index]*(2+stage)/2;
        } else {
            stats[index]=statsBeforeStages[index]*2/(2+stage);
        }
    }
    public void addStatChange(int index, int stage){
        stages[index]+=stage;
        if(stage>0){
            stats[index]=statsBeforeStages[index]*(2+stage)/2;
        } else {
            stats[index]=statsBeforeStages[index]*2/(2+stage);
        }
    }

    public int getStatChange(int index){
        return stages[index];
    }

    public void updateStats(){
        for(int i = 0; i < stats.length; i++){
            int stage = stages[i];
            if(stage>0){
                stats[i]=statsBeforeStages[i]*(2+stage)/2;
            } else {
                stats[i]=statsBeforeStages[i]*2/(2+stage);
            }
        }
    }

    public Pokemon(BasePokemon species, int[] evs, int[] ivs, int level, String nature, double popularity, int dynamaxLevel) {
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

        //dynamax modification
        if(dynamaxLevel >= 0){
            int oldHp = this.stats[Stats.HP];
            this.stats[Stats.HP] = (int)(oldHp*1.5 + oldHp*0.05*dynamaxLevel);
        }
    }
}

