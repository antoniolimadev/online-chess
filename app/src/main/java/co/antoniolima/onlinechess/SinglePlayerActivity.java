package co.antoniolima.onlinechess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

import static co.antoniolima.onlinechess.Constants.BOARD_WIDTH;
import static co.antoniolima.onlinechess.Constants.ONLINE;
import static co.antoniolima.onlinechess.Constants.SERVER;

public class SinglePlayerActivity extends AppCompatActivity {

    GameController gameController;
    TextView whatAmI;
    GridView gridViewBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);

        // get gameController and gridView
        gameController = (GameController) getApplication();
        whatAmI = findViewById(R.id.textViewGameWhatAmI);
        gridViewBoard = findViewById(R.id.gridViewBoard);
        gridViewBoard.setNumColumns(BOARD_WIDTH);
        gameController.setContext(this);

        if (gameController.isWhat() == SERVER){
            whatAmI.setText("SERVER");
//            if (gameController.getReadClientMessage() != null){ // le a msg do cliente
//                whatAmI.setText(gameController.getReadClientMessage().getText());
//            }

        } else {
            whatAmI.setText("CLIENTE");
//            if (gameController.getReadServerMessage() != null){  // le a msg do servidor
//                whatAmI.setText(gameController.getReadServerMessage().getText());
//            }
        }

        GridAdapter gridAdapter = new GridAdapter(this, gameController);
        gridViewBoard.setAdapter(gridAdapter);
        gameController.setGridViewBoard(gridAdapter);
    }
}