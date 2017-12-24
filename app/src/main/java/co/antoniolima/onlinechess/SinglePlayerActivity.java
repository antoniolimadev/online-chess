package co.antoniolima.onlinechess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import java.util.ArrayList;

import static co.antoniolima.onlinechess.Constants.BOARD_WIDTH;
import static co.antoniolima.onlinechess.Constants.WHITE;

public class SinglePlayerActivity extends AppCompatActivity {

    GameData gameData;
    GridView gridViewBoard;
    int [] images;
    ArrayList<Piece> boardPieces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);

        gameData = (GameData) getApplication();
        gridViewBoard = findViewById(R.id.gridViewBoard);
        gridViewBoard.setNumColumns(BOARD_WIDTH);

        images = gameData.getPieceImages();
        boardPieces = gameData.getBoardPieces();

        for (Piece piece : boardPieces) {
            if (piece.getColor() == WHITE) {
                images[piece.getPosition()] = piece.getIdWhiteImage();
            } else{
                images[piece.getPosition()] = piece.getIdBlackImage();
            }
        }
        GridAdapter gridAdapter = new GridAdapter(this, images);
        gridViewBoard.setAdapter(gridAdapter);
    }
}