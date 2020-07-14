import java.io.Serializable;

public class Move implements Serializable {
    public String name;
    public String type;

    public Move(String name, String type, String category, int power, int accuracy) {
        this.name = name;
        this.type = type;
        this.category = category;
        this.power = power;
        this.accuracy = accuracy;
    }

    public String category;
    public int power;
    public int accuracy;

    public String toString() {
        return this.name;
    }
}


/* Location:              /home/alex/IdeaProjects/2020july4pres/!/Move.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */