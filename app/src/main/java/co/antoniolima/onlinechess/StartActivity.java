package co.antoniolima.onlinechess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StartActivity extends AppCompatActivity {

    EditText textBox;
    Button myButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        textBox = findViewById(R.id.editTextPlayerName);
        myButton = findViewById(R.id.btnNewGame);

        myButton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {

                String texto = textBox.getText().toString();
                //gameData.setNome(texto);

                Intent intent = new Intent(getApplicationContext(), SinglePlayerActivity.class);
                startActivity(intent);
            }
        });
    }
}
