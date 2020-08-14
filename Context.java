public class Context {
    public String weather;
    public String terrain;
    public boolean doubles, lightScreen, reflect, auroraVeil, crit;
    public int numHits;

    public double generateMultiplier(Pokemon attacker, Move move, Pokemon attacked) {
        double multiplier = 1;
        if(doubles) multiplier*=0.75;

        //handles STAB and power doubling
        if(!"No Terrain".equals(terrain) && move.name.equals("Terrain Pulse"))
            multiplier*=2.0*2.0*1.3;
        if("Electric Terrain".equals(terrain) && move.type.equals("Electric")){
            multiplier*=1.3;
            if(move.name.equals("Rising Voltage")) multiplier*=2.0;
        } else if("Psychic Terrain".equals(terrain) && move.type.equals("Psychic")){
            multiplier*=1.3;
            if(move.name.equals("Expanding Force")) multiplier*=1.2; //this might be the wrong multiplier
        } else if("Misty Terrain".equals(terrain) && move.type.equals("Water")){
            multiplier*=1.3;
            if(move.name.equals("Misty Explosion")) multiplier*=1.5;
        } else if("Grassy Terrain".equals(terrain) && move.type.equals("Grassy")){
            multiplier*=1.3; //grassy glide's priority increase doesn't matter here
        }
        if("Sunshine".equals(weather) && !attacker.item.equals("Utility Umbrella")){
            if(move.type.equals("Fire")){
                multiplier*=1.5;
            } else if(move.type.equals("Water")){
                multiplier*=2.0/3.0;
            }
        } else if("Harsh Sunshine".equals(weather) && !attacker.item.equals("Utility Umbrella")){
            if(move.type.equals("Fire")){
                multiplier*=1.5;
            } else if(move.type.equals("Water")){
                return 0;
            }
        } else if("Rain".equals(weather)){
            if(move.type.equals("Water")){
                multiplier*=1.5;
            } else if(move.type.equals("Fire")){
                multiplier*=2.0/3.0;
            }
        } else if("Heavy Rain".equals(weather)){
            if(move.type.equals("Water")){
                multiplier*=1.5;
            } else if(move.type.equals("Fire")){
                return 0;
            }
        } else if("Strong winds".equals(weather)){
            boolean isFlying = "Flying".equals(attacked.base.types[0]) || "Flying".equals(attacked.base.types[1]);
            if(isFlying && TypeAdvantages.isSuperEffective(attacked.base,move)){
                multiplier/=2.0;
            }
        }
        if(lightScreen && move.category.equals("Special")) multiplier/=2.0;
        if(reflect && move.category.equals("Physical")) multiplier/=2.0;
        if(auroraVeil){
            if(doubles) multiplier*=0.33;
            else multiplier*=0.5;
        }
        if(crit) multiplier*=1.5;
        multiplier*=numHits;


        return multiplier;
    }
}

