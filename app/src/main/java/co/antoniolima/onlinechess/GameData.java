package co.antoniolima.onlinechess;

import java.util.ArrayList;
import java.util.Arrays;

import static co.antoniolima.onlinechess.Constants.BLACK;
import static co.antoniolima.onlinechess.Constants.DRAWABLE_EMPTY;
import static co.antoniolima.onlinechess.Constants.WHITE;

public class GameData {

    ArrayList<Piece> boardPieces;   // array with all game pieces
    Piece selectedPiece;            // piece selected currently, there can be only one
    ArrayList<Player> players;      // player array
    int turn;
    King[] kingsArray;

    public GameData (){
        this.boardPieces = new ArrayList<>();
        this.selectedPiece = null;
        this.players = new ArrayList<>();
        this.turn = 0;
        this.initGameData();
        this.kingsArray = new King[2];
        this.setKingsArray();
    }

    public ArrayList<Player> getPlayers() { return players; }

    public void setPlayers(ArrayList<Player> players) { this.players = players; }

    public int getTurn() { return turn; }

    public void setTurn(int turn) { this.turn = turn; }

    public ArrayList<Piece> getBoardPieces() {
        return this.boardPieces;
    }

    public King[] getKingsArray() {
        return kingsArray;
    }

    public void setKingsArray() {
        int i = 0;
        for (Piece piece : this.getBoardPieces()){
            if(piece instanceof King){
                //kingsArray.add((King)piece);
                kingsArray[i] = (King) piece;
                i++;
            }
        }

    }

    //    public void sfdfetKingsArray() {
//        //procura as piece king e coloca no array
//        for (Piece piece : this.getBoardPieces()){
//            if(piece instanceof King){
//                kingsArray.add((King)piece);

//            }
//        }
//    }

    public Piece getSelectedPiece(){ return selectedPiece; }

    public void setSelectedPiece(Piece selectedPiece) { this.selectedPiece = selectedPiece; }

    public void deletePiece(Piece piece){ this.boardPieces.remove(piece); }

    public void initGameData(){

        // preenche array de peças com todas as peças na posicao inicial
        this.boardPieces.add(new Rook(BLACK,    0));
        this.boardPieces.add(new Knight(BLACK,  1));
        this.boardPieces.add(new Bishop(BLACK,  2));
        //this.boardPieces.add(new Queen(BLACK,   3));
        this.boardPieces.add(new King(BLACK,    4));
//        this.boardPieces.add(new Bishop(BLACK,  5));
//        this.boardPieces.add(new Knight(BLACK,  6));
//        this.boardPieces.add(new Rook(BLACK,    7));
//        this.boardPieces.add(new Pawn(BLACK, 8));
//        this.boardPieces.add(new Pawn(BLACK, 9));
//        this.boardPieces.add(new Pawn(BLACK, 10));
//        this.boardPieces.add(new Pawn(BLACK, 11));
//        this.boardPieces.add(new Pawn(BLACK, 12));
        this.boardPieces.add(new Pawn(WHITE, 13));
        this.boardPieces.add(new Pawn(WHITE, 14));
        this.boardPieces.add(new Pawn(WHITE, 15));

        this.boardPieces.add(new Rook(WHITE,    56));
        this.boardPieces.add(new Knight(WHITE,  57));
        this.boardPieces.add(new Bishop(WHITE,  58));
        this.boardPieces.add(new Queen(WHITE,   59));
        this.boardPieces.add(new King(WHITE,    60));
        this.boardPieces.add(new Bishop(WHITE,  61));
        this.boardPieces.add(new Knight(WHITE,  62));
        this.boardPieces.add(new Rook(WHITE,    63));
        this.boardPieces.add(new Pawn(WHITE, 48));
        this.boardPieces.add(new Pawn(WHITE, 49));
        this.boardPieces.add(new Pawn(WHITE, 50));
        this.boardPieces.add(new Pawn(WHITE, 51));
        this.boardPieces.add(new Pawn(WHITE, 52));
        this.boardPieces.add(new Pawn(WHITE, 53));
        this.boardPieces.add(new Pawn(WHITE, 54));
        this.boardPieces.add(new Pawn(WHITE, 55));
    }
}
