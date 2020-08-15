import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class AbilityEffects {
    private static final HashMap<String, ability> attackerAbilities = new HashMap();
    private static final HashMap<String, ability> attackedAbilities = new HashMap();
    public static final ArrayList<String> moveBulletProofBlocks = new ArrayList<>(Arrays.asList("Acid Spray", "Aura Sphere", "Beak Blast", "Bullet Seed", "Electro Ball", "Energy Ball", "Focus Blast", "Gyro Ball", "Mist Ball", "Octazooka", "Pollen Puff", "Pyro Ball", "Rocky Blast", "Rock Wrecker", "Searing Shot", "Seed Bomb", "Shadow Ball", "Sludge Bomb", "Weather Ball", "Zap Cannon"));
    public static final ArrayList<String> moveCacophonyBlocks = new ArrayList<>(Arrays.asList(new String[]{"GrassWhistle","Growl","Heal Bell", "Hyper Voice", "Metal Sound", "Perish Song", "Roar", "Screech", "Sing", "Snore", "Supersonic", "Uproar"}));
    interface ability{
        double genMultiplier(Pokemon attacker, Move move, Pokemon attacked,Context context);
    }
    static{
        attackerAbilities.put("Adaptability",(attacker, move, attacked, context) -> move.type.equals(attacker.base.types[0]) || move.type.equals(attacker.base.types[1]) ? 4.0/3.0 : 1);
        attackerAbilities.put("Aerilate", ((attacker,move,attacked, context) -> {
            if(move.type.equals("Normal")){
                move.power*=1.20;
                return 1.5;
            }
            return 1.0;
        }));
        attackerAbilities.put("Air Lock", ((attacker,move,attacked, context) -> 1/context.generateWeatherMultiplier(attacker,move,attacked)));
        attackerAbilities.put("Cloud Nine", ((attacker,move,attacked, context) -> 1/context.generateWeatherMultiplier(attacker,move,attacked)));
        attackerAbilities.put("Analytic",(attacker,move,attacked,context) -> attacker.stats[BasePokemon.Stats.SPE] > attacked.stats[BasePokemon.Stats.SPE] ? 1.3 : 1.0);
        attackerAbilities.put("Blaze",(attacker,move,attacked,context) -> {move.power*=1.5; return 1;});
        attackerAbilities.put("Dark Aura", (attacker,move,attacked,context) -> move.type.equals("Dark") ? 1.33 : 1);
        attackerAbilities.put("Defeatist", (attacker,move,attacked,context) -> {attacker.stats[Pokemon.Stats.ATK]/=2;attacker.stats[Pokemon.Stats.SPA]/=2;return 1;});
        attackerAbilities.put("Defiant", (attacker, move, attacked, context) -> {attacker.stats[Pokemon.Stats.ATK]*=1.5;return 1;});
        attackerAbilities.put("Delta Stream", (attacker, move, attacked, context) -> TypeAdvantages.isSuperEffective(attacked.base,move) ? 0.5 : 1.0); //cancels out the flying advantage
        attackerAbilities.put("Download", (attacker, move, attacked, context) -> {
            if(attacked.stats[Pokemon.Stats.DEF] > attacked.stats[Pokemon.Stats.SPD]){
                attacker.stats[Pokemon.Stats.SPA]*=1.50; //todo incorporate the state changes here
            } else {
                attacker.stats[Pokemon.Stats.ATK]*=1.50;
            }
            return 1;
        });
        attackerAbilities.put("Fairy Aura", (attacker,move,attacked,context) -> move.type.equals("Fairy") ? 1.33 : 1);
        attackerAbilities.put("Flare Boost", (attacker,move,attacked,context) -> {if(move.category.equals("Special")) move.power*=1.5; return 1;});
        attackerAbilities.put("Flash Fire", (attacker,move,attacked,context) -> {if(move.type.equals("Fire")) move.power*=1.5; return 1;});
        attackerAbilities.put("Flower Gift", (attacker, move, attacked, context) -> {if(context.weather.equals("Harsh Sunlight")) attacker.stats[Pokemon.Stats.ATK]*=1.5; return 1;});
        //todo Fluffy
        attackerAbilities.put("Galvanize", (attacker,move,attacked,context) -> move.type.equals("Normal") ? 1.5 : 1);
        attackerAbilities.put("Gorilla Tactics", (attacker, move, attacked, context) -> {attacker.stats[Pokemon.Stats.ATK]*=1.5; return 1.0;});
        attackerAbilities.put("Guts", (attacker, move, attacked, context) -> {attacker.stats[Pokemon.Stats.ATK]*=1.5; return 1;});
        attackerAbilities.put("Huge Power", (attacker,move,attacked,context) -> 2.0);
        attackerAbilities.put("Hustle", (attacker,move,attacked,context) -> 1.5);
        attackerAbilities.put("Intrepid Sword", (attacker, move, attacked, context) -> {attacker.stats[Pokemon.Stats.ATK]*=1.5;return 1;});
        attackerAbilities.put("Iron Fist", (attacker, move, attacked, context) -> {if(
                Arrays.asList(new String[]{"Bullet Punch", "Comet Punch", "Dizzy Punch", "Double Iron Bash", "Drain Punch", "Dynamic Punch", "Fire Punch", "Focus Punch", "Hammer Arm", "Ice Hammer", "Ice Punch", "Mach Punch", "Mega Punch", "Meteor Mash", "Plasma Fists", "Power-Up Punch", "Shadow Punch", "Sky Uppercut", "Thunder Punch"}).contains(move.name)
        ) move.power*=1.2; return 1;});
        attackerAbilities.put("Justified", (attacker, move, attacked, context) -> {attacker.stats[Pokemon.Stats.ATK]*=1.5; return 1;});
        attackerAbilities.put("Libero", (attacker, move, attacked, context) -> move.type.equals(attacker.types[0]) || move.type.equals(attacker.types[1]) ? 1 : 1.5);
        attackerAbilities.put("Lightning Rod", (attacker, move, attacked, context) -> {attacker.stats[Pokemon.Stats.SPA]*=1.5; return 1;});
        attackerAbilities.put("Mega Launcher", (attacker, move, attacked, context) -> {if(Arrays.asList(new String[]{"Aura Sphere","Dark Pulse", "Dragon Pulse", "Origin Pulse", "Terrain Pulse", "Water Pulse"}).contains(move.name)) move.power*=1.5; return 1;});
        attackerAbilities.put("Merciless", (attacker, move, attacked, context) -> {context.crit=true; return 1;});
        attackerAbilities.put("Minus", (attacker, move, attacked, context) -> {attacked.stats[Pokemon.Stats.SPA]*=1.5; return 1;});
        attackerAbilities.put("Plus", (attacker, move, attacked, context) -> {attacked.stats[Pokemon.Stats.SPA]*=1.5; return 1;});
        attackerAbilities.put("Normalize", ((attacker, move, attacked, context) -> move.type.equals("Normal") ? 1 : 1.5)); //automatically does stab, HORRIBLE HACK
        attackerAbilities.put("Overgrow",(attacker,move,attacked,context) -> {move.power*=1.5; return 1;});
        attackerAbilities.put("Parental Bond", (((attacker, move, attacked, context) -> 1.5)));
        attackerAbilities.put("Pixilate", ((attacker, move, attacked, context) -> {move.power*=1.2; return move.type.equals("Fairy") ? 1 : 1.5;})); //automatically does stab, HORRIBLE HACK
        attackerAbilities.put("Sand Force", ((attacker, move, attacked, context) -> {if(Arrays.asList(new String[]{"Rock", "Ground", "Steel"}).contains(move.type)) move.power*=1.3;return 1;}));
        attackerAbilities.put("Steelworker", ((attack, move, attacked, context) -> {if(move.type.equals("Steel")) move.power*=1.5; return 1;}));
        attackerAbilities.put("Swarm", ((attack, move, attacked, context) -> {if(move.type.equals("Bug")) move.power*=1.5; return 1;}));
        attackerAbilities.put("Torrent", ((attack, move, attacked, context) -> {if(move.type.equals("Water")) move.power*=1.5; return 1;}));
        attackerAbilities.put("Toxic Boost", ((attack, move, attacked, context) -> {move.power*=1.5; return 1;}));

        attackedAbilities.put("Aura Break", (attacker,move,attacked,context) -> move.type.equals("Fairy") || move.type.equals("Dark") ? 1/1.33 : 1);
        attackedAbilities.put("Battle Armor", (attacker,move,attacked,context) -> {context.crit=false; return 1;});
        attackedAbilities.put("Bulletproof",(attacker,move,attacked,context) -> moveBulletProofBlocks.contains(move.name) ? 0 : 1);
        attackedAbilities.put("Cacophony", (attacker,move,attacked,context) -> moveCacophonyBlocks.contains(move.name) ? 0 : 1);
        attackedAbilities.put("Air Lock", ((attacker,move,attacked, context) -> 1/context.generateWeatherMultiplier(attacker,move,attacked)));
        attackedAbilities.put("Cloud Nine", ((attacker,move,attacked, context) -> 1/context.generateWeatherMultiplier(attacker,move,attacked)));
        attackedAbilities.put("Damp", (attacker,move,attacked,context) -> Arrays.asList(new String[]{"Self-Destruct", "Explosion", "Mind Blown", "Misty Explosion"}).contains(move.name) ? 0 : 1);
        attackedAbilities.put("Dark Aura", (attacker,move,attacked,context) -> move.type.equals("Dark") ? 1.33 : 1);
        attackedAbilities.put("Dauntless Shield", (attacker,move,attacked,context) -> {attacked.stats[Pokemon.Stats.DEF]*=1.5; return 1;}); //todo, if we add stat changes just use those
        attackedAbilities.put("Delta Stream", (attacker, move, attacked, context) -> TypeAdvantages.isSuperEffective(attacked.base,move) ? 0.5 : 1.0); //cancels out the flying advantage
        attackedAbilities.put("Dry Skin", (attacker,move,attacked,context) -> move.type.equals("Fire") ? 1.25 : 1);
        attackedAbilities.put("Fairy Aura", (attacker,move,attacked,context) -> move.type.equals("Fairy") ? 1.33 : 1);
        attackedAbilities.put("Filter", (attacker,move,attacked,context) -> TypeAdvantages.isSuperEffective(attacked.base,move) ? 0.75 : 1);
        attackedAbilities.put("Flower Gift", (attacker, move, attacked, context) -> {if(context.weather.equals("Harsh Sunlight")) attacked.stats[Pokemon.Stats.SPD]*=1.5; return 1;});
        attackedAbilities.put("Grass Pelt", (attacker, move, attacked, context) -> {if(context.terrain.equals("Grassy Terrain")) attacked.stats[Pokemon.Stats.DEF]*=1.5; return 1;});
        attackedAbilities.put("Heatproof", (attacker,move,attacked,context) -> move.type.equals("Fire") ? 0.5 : 1);
        attackedAbilities.put("Ice Scales", (attacker, move, attacked, context) -> move.category.equals("Special") ? 0.5 : 1);
        attackedAbilities.put("Intimidate",(attacker, move, attacked, context) -> {attacker.stats[Pokemon.Stats.ATK]*=0.75; return 1;}); //todo STAT STAGES
        attackedAbilities.put("Levitate", (attacker, move, attacked, context) -> move.type.equals("Ground") ? 0 : 1);
        attackedAbilities.put("Prism Armor", (attacker,move,attacked,context) -> TypeAdvantages.isSuperEffective(attacked.base,move) ? 0.75 : 1);
        attackedAbilities.put("Thick Fat", (((attacker, move, attacked, context) -> move.type.equals("Fire") || move.type.equals("Ice") ? 0.5 : 1)));
        attackedAbilities.put("Water Bubble", (((attacker, move, attacked, context) -> {if(move.type.equals("Fire")) move.power/=2; return 1;})));
    }
    public static double genMultiplier(Pokemon attacker, Move move, Pokemon attacked, Context context){
        return attackedAbilities.getOrDefault(attacker.ability, ((a,b,c,d) -> 1)).genMultiplier(attacker,move,attacked,context) *
                attackerAbilities.getOrDefault(attacker.ability, ((a,b,c,d) -> 1)).genMultiplier(attacker,move,attacked,context);
    }
}
