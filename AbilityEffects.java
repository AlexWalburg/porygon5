import java.util.HashMap;
import java.util.function.Function;

public class AbilityEffects {
    private static HashMap<String, ability> abilities = new HashMap();
    interface ability{
        double genMultiplier(Pokemon attacker, Move move, Pokemon attacked);
    }
    static{
        abilities.put("Test",(attacker, move, attacked) -> 1);
    }
}
