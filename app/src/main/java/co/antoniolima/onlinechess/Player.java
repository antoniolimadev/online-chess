package co.antoniolima.onlinechess;

import static co.antoniolima.onlinechess.Constants.BOT;

public class Player {

    String name;
    boolean color;
    boolean type;

    public Player(boolean type) {
        this.type = type;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public boolean isBot() {
        if (this.type == BOT){
            return true;
        }
        return false;
    }
}
