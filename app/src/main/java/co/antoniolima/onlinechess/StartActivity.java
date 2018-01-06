package co.antoniolima.onlinechess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StartActivity extends AppCompatActivity {

    GameController gameController;
    EditText textBox;
    Button buttonSingleplayer;
    Button buttonLocalMultiplayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        gameController = (GameController) getApplication();
        //textBox = findViewById(R.id.editTextPlayerName);
        buttonSingleplayer = findViewById(R.id.btnSinglePlayer);
        buttonLocalMultiplayer= findViewById(R.id.btnMultiplayerLocal);

        buttonSingleplayer.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {

            //String texto = textBox.getText().toString();
            gameController.newSinglePlayerGame();

            Intent intent = new Intent(getApplicationContext(), SinglePlayerActivity.class);
            startActivity(intent);
            }
        });

        buttonLocalMultiplayer.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {

                gameController.newLocalMultiPlayerGame();

                Intent intent = new Intent(getApplicationContext(), SinglePlayerActivity.class);
                startActivity(intent);
            }
        });
    }
}
