package co.antoniolima.onlinechess;

import static co.antoniolima.onlinechess.Constants.DRAWABLE_BLACK_PIECE_KNIGHT;
import static co.antoniolima.onlinechess.Constants.DRAWABLE_WHITE_PIECE_KNIGHT;
import static co.antoniolima.onlinechess.Constants.WHITE;

public class Knight extends Piece {

    public Knight(boolean color, int position) {
        super(color, position);
        this.setIdWhiteImage(DRAWABLE_WHITE_PIECE_KNIGHT);
        this.setIdBlackImage(DRAWABLE_BLACK_PIECE_KNIGHT);
        int [] targetPositions = new int [8];
        targetPositions[0] = -17;
        targetPositions[1] = -15;
        targetPositions[2] = -10;
        targetPositions[3] = -6;
        targetPositions[4] = 6;
        targetPositions[5] = 10;
        targetPositions[6] = 15;
        targetPositions[7] = 17;
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
        gameController.resetHighlights();
        this.setPosition(p);
    }
}
