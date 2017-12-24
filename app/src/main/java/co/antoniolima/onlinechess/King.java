package co.antoniolima.onlinechess;

import static co.antoniolima.onlinechess.Constants.DRAWABLE_BLACK_PIECE_KING;
import static co.antoniolima.onlinechess.Constants.DRAWABLE_WHITE_PIECE_KING;

public class King extends Piece {

    public King(boolean color, int position) {
        super(color, position);
        this.setIdWhiteImage(DRAWABLE_WHITE_PIECE_KING);
        this.setIdBlackImage(DRAWABLE_BLACK_PIECE_KING);
    }

    @Override
    public boolean move() {
        return super.move();
    }
}
