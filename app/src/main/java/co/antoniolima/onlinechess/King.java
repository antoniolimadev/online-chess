package co.antoniolima.onlinechess;

import static co.antoniolima.onlinechess.Constants.BOARD_WIDTH;
import static co.antoniolima.onlinechess.Constants.DRAWABLE_BLACK_PIECE_KING;
import static co.antoniolima.onlinechess.Constants.DRAWABLE_WHITE_PIECE_KING;

public class King extends Piece {

    private boolean hasMadeFirstMove;

    public King(boolean color, int position) {
        super(color, position);
        this.setIdWhiteImage(DRAWABLE_WHITE_PIECE_KING);
        this.setIdBlackImage(DRAWABLE_BLACK_PIECE_KING);
        this.hasMadeFirstMove = false;
        this.initTargetPositions();
    }

    @Override
    public void initTargetPositions(){
        this.resetTargetPositions();
        int pieceX = this.getPosition()%BOARD_WIDTH;
        int pieceY = this.getPosition()/BOARD_WIDTH;
        this.addTargetPosition(new Position(pieceX, pieceY-1));         // norte
        this.addTargetPosition(new Position(pieceX+1, pieceY-1));   // nordeste
        this.addTargetPosition(new Position(pieceX+1, pieceY));         // este
        this.addTargetPosition(new Position(pieceX+1, pieceY+1));   // sudeste
        this.addTargetPosition(new Position(pieceX, pieceY+1));         // sul
        this.addTargetPosition(new Position(pieceX-1, pieceY+1));   // sudoeste
        this.addTargetPosition(new Position(pieceX-1, pieceY));        // oeste
        this.addTargetPosition(new Position(pieceX-1, pieceY-1));   // nordoeste
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
        hasMadeFirstMove = true;
        gameController.resetHighlights();
        this.setPosition(p);
    }
}