package co.antoniolima.onlinechess;

import android.app.Application;
import java.util.Arrays;

import static co.antoniolima.onlinechess.Constants.BOARD_SIZE;
import static co.antoniolima.onlinechess.Constants.DRAWABLE_EMPTY;
import static co.antoniolima.onlinechess.Constants.WHITE;

public class GameController extends Application{

    GameData gameData;
    int [] images;          // array with all 8x8 images to visually build the chess board
    boolean [] highlighted; // if highlighted[5] is true then position 5 is highlighted

    @Override
    public void onCreate() {
        super.onCreate();
        this.gameData = new GameData();
        this.images = new int[BOARD_SIZE];
        this.highlighted = new boolean[BOARD_SIZE];
        this.updateImages();
        this.resetHighlights();
    }

    public boolean isThisPositionValid(int p){
        if(p < 0 || p > BOARD_SIZE-1) {
            return false;
        }
        return true;
    }

    // if there's a piece on position p
    public boolean isThisPositionTaken(int p){

        for (Piece piece : this.gameData.getBoardPieces()) {
            if (piece.getPosition() == p)
                return true;
        }
        return false;
    }

    public Piece getPieceByPosition(int p){

        for (Piece piece : this.gameData.getBoardPieces()) {
            if (piece.getPosition() == p)
                return piece;
        }
        return null;
    }

    public Piece getSelectedPiece(){ return this.gameData.getSelectedPiece(); }

    public void selectPiece(int p){

        // se tou a carregar numa peça
        if(isThisPositionTaken(p)) {
            this.gameData.setSelectedPiece(getPieceByPosition(p));
            this.getSelectedPiece().select(this);
        }else{
            // se tou a carregar numa casa vazia e ta uma peça selecionada
            // move essa peça para essa casa
            if(this.gameData.getSelectedPiece() != null){
                this.getSelectedPiece().move(this, p);
                gameData.setSelectedPiece(null);// Jorge - A peca depois de ser movida deixa de estar selecionada
            }
        }
        this.updateImages();
    }

    public int[] getImages() {
        return images;
    }

    public int getImage(int i){ return images[i]; }

    public void updateImages(){

        //resets array
        Arrays.fill(this.images, DRAWABLE_EMPTY);

        for (Piece piece : this.gameData.getBoardPieces()) {
            if (piece.getColor() == WHITE) {
                this.images[piece.getPosition()] = piece.getIdWhiteImage();
            } else {
                this.images[piece.getPosition()] = piece.getIdBlackImage();
            }
        }
    }

    public void resetHighlights() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            highlighted[i] = false;
        }
    }

    public void highlightPosition(int p){ this.highlighted[p] = true; }

    public boolean getHighlightPosition(int p){ return this.highlighted[p]; }
}