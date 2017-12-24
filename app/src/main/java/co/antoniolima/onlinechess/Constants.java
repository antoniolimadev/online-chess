package co.antoniolima.onlinechess;

public final class Constants {

    private Constants() {
        // restrict instantiation
    }

    public static final int BOARD_WIDTH = 8;
    public static final int BOARD_SIZE = BOARD_WIDTH * BOARD_WIDTH;
    public static final int BOARD_WIDTH_ARRAY = BOARD_WIDTH - 1;

    // board "black and white" colors
    public static final int WHITE_R = 255;
    public static final int WHITE_G = 225;
    public static final int WHITE_B = 127;
    public static final int BLACK_R = 183;
    public static final int BLACK_G = 99;
    public static final int BLACK_B = 31;

    // pieces id
    public static final int PIECE_ROOK = 100;
    public static final int PIECE_BISHOP = 101;
    public static final int PIECE_KNIGHT = 102;
    public static final int PIECE_KING = 103;
    public static final int PIECE_QUEEN = 104;
    public static final int PIECE_PAWN = 105;

    public static final boolean WHITE = true;
    public static final boolean BLACK = false;

    //images id
    public static final int DRAWABLE_WHITE_PIECE_ROOK = R.drawable.rook_white;
    public static final int DRAWABLE_WHITE_PIECE_BISHOP = R.drawable.bishop_white;
    public static final int DRAWABLE_WHITE_PIECE_KNIGHT = R.drawable.knight_white;
    public static final int DRAWABLE_WHITE_PIECE_KING = R.drawable.king_white;
    public static final int DRAWABLE_WHITE_PIECE_QUEEN = R.drawable.queen_white;
    public static final int DRAWABLE_WHITE_PIECE_PAWN = R.drawable.pawn_white;
    public static final int DRAWABLE_BLACK_PIECE_ROOK = R.drawable.rook_black;
    public static final int DRAWABLE_BLACK_PIECE_BISHOP = R.drawable.bishop_black;
    public static final int DRAWABLE_BLACK_PIECE_KNIGHT = R.drawable.knight_black;
    public static final int DRAWABLE_BLACK_PIECE_KING = R.drawable.king_black;
    public static final int DRAWABLE_BLACK_PIECE_QUEEN = R.drawable.queen_black;
    public static final int DRAWABLE_BLACK_PIECE_PAWN = R.drawable.pawn_black;
    public static final int DRAWABLE_EMPTY = R.drawable.empty;
}
