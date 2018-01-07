package co.antoniolima.onlinechess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import java.util.ArrayList;

import static co.antoniolima.onlinechess.Constants.BOARD_WIDTH;

public class SinglePlayerActivity extends AppCompatActivity {

    GameController gameController;
    GridView gridViewBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);

        // get gameController and gridView
        gameController = (GameController) getApplication();
        gridViewBoard = findViewById(R.id.gridViewBoard);
        gridViewBoard.setNumColumns(BOARD_WIDTH);

        gameController.setContext(this);

        GridAdapter gridAdapter = new GridAdapter(this, gameController);
        gridViewBoard.setAdapter(gridAdapter);
    }
}