package co.antoniolima.onlinechess;

import android.app.Application;
import java.util.ArrayList;
import java.util.Arrays;

import static co.antoniolima.onlinechess.Constants.BLACK;
import static co.antoniolima.onlinechess.Constants.BOARD_SIZE;
import static co.antoniolima.onlinechess.Constants.DRAWABLE_EMPTY;
import static co.antoniolima.onlinechess.Constants.WHITE;

public class GameData extends Application{

    ArrayList<Piece> boardPieces;   // array with all game pieces
    int [] pieceImages;             // array with all 8x8 images to visually build the chess board
    Piece selectedPiece;            // piece selected currently, there can be only one

    @Override
    public void onCreate() {
        super.onCreate();

        this.pieceImages = new int[BOARD_SIZE];
        this.boardPieces = new ArrayList<>();
        this.selectedPiece = null;
        this.initGameData();
    }

    public ArrayList<Piece> getBoardPieces() {
        return boardPieces;
    }

    public boolean isThisPositionTaken(int p){

        for (Piece piece : boardPieces) {
            if (piece.getPosition() == p)
                return true;
        }
        return false;
    }

    public Piece getPieceByPosition(int p){

        for (Piece piece : boardPieces) {
            if (piece.getPosition() == p)
                return piece;
        }
        return null;
    }

    public void selectPiece(Piece piece){
        this.selectedPiece = piece;
    }

    public int[] getPieceImages() {
        return pieceImages;
    }

    public void initGameData(){

        // preenche array de images com images transparentes
        Arrays.fill(pieceImages, DRAWABLE_EMPTY);

        // preenche array de peças com todas as peças na posicao inicial
        this.boardPieces.add(new Rook(BLACK,    0));
        this.boardPieces.add(new Knight(BLACK,  1));
        this.boardPieces.add(new Bishop(BLACK,  2));
        this.boardPieces.add(new Queen(BLACK,   3));
        this.boardPieces.add(new King(BLACK,    4));
        this.boardPieces.add(new Bishop(BLACK,  5));
        this.boardPieces.add(new Knight(BLACK,  6));
        this.boardPieces.add(new Rook(BLACK,    7));
        this.boardPieces.add(new Pawn(BLACK, 8));
        this.boardPieces.add(new Pawn(BLACK, 9));
        this.boardPieces.add(new Pawn(BLACK, 10));
        this.boardPieces.add(new Pawn(BLACK, 11));
        this.boardPieces.add(new Pawn(BLACK, 12));
        this.boardPieces.add(new Pawn(BLACK, 13));
        this.boardPieces.add(new Pawn(BLACK, 14));
        this.boardPieces.add(new Pawn(BLACK, 15));

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