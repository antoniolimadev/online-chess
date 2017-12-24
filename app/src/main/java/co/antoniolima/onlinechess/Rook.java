package co.antoniolima.onlinechess;

import static co.antoniolima.onlinechess.Constants.DRAWABLE_BLACK_PIECE_ROOK;
import static co.antoniolima.onlinechess.Constants.DRAWABLE_WHITE_PIECE_ROOK;

public class Rook extends Piece {

    public Rook(boolean color, int position) {
        super(color, position);
        this.setIdWhiteImage(DRAWABLE_WHITE_PIECE_ROOK);
        this.setIdBlackImage(DRAWABLE_BLACK_PIECE_ROOK);
    }

    @Override
    public boolean move() {
        return super.move();
    }
}
