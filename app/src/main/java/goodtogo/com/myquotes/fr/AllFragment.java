package goodtogo.com.myquotes.fr;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import goodtogo.com.myquotes.MainActivity;
import goodtogo.com.myquotes.R;
import goodtogo.com.myquotes.ReadJson;
import goodtogo.com.myquotes.adapter.AllAdapter;
import goodtogo.com.myquotes.adapter.LoveAdapter;
import goodtogo.com.myquotes.model.AllModel;
import goodtogo.com.myquotes.model.LoveModel;

/**
 * Created by Sopheap on 6/9/2017.
 */
public class AllFragment extends Fragment {

    MainActivity mainActivity;
    private ListView listView;
    private AllAdapter allAdapter;
    private List<AllModel> listAllModel;
    TextView textView;
    EditText editTextSearch;
    private final static String TITLE = "all.json";
    private static final String[] TITLE_ARY = {"education.json", "attitude.json"
            , "failure.json", "idea.json"
            , "angry.json" , "love_relationship.json"
            , "sad.json"
            ,"other.json"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_fr, null);
        bindComponent(view);
        adListener();

        listAllModel = new ArrayList<AllModel>();

        ReadJson readJson = new ReadJson(getActivity(), TITLE);
        listAllModel = readJson.passJsonForAllFr();

        listView = (ListView) view.findViewById(R.id.lv_all);
        allAdapter = new AllAdapter(getActivity().getApplicationContext(), listAllModel);
        listView.setAdapter(allAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Toast.makeText(getActivity(), i + " - "   + listAllModel.get(i).getItemText().toLowerCase(), Toast.LENGTH_SHORT).show();
                String title = listAllModel.get(i).getItemText().toLowerCase();
                title = title + ".json";
                mainActivity.commitFragment(new LoveFragment(), TITLE_ARY[i]);
                Log.e("THIS--",  TITLE_ARY[i]);
                        //listView.getItemAtPosition(i).toString());

            }
        });


        return view;
    }

    private void bindComponent(View v){
       editTextSearch = (EditText) v.findViewById(R.id.search_word);
    }
    private void adListener(){
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                allAdapter.getFilter().filter(charSequence);
                allAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
