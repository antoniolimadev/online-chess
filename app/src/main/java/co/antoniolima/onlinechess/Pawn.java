package co.antoniolima.onlinechess;

import static co.antoniolima.onlinechess.Constants.DRAWABLE_BLACK_PIECE_PAWN;
import static co.antoniolima.onlinechess.Constants.DRAWABLE_WHITE_PIECE_PAWN;

public class Pawn extends Piece {

    public Pawn(boolean color, int position) {
        super(color, position);
        this.setIdWhiteImage(DRAWABLE_WHITE_PIECE_PAWN);
        this.setIdBlackImage(DRAWABLE_BLACK_PIECE_PAWN);
    }

    @Override
    public boolean move() {
        return super.move();
    }
}
