package co.antoniolima.onlinechess;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import static android.content.ContentValues.TAG;
import static co.antoniolima.onlinechess.Constants.BLACK;
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
    }

    public void nextTurn(){
        this.gameData.setTurn(this.gameData.getTurn() + 1);


        Log.i(TAG, "OLA " );
        //ve se existe algum KING em check
        this.isKingInCheck();

        if (this.getCurrentPlayer().isBot()){
            try {
                this.makeRandomMove();
                Log.i(TAG, "BOT is playing turn " + this.gameData.getTurn());
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.gameData.setTurn(this.gameData.getTurn() + 1);
        }


    }

    public void newSinglePlayerGame(){

        this.gameData.getPlayers().add(new Player(HUMAN, WHITE));
        this.gameData.getPlayers().add(new Player(BOT, BLACK));
    }

    public void newLocalMultiPlayerGame(){

        this.gameData.getPlayers().add(new Player(HUMAN, WHITE));
        this.gameData.getPlayers().add(new Player(HUMAN, BLACK));
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
        // se tou a clicar numa peça
        if(isThisPositionTaken(p)) {
            // peça clicada
            Piece clickedPiece = this.getPieceByPosition(p);
            // e ja tava uma selecionada
            if (this.getSelectedPiece() != null){
                // peça seleciona
                Piece selectedPiece = this.getSelectedPiece();
                // se forem de cores diferentes, tenta capturar (pode nao ser uma jogada valida)
                if (selectedPiece.getColor() != clickedPiece.getColor()){
                    selectedPiece.capture(this, clickedPiece); // JOGADA TERMINADA
                } else {
                    // se forem da mesma cor, seleciona a nova peça
                    this.gameData.setSelectedPiece(getPieceByPosition(p));
                    this.getSelectedPiece().select(this);
                }
            // e nao ta nenhuma selecionada previamente
            }else {
                // compara a cor para verificar se pode seleciona-la
                if (clickedPiece.getColor() == this.getCurrentPlayer().getColor()){
                    this.gameData.setSelectedPiece(getPieceByPosition(p));
                    this.getSelectedPiece().select(this);
                }
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

    public GameData getGameData() { return gameData; }

    public int randomNumberGenerator(int min, int max) {
        long seed1 = System.nanoTime();
        Random randomGenerator = new Random(seed1);

        int x = randomGenerator.nextInt((max - min) + 1) + min;
        return x;
    }

    public void makeRandomMove(){
        // peças que o bot pode mexer e têm pelo menos uma posicao livre
        ArrayList<Piece> botPieces = new ArrayList<>();
        ArrayList<Piece> availableBotPieces = new ArrayList<>();

        // isola as peças do bot
        for (Piece piece : this.gameData.getBoardPieces()) {
            if (piece.getColor() == this.getCurrentPlayer().getColor()) {
                botPieces.add(piece);
            }
        }

        for (Piece piece : botPieces) {
            piece.initTargetPositions(this);
            piece.calculateTargetPositions(this);

            if (piece.getColor() == this.getCurrentPlayer().getColor() &&
                    piece.anyTargetPositionAvailable()){                        // e se tem posicoes para mexer
                availableBotPieces.add(piece);
            }
        }

        // escolhe uma peça ao calhas
        int randPieceIndex = randomNumberGenerator(0, availableBotPieces.size()-1);
        Piece pieceToMove = availableBotPieces.get(randPieceIndex);

        boolean positionFound = false;
        int randPosIndex = 0;
        int tries = 0;

        // procura uma posicao valida ao calhas
        while (!positionFound){
            randPosIndex = randomNumberGenerator(0, pieceToMove.getTargetPositionsArray().size()-1);
            tries++;
            if(pieceToMove.getTargetPositionsArray().get(randPosIndex).isValid()){

                //pieceToMove.move(this, this.getUniCoordinate(pieceToMove.getTargetPositionsArray().get(randPosIndex)));
                pieceToMove.setPosition(this.getUniCoordinate(pieceToMove.getTargetPositionsArray().get(randPosIndex)));
                // se a peça é movida, deixa de estar selecionada
                this.setSelectedPiece(null);
                positionFound = true;
            }
        }
        this.updateImages();
    }

//    public void isKingInCheck(){
//
//        //percorre o array de kings. para cada king ve se a posicao do mesmo
//        //e'uma posicao valida para as pecas de outra cor
//        for(King king : this.gameData.getKingsArray()){//escolhe um king
//            for(Piece piece : this.gameData.getBoardPieces()){//escolhe peca
//                if(piece.getColor() != king.getColor()){//se a peca e'de cor diferente
//                    piece.initTargetPositions(this);//calcula targets para essa peca
//                    piece.calculateTargetPositions(this);
//                    for(Position p : piece.getTargetPositionsArray()){//percorre targets da peca
//                        if(getUniCoordinate(p) == king.getPosition()){//se o king esta nos targets
//                            king.setCheck(true);
//                            Log.i(TAG, "A KING is checked " + king.getPosition());
//                        }
//                    }
//                }
//            }
//        }
//    }

    public void isKingInCheck(){

        King k;
        ArrayList<Piece> enemyPiece = new ArrayList<>();
        Iterator<Piece> iter = enemyPiece.iterator();

        //seleciona king do jogador actual
        if(getCurrentPlayer().getColor() == (this.gameData.getKingsArray())[0].getColor()) {
            k=(this.gameData.getKingsArray())[0];
        }else {
            k=(this.gameData.getKingsArray())[1];
        }

        //isola pecas da cor diferente do king
        for (Piece p : this.gameData.getBoardPieces()){
            if(p.getColor() != k.getColor())
                enemyPiece.add(p);
        }


//        //gera targets de cada peça inimiga
//        for(Piece piece : enemyPiece){
//            piece.initTargetPositions(this);
//            piece.calculateTargetPositions(this);
//        }

        while(iter.hasNext()){
            Piece piece = iter.next();
            piece.initTargetPositions(this);
            piece.calculateTargetPositions(this);
        }

        //procura se o king e'um target de uma peca inimiga
        for(Piece p : enemyPiece){
            if(p.getTargetPositionsArray().contains(k)){
            k.setCheck(true);
            Log.i(TAG, "A KING is checked " + k.getPosition());
            }
        }

    }
}