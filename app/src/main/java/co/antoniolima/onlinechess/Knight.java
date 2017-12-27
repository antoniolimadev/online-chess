package co.antoniolima.onlinechess;

import static co.antoniolima.onlinechess.Constants.DRAWABLE_BLACK_PIECE_KNIGHT;
import static co.antoniolima.onlinechess.Constants.DRAWABLE_WHITE_PIECE_KNIGHT;
import static co.antoniolima.onlinechess.Constants.WHITE;

public class Knight extends Piece {

    public Knight(boolean color, int position) {
        super(color, position);
        this.setIdWhiteImage(DRAWABLE_WHITE_PIECE_KNIGHT);
        this.setIdBlackImage(DRAWABLE_BLACK_PIECE_KNIGHT);
        int [] targetPosition = new int [4];
        targetPosition[0] = -15;
        targetPosition[1] = -17;
        targetPosition[2] = 15;
        targetPosition[3] = 17;
        this.setTargetPositionArray(targetPosition);
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
    public boolean move() {
        return super.move();
    }
}
