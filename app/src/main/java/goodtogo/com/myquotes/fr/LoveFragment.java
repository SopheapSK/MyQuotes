package goodtogo.com.myquotes.fr;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
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

import goodtogo.com.myquotes.R;
import goodtogo.com.myquotes.ReadJson;
import goodtogo.com.myquotes.ShowActivity;
import goodtogo.com.myquotes.adapter.LoveAdapter;
import goodtogo.com.myquotes.model.LoveModel;
import goodtogo.com.myquotes.preference.SaveSharePref;

/**
 * Created by Sopheap on 6/9/2017.
 */
public class LoveFragment extends Fragment {
    private ListView listView;
    private LoveAdapter loveAdapter;
    private List<LoveModel> listLoveModel;
    private static final String KEY = "PASS_KEY_NAME";
    String TEXT_TITLE = "";
    EditText editTextSearch;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.love_fr, container, false);
        bindComponent(rootView);
        adListener();

        listLoveModel = new ArrayList<LoveModel>();
        Bundle b = getArguments();
        TEXT_TITLE = b.getString(KEY, "text.json");

        final ReadJson readJson = new ReadJson(getActivity(), TEXT_TITLE);
        listLoveModel = readJson.passJson();

        //listLoveModel.add(new LoveModel("wah"));

        listView = (ListView) rootView.findViewById(R.id.lv);
        loveAdapter = new LoveAdapter(getActivity().getApplicationContext(), listLoveModel);
        listView.setAdapter(loveAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "" + i, Toast.LENGTH_SHORT).show();
                //SaveSharePref saveSharePref = new SaveSharePref();
                Intent intent = new Intent(getActivity(), ShowActivity.class);
                //View view1=(View) listView.getItemAtPosition(i);
                TextView texto = (TextView) view.findViewById(R.id.show_listitem);

                if (listLoveModel.size() != loveAdapter.getCount()) {
                    intent.putExtra("isSearch", texto.getText().toString());
                }
                intent.putExtra("pos", i);
                intent.putExtra("KEY_NAME", TEXT_TITLE);


                //saveSharePref.saveToPreference(getActivity(), i);
                startActivity(intent);
            }
        });

        return rootView;
    }


    private void bindComponent(View v){
        editTextSearch = (EditText) v.findViewById(R.id.search_word);
    }
    private void adListener(){
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                loveAdapter.getFilter().filter(charSequence);
                loveAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

}
