package co.antoniolima.onlinechess;

import android.app.Application;
import android.util.Log;
import java.util.Arrays;

import static android.content.ContentValues.TAG;
import static co.antoniolima.onlinechess.Constants.BOARD_SIZE;
import static co.antoniolima.onlinechess.Constants.BOARD_WIDTH;
import static co.antoniolima.onlinechess.Constants.BOT;
import static co.antoniolima.onlinechess.Constants.DRAWABLE_EMPTY;
import static co.antoniolima.onlinechess.Constants.HUMAN;
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
        // TODO: no futuro esta funcao deve ser chamada quando o utilizador
        // TODO: selecionar single player no menu principal
        this.newSinglePlayerGame();
    }

    public void nextTurn(){
        this.gameData.setTurn(this.gameData.getTurn() + 1);

        if (this.getCurrentPlayer().isBot()){
            // TODO: jogada random do bot, escolher e mover/capturar, e actualizar as imagens da gridView
            Log.i(TAG, "BOT is playing turn " + this.gameData.getTurn());
            this.gameData.setTurn(this.gameData.getTurn() + 1);
        }
    }

    public void newSinglePlayerGame(){

        this.gameData.getPlayers().add(new Player(HUMAN));
        this.gameData.getPlayers().add(new Player(BOT));
    }

    public Player getCurrentPlayer(){
        int playerIndex = this.gameData.getTurn() % this.gameData.getPlayers().size();
        return this.gameData.getPlayers().get(playerIndex);
    }

    public boolean isThisPositionValid(Position p){

        if (p.getX() < 0 || p.getX() >= BOARD_WIDTH
            || p.getY() < 0 || p.getY() >= BOARD_WIDTH) {
            return false;
        }
        return true;
    }

    public int getUniCoordinate(Position p){
        return p.getY() * BOARD_WIDTH + p.getX();
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

    public void selectPosition(int p){



        // se tou a carregar numa peça
        if(isThisPositionTaken(p)) {
            if (this.getSelectedPiece() != null){
                Piece selectedPiece = this.getSelectedPiece();
                Piece pieceToCapture = this.getPieceByPosition(p);
                if (selectedPiece.getColor() != pieceToCapture.getColor()){
                    selectedPiece.capture(this, pieceToCapture); // JOGADA TERMINADA
                } else {
                    this.gameData.setSelectedPiece(getPieceByPosition(p));
                    this.getSelectedPiece().select(this);
                }
            }else {
                this.gameData.setSelectedPiece(getPieceByPosition(p));
                this.getSelectedPiece().select(this);
            }

        }else{
            // se tou a carregar numa casa vazia e ta uma peça selecionada
            // move essa peça para essa casa
            if(this.gameData.getSelectedPiece() != null){
                this.getSelectedPiece().move(this, p); // JOGADA TERMINADA
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
            if (!piece.isCaptured()) {
                if (piece.getColor() == WHITE) {
                    this.images[piece.getPosition()] = piece.getIdWhiteImage();
                } else {
                    this.images[piece.getPosition()] = piece.getIdBlackImage();
                }
            }
        }
    }

    public void resetHighlights() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            highlighted[i] = false;
        }
    }

    public void deletePiece(Piece piece){ this.gameData.deletePiece(piece); }

    public void setSelectedPiece(Piece piece) { this.gameData.setSelectedPiece(piece); }

    public void highlightPosition(int p){ this.highlighted[p] = true; }

    public boolean getHighlightPosition(int p){ return this.highlighted[p]; }
}