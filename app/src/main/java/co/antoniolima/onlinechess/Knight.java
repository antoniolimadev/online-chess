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
        this.initTargetPositions();
    }

    @Override
    public void initTargetPositions(){
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
        this.initTargetPositions();
        // cycle through targets array and check which positions are valid (within the board)
        for (Position p : this.getTargetPositionsArray()) {
            if (gameController.isThisPositionValid(p)){
                p.setValid(true);
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
        gameController.resetHighlights();
        this.setPosition(p);
    }
}