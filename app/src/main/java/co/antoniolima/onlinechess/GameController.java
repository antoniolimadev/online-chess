package co.antoniolima.onlinechess;

import android.app.Activity;
import android.app.Application;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Random;

import static android.content.ContentValues.TAG;
import static co.antoniolima.onlinechess.Constants.BLACK;
import static co.antoniolima.onlinechess.Constants.BOARD_SIZE;
import static co.antoniolima.onlinechess.Constants.BOARD_WIDTH;
import static co.antoniolima.onlinechess.Constants.BOT;
import static co.antoniolima.onlinechess.Constants.DRAWABLE_EMPTY;
import static co.antoniolima.onlinechess.Constants.HUMAN;
import static co.antoniolima.onlinechess.Constants.OFFLINE;
import static co.antoniolima.onlinechess.Constants.SERVER;
import static co.antoniolima.onlinechess.Constants.WHITE;

public class GameController extends Application{

    private GameData gameData;
    private int [] images;          // array with all 8x8 images to visually build the chess board
    private boolean [] highlighted; // if highlighted[5] is true then position 5 is highlighted
    private King [] kingsArray;    // pointers to both Kings
    private Context context;
    private GridAdapter gridViewBoard;
    private Piece selectedRook;      // ponteiro para a Torre usada no roque
    // ONLINE
    private boolean what;
    private boolean onlineStatus;
    private ObjectInputStream input; // recebe informacao
    private ObjectOutputStream output; // envia informacao

    private Handler procMsg;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private Socket socketGame;
    private int test;
    private Message readClientMessage;
    private Message readServerMessage;

    @Override
    public void onCreate() {
        super.onCreate();
        this.gameData = new GameData();
        this.images = new int[BOARD_SIZE];
        this.highlighted = new boolean[BOARD_SIZE];
        this.updateImages();
        this.resetHighlights();
        this.kingsArray = new King[2];
        this.setKingsArray();
        this.selectedRook = null;
        // ONLINE
        this.onlineStatus = OFFLINE;
        this.what = SERVER;
        this.readClientMessage = null;
        this.readServerMessage = null;
        this.procMsg = null;
        this.serverSocket = null;
        this.clientSocket = null;
        this.socketGame = null;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setGridViewBoard(GridAdapter gridViewBoard) { this.gridViewBoard = gridViewBoard; }

    public void dialogRoque(){
        Activity activity = (Activity) context;
        FragmentManager fm;
        RoqueFragment roqueFragment;

        fm = activity.getFragmentManager();
        roqueFragment = new RoqueFragment();

        RoqueFragment roqueFragment2 = roqueFragment.newInstance(this);

        roqueFragment2.show(fm, "Sample Fragment");
    }

    public void setKingsArray(){
        int i = 0;
        for (Piece piece : this.gameData.getBoardPieces()) {
            if(piece instanceof King){
                kingsArray[i] = (King) piece;
                i++;
            }
        }
    }

    public void nextTurn(){

        if(this.isGameOver()){
            // TODO: end game properly
            return;
        }
        this.checkPawnPromotion();

        this.gameData.setTurn(this.gameData.getTurn() + 1);
        Log.i(TAG, "TURNO "+this.gameData.getTurn() );
        //Log.i(TAG, "king 0" + (this.gameData.getKingsArray())[0].isCheck());
        //Log.i(TAG, "king 1" + (this.gameData.getKingsArray())[1].isCheck());

        if (this.getCurrentPlayer().isBot()){
            try {
                this.makeRandomMove();
                Log.i(TAG, "BOT is playing turn " + this.gameData.getTurn());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isGameOver(){
        for (int i = 0; i < this.kingsArray.length; i++) {
            if (this.kingsArray[i].isCheckMate()){
                return true;
            }
        }
        return false;
    }

    public void checkPawnPromotion(){

        ArrayList<Piece> pawns = new ArrayList<>();

        // get all pawns
        for (Piece piece : this.gameData.getBoardPieces()) {
            if (piece instanceof Pawn) {
                pawns.add(piece);
            }
        }
        // check if there's any Pawn on the first or last line of the board and return it
        for (Piece pawn : pawns) {
            if (pawn.getPosition() < BOARD_WIDTH ||
                    pawn.getPosition() > (BOARD_SIZE - BOARD_WIDTH - 1)){
                this.promotePawn(pawn);
            }
        }
    }

    public void promotePawn(Piece pawn){

        int pawnPosition = pawn.getPosition();
        boolean pawnColor = pawn.getColor();

        this.deletePiece(pawn);
        this.gameData.getBoardPieces().add(new Queen(pawnColor, pawnPosition));

        this.updateImages();
    }

    public boolean isRoqueValid(Piece king, Piece rook){

        if (!((King) king).hasMadeFirstMove() &&
            !((Rook) rook).hasMadeFirstMove()){
            this.selectedRook = rook;
            // check if cells between them are empty
            if (rook.getPosition() > king.getPosition()) {  // Torre à direita

                //verifica as 2 posicoes à direita do rei
                if (!isThisPositionTaken(king.getPosition() + 1) &&
                    !isThisPositionTaken(king.getPosition() + 2)){
                    return true;
                }
            } else {                                        // Torre à esquerda
                //verifica as 3 posicoes à direita do rei
                if (!isThisPositionTaken(king.getPosition() - 1) &&
                    !isThisPositionTaken(king.getPosition() - 2) &&
                    !isThisPositionTaken(king.getPosition() - 3)){
                    return true;
                }
            }
        }
        return false;
    }

    public void executeRoque(){

        Piece king = this.gameData.getSelectedPiece();
        Piece rook = this.selectedRook;

        // check side
        if (rook.getPosition() > king.getPosition()){ // Torre à direita
            king.setPosition(king.getPosition() + 2); // move rei duas casas para a direita
            rook.setPosition(rook.getPosition() - 2); // move torre duas casas para a esquerda
        }else{ // Torre à esquerda
            king.setPosition(king.getPosition() - 2); // move rei duas casas para a esquerda
            rook.setPosition(rook.getPosition() + 3); // move torre tres casas para a direita
        }
        ((King) king).setHasMadeFirstMove(true);
        ((Rook) rook).setHasMadeFirstMove(true);

        // reset aos ponteiros
        this.setSelectedPiece(null);
        this.selectedRook = null;
        this.resetHighlights();
        this.updateImages();
        this.nextTurn();
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
                }
                // se forem da mesma cor
                else {
                    // check Roque
                    if (selectedPiece instanceof King &&
                        clickedPiece instanceof Rook){

                        if (this.isRoqueValid(selectedPiece, clickedPiece)){
                            this.dialogRoque();
                        }else {
                            // se o roque ja nao é possivel, simplesmente seleciona a Torre
                            this.gameData.setSelectedPiece(getPieceByPosition(p));
                            this.getSelectedPiece().select(this);
                        }

                    } else {
                        // seleciona a nova peça
                        this.gameData.setSelectedPiece(getPieceByPosition(p));
                        this.getSelectedPiece().select(this);
                    }
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

    public Piece getSelectedRook() { return this.selectedRook; }

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

        // procura uma posicao valida ao calhas
        while (!positionFound){
            randPosIndex = randomNumberGenerator(0, pieceToMove.getTargetPositionsArray().size()-1);
            if(pieceToMove.getTargetPositionsArray().get(randPosIndex).isValid()){

                //target position coordinate
                int targetPositionCoordinate = this.getUniCoordinate(pieceToMove.getTargetPositionsArray().get(randPosIndex));

                // if there's a piece on target
                if (this.getPieceByPosition(targetPositionCoordinate) != null){
                    // capture it
                    pieceToMove.capture(this, this.getPieceByPosition(targetPositionCoordinate)); // JOGADA TERMINADA
                    // o metodo capture ja avança um turno, nao é preciso chamar this.nextTurn()
                }
                else{
                    // otherwise just move it
                    pieceToMove.setPosition(targetPositionCoordinate);  // JOGADA TERMINADA
                    this.nextTurn();
                }
                // se a peça é movida, deixa de estar selecionada
                this.setSelectedPiece(null);
                positionFound = true;
            }
        }
        this.updateImages();
    }

    public boolean isKingInCheck(){

        King k;
        ArrayList<Piece> enemyPiece = new ArrayList<>();

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

        Iterator<Piece> iter = enemyPiece.iterator();
        while(iter.hasNext()){
            Piece piece = iter.next();
            piece.initTargetPositions(this);
            piece.calculateTargetPositions(this);
        }

        iter = enemyPiece.iterator();
        while(iter.hasNext()){
            for(Position pos : iter.next().getTargetPositionsArray()){
                if(this.getUniCoordinate(pos) == k.getPosition()){
                    k.setCheck(true);
                    Log.i(TAG, "A KING is checked " + k.getPosition());
                    return true;
                }
            }
        }
        k.setCheck(false);
        return false;
    }

    public void updateBoard() {

        this.gridViewBoard.notifyDataSetChanged();
    }
    //////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////// ONLINE /////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////

    public boolean isWhat() {
        return what;
    }

    public void setWhat(boolean what) {
        this.what = what;
    }

    public Message getReadClientMessage() { return readClientMessage; }

    public Message getReadServerMessage() { return readServerMessage; }

    public void setReadServerMessage(Message readServerMessage) { this.readServerMessage = readServerMessage; }

    public void setReadClientMessage(Message readClientMessage) { this.readClientMessage = readClientMessage; }

    public void runServer() {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(8899);
                    socketGame = serverSocket.accept();

                    commThreadServer.start();
//                    Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                    socketGame = null;
                }
//                procMsg.post(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        if (socketGame == null) {
//
//                            procMsg.post(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    startActivity(intent);
//                                }
//                            });
//                            return;
//                        }
//                    }
//                });
            }
        });
        t.start();
    }

    Thread commThreadServer = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                input = new ObjectInputStream(socketGame.getInputStream());
                output = new ObjectOutputStream(socketGame.getOutputStream());

                //recebe info do cliente
                readClientMessage = (Message) input.readObject();

                output.writeObject(new Message("Ola do Servidor"));
                output.flush();

                Intent intent = new Intent(getApplicationContext(), SinglePlayerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                //envia info para cliente
//                Message resposta = new Message("Recebi " + readClient.getText());
//                output.writeObject(resposta);
                output.flush();

//                try {
//                    procMsg.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            //AQUI QUE INICIO O TABULEIRO
//                            Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(intent);
//                        }
//                    });
//                } catch (Exception e2) {
//                    procMsg.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getApplicationContext(), "Fim do Jogo", Toast.LENGTH_LONG).show();
//                        }
//                    });
//                }


//                while (!Thread.currentThread().isInterrupted()) {
//                    final GameInformation readClientMoves = (GameInformation) input.readObject();
//                    setChanges(readClientMoves);
//
//                    procMsg.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            //TODO: logica
//
//
//
//                            MultiplayerOnlineBoardActivity.updateBoard();
//                        }
//                    });
//                }
            } catch (final Exception e) {
                procMsg.post(new Runnable() {
                    @Override

                    public void run() {
                        Log.e("MYAPP", "exception", e);
                    }
                });
            }
        }
    });

    public void runClient(final String strIP, final int Port) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d("RPS", "Connecting to the server  " + strIP);
                    socketGame = new Socket(strIP, Port);
                } catch (Exception e) {
                    socketGame = null;
                }
                if (socketGame == null) {
//                    procMsg.post(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(intent);
//                        }
//                    });
//                    return;
                }
                commThreadClient.start();
            }
        });
        t.start();
    }



    Thread commThreadClient = new Thread(new Runnable() {

        @Override
        public void run() {
            try {
                output = new ObjectOutputStream(socketGame.getOutputStream());
                input = new ObjectInputStream(socketGame.getInputStream());

                //Envia a info do cliente
                output.writeObject(new Message("Ola do Cliente"));
                output.flush();
                //recebe info do servidor
                readServerMessage = (Message) input.readObject();

                Intent intent = new Intent(getApplicationContext(), SinglePlayerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

//                try {
//                    procMsg.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Intent intent = new Intent(getApplicationContext(), SinglePlayerActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(intent);
//                        }
//                    });
//                } catch (Exception e2) {
//                    procMsg.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getApplicationContext(), "Fim do Jogo", Toast.LENGTH_LONG).show();
//                        }
//                    });
//                }


//                while (!Thread.currentThread().isInterrupted()) {
//                    final GameInformation readServerMoves = (GameInformation) input.readObject();
//                    setChanges(readServerMoves);
//
//                    procMsg.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            //TODO:logica
//
//                            MultiplayerOnlineBoardActivity.updateBoard();
//                        }
//                    });
//                }
            } catch (final Exception e) {
                procMsg.post(new Runnable() {
                    @Override

                    public void run() {
                        Log.e("MYAPP", "exception", e);
                    }
                });
            }
        }
    });

    @Nullable
    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                            && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}