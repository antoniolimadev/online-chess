package co.antoniolima.onlinechess;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable{

    String  text;
    int position;
    ArrayList<Piece> boardPieces;

    public Message(String text) {

        this.text = text;
        this.position = 1337;
    }

    public Message(int position) { this.position = position; }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPosition() { return position; }

    public void setPosition(int position) { this.position = position; }

    public ArrayList<Piece> getBoardPieces() {
        return boardPieces;
    }

    public void setBoardPieces(ArrayList<Piece> boardPieces) {
        this.boardPieces = boardPieces;
    }
}
