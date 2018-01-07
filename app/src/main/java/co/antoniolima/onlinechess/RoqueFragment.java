package co.antoniolima.onlinechess;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RoqueFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RoqueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoqueFragment extends DialogFragment {

    GameController gameController;
    View view;
    //Button bt = view.findbyid(...)

    //@NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        LayoutInflater inflater = getActivity().getLayoutInflater();

        view = inflater.inflate(R.layout.fragment_roque, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view).setNeutralButton("OK", dialogClickListener);
        builder.setView(view).setNegativeButton("Cancelar", dialogClickListener);
        return builder.create();
    }


//    public static RoqueFragment newInstance(int arg, GameController gameController) {
//        RoqueFragment frag = new RoqueFragment();
//        Bundle args = new Bundle();
//        args.putInt("count", arg);
//        frag.setArguments(args);
//        frag.setComplexVariable(gameController);
//        return frag;
//    }

    public static RoqueFragment newInstance(GameController gameController) {
        RoqueFragment frag = new RoqueFragment();
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
                    gameController.executeRoque();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    // se o roque nao é executado, fica a torre selecionada
                    gameController.getSelectedRook().select(gameController);
                    break;
            }
        }
    };
}