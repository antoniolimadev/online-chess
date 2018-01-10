package co.antoniolima.onlinechess;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StatsFragment extends Fragment {

    TextView numOfWins, numOfLosses;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.stats_fragment, container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.stats);

        numOfWins = (TextView) getView().findViewById(R.id.numOfWins);
        int i = ((MainActivity)getActivity()).getNumOfWins();
        numOfWins.setText("WIN: " + Integer.toString(i));
        numOfLosses = (TextView) getView().findViewById(R.id.numOfLosses);
        int j = ((MainActivity)getActivity()).getNumOfLosses();
        numOfLosses.setText("LOST: " +  Integer.toString(j));
    }
}
