package goodtogo.com.myquotes.fr;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import goodtogo.com.myquotes.R;

/**
 * Created by Sopheap on 6/9/2017.
 */
public class SadFragment extends Fragment {

    TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_text_fr, null);

        Bundle b = getArguments();
        String text = "Sad Fragment";
        // b.getString("KEY_STRING", "DEMO");

        textView = (TextView) view.findViewById(R.id.fragment_textview_textview);
        textView.setText(text);

        return view;
    }
}
