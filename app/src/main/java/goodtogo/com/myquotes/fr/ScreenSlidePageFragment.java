package goodtogo.com.myquotes.fr;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import goodtogo.com.myquotes.R;

public class ScreenSlidePageFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);

        Bundle b = getArguments();

        TextView tv = (TextView) rootView.findViewById(R.id.text_show);
        tv.setText(b.getString("KEY_STRING", "Error"));

        return rootView;
    }
}


