package co.antoniolima.onlinechess;

import static co.antoniolima.onlinechess.Constants.DRAWABLE_BLACK_PIECE_ROOK;
import static co.antoniolima.onlinechess.Constants.DRAWABLE_WHITE_PIECE_ROOK;

public class Rook extends Piece {

    private boolean hasMadeFirstMove;

    public Rook(boolean color, int position) {
        super(color, position);
        this.setIdWhiteImage(DRAWABLE_WHITE_PIECE_ROOK);
        this.setIdBlackImage(DRAWABLE_BLACK_PIECE_ROOK);
        this.hasMadeFirstMove = false;
    }

    @Override
    public void move(GameController gameController, int p) {
        hasMadeFirstMove = true;
        gameController.resetHighlights();
        this.setPosition(p);
    }
}
