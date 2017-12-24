package co.antoniolima.onlinechess;

import static co.antoniolima.onlinechess.Constants.DRAWABLE_BLACK_PIECE_KNIGHT;
import static co.antoniolima.onlinechess.Constants.DRAWABLE_WHITE_PIECE_KNIGHT;

public class Knight extends Piece {

    public Knight(boolean color, int position) {
        super(color, position);
        this.setIdWhiteImage(DRAWABLE_WHITE_PIECE_KNIGHT);
        this.setIdBlackImage(DRAWABLE_BLACK_PIECE_KNIGHT);
    }

    @Override
    public boolean move() {
        return super.move();
    }
}
