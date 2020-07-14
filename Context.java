public class Context {
    public String weather;
    public double generateMultiplier(Pokemon attacker, Move move, Pokemon attacked) {
        double multiplier = 1;

        //weathers
        //todo when we add items, teh utility umbrella should come here
        if("Sunshine".equals(weather)){
            if(move.type.equals("Fire")){
                multiplier*=1.5;
            } else if(move.type.equals("Water")){
                multiplier*=2.0/3.0;
            }
        } else if("Harsh Sunshine".equals(weather)){
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
        }

        return multiplier;
    }
}

