package co.antoniolima.onlinechess;


public class Position {

    private int x;
    private int y;
    private boolean valid;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        this.valid = false;
    }

    public int getX() { return x; }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
