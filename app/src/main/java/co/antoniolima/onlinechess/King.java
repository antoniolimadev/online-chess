package co.antoniolima.onlinechess;

import static co.antoniolima.onlinechess.Constants.DRAWABLE_BLACK_PIECE_KING;
import static co.antoniolima.onlinechess.Constants.DRAWABLE_WHITE_PIECE_KING;

public class King extends Piece {

    private boolean hasMadeFirstMove;

    public King(boolean color, int position) {
        super(color, position);
        this.setIdWhiteImage(DRAWABLE_WHITE_PIECE_KING);
        this.setIdBlackImage(DRAWABLE_BLACK_PIECE_KING);
        this.hasMadeFirstMove = false;
        int [] targetPositions = new int [8];
        targetPositions[0] = -9;
        targetPositions[1] = -8;
        targetPositions[2] = -7;
        targetPositions[3] = -1;
        targetPositions[4] = 1;
        targetPositions[5] = 7;
        targetPositions[6] = 8;
        targetPositions[7] = 9;
        this.setTargetPositionArray(targetPositions);
    }

    @Override
    public void findAvailablePositions(GameController gameController){
        this.resetAvailablePositions();

        // TODO: check if there are pieces in the way
    }

    @Override
    public void select(GameController gameController) {

        this.findAvailablePositions(gameController);
        gameController.resetHighlights();
        // select itself
        gameController.highlightPosition(this.getPosition());
        // cycle through targets array and check which positions are valid (within the board)
        for (int i = 0; i < this.getTargetPositionsArray().length; i++) {
            if(gameController.isThisPositionValid(this.getPosition()+this.getTargetPosition(i)))
                gameController.highlightPosition(this.getPosition()+this.getTargetPosition(i));
        }
    }
    @Override
    public void move(GameController gameController, int p) {
        hasMadeFirstMove = true;
        gameController.resetHighlights();
        this.setPosition(p);
    }
}
