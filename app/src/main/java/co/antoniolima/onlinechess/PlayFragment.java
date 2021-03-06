package co.antoniolima.onlinechess;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import static co.antoniolima.onlinechess.Constants.BLACK;
import static co.antoniolima.onlinechess.Constants.CLIENT;
import static co.antoniolima.onlinechess.Constants.ONLINE;
import static co.antoniolima.onlinechess.Constants.SERVER;
import static co.antoniolima.onlinechess.Constants.WHITE;


public class PlayFragment extends Fragment {

    GameController gameController;
    EditText textBox;
    Button buttonSingleplayer;
    Button buttonLocalMultiplayer;
    Button buttonCreateOnlineGame;
    Button buttonJoinOnlineGame;

    Activity activity;
    FragmentManager fm;
    DiscardGameFragment discardGameFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.play_fragment, container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.play);

        gameController = (GameController) getActivity().getApplication();
        buttonSingleplayer = getActivity().findViewById(R.id.btnSinglePlayer);
        buttonLocalMultiplayer= getActivity().findViewById(R.id.btnMultiplayerLocal);
        buttonCreateOnlineGame = getActivity().findViewById(R.id.btnCreateGame);
        buttonJoinOnlineGame = getActivity().findViewById(R.id.btnJoinGame);

        activity = (Activity) view.getContext();

        fm = activity.getFragmentManager();
        discardGameFragment = new DiscardGameFragment();

        final DiscardGameFragment discardGameFragment2 = discardGameFragment.newInstance(gameController);

        buttonSingleplayer.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {

                // se ja esta um jogo a decorrer
                if (gameController.isGameRunning()){
                    discardGameFragment2.show(fm, "Discard Game Fragment");
                } else{
                    gameController.resetGameData();
                    gameController.initGameController();
                    gameController.newSinglePlayerGame();
                    gameController.setLocalMultiplayerGame(false);
                    Intent intent = new Intent(getActivity().getApplicationContext(), SinglePlayerActivity.class);
                    startActivity(intent);
                }
            }
        });

        buttonLocalMultiplayer.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {

                // se ja esta um jogo a decorrer
                if (gameController.isGameRunning()){
                    discardGameFragment2.show(fm, "Discard Game Fragment");
                } else{
                    gameController.resetGameData();
                    gameController.initGameController();
                    gameController.newLocalMultiPlayerGame();
                    gameController.setLocalMultiplayerGame(true);
                    Intent intent = new Intent(getActivity().getApplicationContext(), SinglePlayerActivity.class);
                    startActivity(intent);
                }
            }
        });

        buttonCreateOnlineGame.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {

                // se ja esta um jogo a decorrer
                if (gameController.isGameRunning()){
                    discardGameFragment2.show(fm, "Discard Game Fragment");
                } else {
                    gameController.resetGameData();
                    gameController.initGameController();
                    gameController.setWhat(SERVER);
                    gameController.newLocalMultiPlayerGame();
                    gameController.setOnlineStatus(ONLINE);
                    gameController.setItMyTurn(true);
                    gameController.setMyColor(WHITE);
                    Intent intent = new Intent(getActivity().getApplicationContext(), SetupOnlineActivity.class);
                    startActivity(intent);
                }
            }
        });

        buttonJoinOnlineGame.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {

                // se ja esta um jogo a decorrer
                if (gameController.isGameRunning()){
                    discardGameFragment2.show(fm, "Discard Game Fragment");
                } else {

                    gameController.resetGameData();
                    gameController.initGameController();
                    gameController.setWhat(CLIENT);
                    gameController.newLocalMultiPlayerGame();
                    gameController.setOnlineStatus(ONLINE);
                    gameController.setItMyTurn(false);
                    gameController.setMyColor(BLACK);
                    Intent intent = new Intent(getActivity().getApplicationContext(), SetupOnlineActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

}
