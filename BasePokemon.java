import java.io.Serializable;


public class BasePokemon implements Serializable {
    String name;
    int pokedexNum;

    public static class Stats {
        public static int HP = 0, ATK = 1, DEF = 2, SPA = 3, SPD = 4, SPE = 5;
    }

    int[] stats = new int[]{0, 0, 0, 0, 0, 0};
    String[] types;

    public BasePokemon(String name, int pokedexNum, int hp, int atk, int def, int spa, int spd, int spe, String[] types) {
        this.name = name;
        this.pokedexNum = pokedexNum;
        this.stats[Stats.HP] = hp;
        this.stats[Stats.ATK] = atk;
        this.stats[Stats.DEF] = def;
        this.stats[Stats.SPA] = spa;
        this.stats[Stats.SPD] = spd;
        this.stats[Stats.SPE] = spe;
        this.types = types;
    }

    public BasePokemon(String name, int pokedexNum, int[] stats, String[] types) {
        this.name = name;
        this.pokedexNum = pokedexNum;
        this.stats = stats;
        this.types = types;
    }

    public String toString() {
        return this.name;
    }
}


/* Location:              /home/alex/IdeaProjects/2020july4pres/!/BasePokemon.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */