package co.antoniolima.onlinechess;

import java.util.ArrayList;

import static co.antoniolima.onlinechess.Constants.BOARD_SIZE;
import static co.antoniolima.onlinechess.Constants.WHITE;

public abstract class Piece {

    private int idWhiteImage;
    private int idBlackImage;
    private int position;
    private boolean color;
    private int x;
    private int y;
    private boolean [] availablePositions;
    private ArrayList<Position> targetPositions;
    private boolean captured;
    private String name;

    public Piece(boolean color, int position) {
        this.color = color;
        this.position = position;
        this.availablePositions = new boolean[BOARD_SIZE];
        this.targetPositions = new ArrayList<>();
        this.resetAvailablePositions();
        this.captured = false;
    }

    public boolean isCaptured() { return captured; }

    public void setCaptured(boolean captured) { this.captured = captured; }

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

    public int getCurrentImageId(){

        if(this.color == WHITE){
            return this.idWhiteImage;
        }else{
            return this.idBlackImage;
        }
    }

    public ArrayList<Position> getTargetPositionsArray() {
        return targetPositions;
    }

    public void addTargetPosition(Position newPosition) {
        this.targetPositions.add(newPosition);
    }

    public boolean anyTargetPositionAvailable () {
        for (Position pos : this.targetPositions) {
            if (pos.isValid()){
                return true;
            }
        }
        return false;
    }

    //    public int[] getTargetPositionsArray() { return targetPositions; }
//
//    public int getTargetPosition(int p) { return targetPositions[p]; }
//
//    public void setTargetPositionArray(int[] targetPositions) { this.targetPositions = targetPositions; }

    public boolean getAvailablePosition(int p) { return availablePositions[p]; }

    public void setAvailablePosition(int p) { this.availablePositions[p] = true; }

    public void resetAvailablePositions(){
        for (int i = 0; i < BOARD_SIZE; i++) {
            availablePositions[i] = false;
        }
    }

    public void initTargetPositions(GameController gameController){ }

    public void calculateTargetPositions (GameController gameController) { }

    public void resetTargetPositions () { this.targetPositions.clear(); }

    public void findAvailablePositions(GameController gameController) { }

    public void select(GameController gameController) { }

    public void move(GameController gameController, int p) { }

    public void capture(GameController gameController, Piece piece) {

        for (Position pos : this.getTargetPositionsArray()) {
            if (gameController.getUniCoordinate(pos) == piece.getPosition()){
                gameController.resetHighlights();
                this.setPosition(piece.getPosition());
                gameController.deletePiece(piece);
                // se a peça é movida, deixa de estar selecionada
                gameController.setSelectedPiece(null);
                // passa o turno
                gameController.nextTurn();
            }
        }
    }
}