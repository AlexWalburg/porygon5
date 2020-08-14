
import java.util.HashMap;

public class ItemEffects {
    private static HashMap<String, item> items = new HashMap();
    interface item{
        double genMultiplier(Pokemon attacker, Move move, Pokemon attacked, Context context);
    }
    static{
        items.put("Air Ballon",(attacker, move, attacked, context) -> move.type.equals("Ground") ? 0.0 : 1.0);
        //assault vest has to be handled elsewhere, i hate it but it's reality
        items.put("Expert Belt", (attacker, move, attacked, context) -> TypeAdvantages.isSuperEffective(attacked.base,move) ? 1.2 : 1.0);
        items.put("Life Orb", ((attacker, move, attacked, context) -> 5324.0/4096.9));
    }
}
