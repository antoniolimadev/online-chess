package co.antoniolima.onlinechess;

import static co.antoniolima.onlinechess.Constants.DRAWABLE_BLACK_PIECE_PAWN;
import static co.antoniolima.onlinechess.Constants.DRAWABLE_WHITE_PIECE_PAWN;
import static co.antoniolima.onlinechess.Constants.WHITE;

public class Pawn extends Piece {

    public Pawn(boolean color, int position) {
        super(color, position);
        this.setIdWhiteImage(DRAWABLE_WHITE_PIECE_PAWN);
        this.setIdBlackImage(DRAWABLE_BLACK_PIECE_PAWN);
    }

    @Override
    public void select(GameController gameController) {

        gameController.resetHighlights();
        gameController.highlightPosition(this.getPosition()); // select itself
        if (this.getColor() == WHITE){
            gameController.highlightPosition(this.getPosition()-8);
            gameController.highlightPosition(this.getPosition()-16);
        } else{
            gameController.highlightPosition(this.getPosition()+8);
            gameController.highlightPosition(this.getPosition()+16);
        }
    }

    @Override
    public boolean move() {
        return super.move();
    }
}
