package co.antoniolima.onlinechess;

import static co.antoniolima.onlinechess.Constants.BOARD_WIDTH;
import static co.antoniolima.onlinechess.Constants.DRAWABLE_BLACK_PIECE_KNIGHT;
import static co.antoniolima.onlinechess.Constants.DRAWABLE_WHITE_PIECE_KNIGHT;
import static co.antoniolima.onlinechess.Constants.WHITE;

public class Knight extends Piece {

    public Knight(boolean color, int position) {
        super(color, position);
        this.setIdWhiteImage(DRAWABLE_WHITE_PIECE_KNIGHT);
        this.setIdBlackImage(DRAWABLE_BLACK_PIECE_KNIGHT);
        //this.initTargetPositions();
    }

    @Override
    public void initTargetPositions(GameController gameController){
        this.resetTargetPositions();
        int pieceX = this.getPosition()%BOARD_WIDTH;
        int pieceY = this.getPosition()/BOARD_WIDTH;
        this.addTargetPosition(new Position(pieceX-1, pieceY-2)); // cima esquerda
        this.addTargetPosition(new Position(pieceX+1, pieceY-2)); // cima direita
        this.addTargetPosition(new Position(pieceX+2, pieceY-1)); // direita cima
        this.addTargetPosition(new Position(pieceX+2, pieceY+1)); // direita baixo
        this.addTargetPosition(new Position(pieceX+1, pieceY+2)); // baixo direita
        this.addTargetPosition(new Position(pieceX-1, pieceY+2)); // baixo esquerda
        this.addTargetPosition(new Position(pieceX-2, pieceY+1)); // esquerda baixo
        this.addTargetPosition(new Position(pieceX-2, pieceY-1)); // esquerda cima
    }

    @Override
    public  void calculateTargetPositions(GameController gameController){

        this.resetAvailablePositions();
        this.initTargetPositions(gameController);
        // cycle through targets array and check which positions are within the board
        for (Position p : this.getTargetPositionsArray()) {
            if (gameController.isThisPositionValid(p)){
                // se ta livre, é valida
                if (!gameController.isThisPositionTaken(gameController.getUniCoordinate(p))){
                    p.setValid(true);
                // se ta ocupada, vê a cor da peça
                } else {
                    // se é diferente, é valida
                    if (gameController.getPieceByPosition(gameController.getUniCoordinate(p)).getColor() != this.getColor()){
                        p.setValid(true);
                    }
                }
            }
        }
    }

    @Override
    public void findAvailablePositions(GameController gameController){
        this.resetAvailablePositions();

        // TODO: check if there are pieces in the way
    }

    @Override
    public void select(GameController gameController) {
        this.initTargetPositions(gameController);
        this.calculateTargetPositions(gameController);
        gameController.resetHighlights();
        // highlight itself
        gameController.highlightPosition(this.getPosition());
        // highlight valid positions
        for (Position p : this.getTargetPositionsArray()) {
            if (p.isValid()){
                gameController.highlightPosition(gameController.getUniCoordinate(p));
            }
        }
    }

    @Override
    public void move(GameController gameController, int p) {

        // verifica se p é uma posicao das validas
        for (Position pos : this.getTargetPositionsArray()) {
            if (gameController.getUniCoordinate(pos) == p){
                gameController.resetHighlights();
                this.setPosition(p);
                // se a peça é movida, deixa de estar selecionada
                gameController.setSelectedPiece(null);
                // passa o turno
                gameController.isKingInCheck();
                gameController.nextTurn();
            }
        }
    }
}