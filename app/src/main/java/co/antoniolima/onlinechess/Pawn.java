package co.antoniolima.onlinechess;

import static co.antoniolima.onlinechess.Constants.BOARD_SIZE;
import static co.antoniolima.onlinechess.Constants.DRAWABLE_BLACK_PIECE_PAWN;
import static co.antoniolima.onlinechess.Constants.DRAWABLE_WHITE_PIECE_PAWN;
import static co.antoniolima.onlinechess.Constants.WHITE;

public class Pawn extends Piece {

    private boolean hasMadeFirstMove;

    public Pawn(boolean color, int position) {
        super(color, position);
        this.setIdWhiteImage(DRAWABLE_WHITE_PIECE_PAWN);
        this.setIdBlackImage(DRAWABLE_BLACK_PIECE_PAWN);
        this.hasMadeFirstMove = false;
    }

    @Override
    public void findAvailablePositions(GameController gameController){
        this.resetAvailablePositions();

        // TODO: check if there are pieces in the way and Pawn direction
    }

    @Override
    public void select(GameController gameController) {

        gameController.resetHighlights();
        gameController.highlightPosition(this.getPosition()); // select itself

        for (int i = 0; i < BOARD_SIZE; i++) {
            if (this.getAvailablePosition(i) == true){
                gameController.highlightPosition(i);
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