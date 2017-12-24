package co.antoniolima.onlinechess;

import static co.antoniolima.onlinechess.Constants.DRAWABLE_BLACK_PIECE_BISHOP;
import static co.antoniolima.onlinechess.Constants.DRAWABLE_WHITE_PIECE_BISHOP;

public class Bishop extends Piece {

    public Bishop(boolean color, int position) {
        super(color, position);
        this.setIdWhiteImage(DRAWABLE_WHITE_PIECE_BISHOP);
        this.setIdBlackImage(DRAWABLE_BLACK_PIECE_BISHOP);
    }

    @Override
    public boolean move() {
        return super.move();
    }
}
