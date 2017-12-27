package co.antoniolima.onlinechess;

import static co.antoniolima.onlinechess.Constants.DRAWABLE_BLACK_PIECE_KING;
import static co.antoniolima.onlinechess.Constants.DRAWABLE_WHITE_PIECE_KING;

public class King extends Piece {

    private boolean hasMadeFirstMove;

    public King(boolean color, int position) {
        super(color, position);
        this.setIdWhiteImage(DRAWABLE_WHITE_PIECE_KING);
        this.setIdBlackImage(DRAWABLE_BLACK_PIECE_KING);
        this.hasMadeFirstMove = false;
    }

    @Override
    public void move(GameController gameController, int p) {
        hasMadeFirstMove = true;
        gameController.resetHighlights();
        this.setPosition(p);
    }
}
