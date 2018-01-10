package co.antoniolima.onlinechess;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import java.util.Locale;

public class HelpFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.help_fragment, container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.help);

        WebView webView = (WebView) view.findViewById(R.id.webView);

        if((Locale.getDefault().getLanguage()).equals("en") ){
            webView.loadUrl("https://en.m.wikipedia.org/w/index.php?title=Rules_of_chess");
        }else {
            webView.loadUrl("https://pt.m.wikipedia.org/wiki/Leis_do_xadrez");

        }

    }
}
