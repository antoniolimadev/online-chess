package co.antoniolima.onlinechess;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import static co.antoniolima.onlinechess.Constants.BLACK;
import static co.antoniolima.onlinechess.Constants.CLIENT;
import static co.antoniolima.onlinechess.Constants.ONLINE;
import static co.antoniolima.onlinechess.Constants.SERVER;
import static co.antoniolima.onlinechess.Constants.WHITE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RoqueFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RoqueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiscardGameFragment extends DialogFragment {

    GameController gameController;
    View view;
    Intent intent;
    //Button bt = view.findbyid(...)

    //@NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        LayoutInflater inflater = getActivity().getLayoutInflater();

        view = inflater.inflate(R.layout.fragment_discard_game, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view).setNeutralButton("Continuar Jogo", dialogClickListener);
        builder.setView(view).setNegativeButton("Novo Jogo", dialogClickListener);
        return builder.create();
    }

    public static DiscardGameFragment newInstance(GameController gameController) {
        DiscardGameFragment frag = new DiscardGameFragment();
        frag.setComplexVariable(gameController);
        return frag;
    }

    public void setComplexVariable(GameController gameController) {
        this.gameController = gameController;
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            switch (which) {

                case DialogInterface.BUTTON_NEUTRAL:
                    intent = new Intent(getActivity().getApplicationContext(), SinglePlayerActivity.class);
                    startActivity(intent);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:

                    if (gameController.getOnlineStatus() != ONLINE) {
                        // se o jogo a decorrer era single player, faz reset de acordo
                        if (!gameController.isLocalMultiplayerGame()) {
                            gameController.resetGameData();
                            gameController.initGameController();
                            gameController.newSinglePlayerGame();
                            gameController.setLocalMultiplayerGame(false);
                            intent = new Intent(getActivity().getApplicationContext(), SinglePlayerActivity.class);
                            startActivity(intent);
                        } else {
                            gameController.resetGameData();
                            gameController.initGameController();
                            gameController.newLocalMultiPlayerGame();
                            gameController.setLocalMultiplayerGame(true);
                            intent = new Intent(getActivity().getApplicationContext(), SinglePlayerActivity.class);
                            startActivity(intent);
                        }
                        // se era um jogo online
                    } else{
                        if (gameController.isWhat() == SERVER){

                            gameController.resetGameData();
                            gameController.initGameController();
                            gameController.setWhat(SERVER);
                            gameController.newLocalMultiPlayerGame();
                            gameController.setOnlineStatus(ONLINE);
                            gameController.setItMyTurn(true);
                            gameController.setMyColor(WHITE);
                            Intent intent = new Intent(getActivity().getApplicationContext(), SetupOnlineActivity.class);
                            startActivity(intent);
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



                    break;
            }
        }
    };
}