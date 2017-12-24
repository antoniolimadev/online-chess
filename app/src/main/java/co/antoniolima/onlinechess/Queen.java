package co.antoniolima.onlinechess;

import static co.antoniolima.onlinechess.Constants.DRAWABLE_BLACK_PIECE_QUEEN;
import static co.antoniolima.onlinechess.Constants.DRAWABLE_WHITE_PIECE_QUEEN;

public class Queen extends Piece {

    public Queen(boolean color, int position) {
        super(color, position);
        this.setIdWhiteImage(DRAWABLE_WHITE_PIECE_QUEEN);
        this.setIdBlackImage(DRAWABLE_BLACK_PIECE_QUEEN);
    }

    @Override
    public boolean move() {
        return super.move();
    }
}
