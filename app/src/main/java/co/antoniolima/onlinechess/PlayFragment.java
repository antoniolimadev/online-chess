package co.antoniolima.onlinechess;

import android.os.Bundle;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;


public class PlayFragment extends Fragment {

    GameController gameController;
    EditText textBox;
    Button buttonSingleplayer;
    Button buttonLocalMultiplayer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.play_fragment, container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Play");

        gameController = (GameController) getActivity().getApplication();
        buttonSingleplayer = getActivity().findViewById(R.id.btnSinglePlayer);
        buttonLocalMultiplayer= getActivity().findViewById(R.id.btnMultiplayerLocal);

        buttonSingleplayer.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {

                //String texto = textBox.getText().toString();
                gameController.newSinglePlayerGame();

                Intent intent = new Intent(getActivity().getApplicationContext(), SinglePlayerActivity.class);
                startActivity(intent);
            }
        });

        buttonLocalMultiplayer.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {

                gameController.newLocalMultiPlayerGame();

                Intent intent = new Intent(getActivity().getApplicationContext(), SinglePlayerActivity.class);
                startActivity(intent);
            }
        });
    }

}
