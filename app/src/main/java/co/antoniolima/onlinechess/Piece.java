package co.antoniolima.onlinechess;

public abstract class Piece {

    private int idWhiteImage;
    private int idBlackImage;
    private int position;
    private boolean color;
    private int x;
    private int y;
    private String name;

    public Piece(boolean color, int position) {
        this.color = color;
        this.position = position;
    }

    public int getIdWhiteImage() {
        return idWhiteImage;
    }

    public void setIdWhiteImage(int idWhiteImage) {
        this.idWhiteImage = idWhiteImage;
    }

    public int getIdBlackImage() {
        return idBlackImage;
    }

    public void setIdBlackImage(int idBlackImage) {
        this.idBlackImage = idBlackImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean getColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean move() {return true; }
}