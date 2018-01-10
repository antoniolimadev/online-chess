package co.antoniolima.onlinechess;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class AccountFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_fragment, container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.account);
        ImageView imageview = (ImageView) view.findViewById(R.id.edit);
        imageview.setOnClickListener(this);

        TextView nametv = (TextView) view.findViewById(R.id.name);
        nametv.setText(((MainActivity)getActivity()).getUserName());

        TextView  emailtv = (TextView) view.findViewById(R.id.email);
        emailtv.setText("Email: " + ((MainActivity)getActivity()).getUserEmail());

        TextView birthdaytv = (TextView) view.findViewById(R.id.birthday);
        birthdaytv.setText("Birthday: " + ((MainActivity)getActivity()).getUserDate());

    }

    public void onClick(View view){
        editFunc();
    }

    public void editFunc(){
        Fragment fragment = null;
        fragment = new EditFragment();

        if(fragment != null){
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main,fragment);
            ft.addToBackStack(null);
            ft.commit();
        }
    }

}
