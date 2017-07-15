package goodtogo.com.myquotes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.List;

import goodtogo.com.myquotes.R;
import goodtogo.com.myquotes.model.AllModel;
import goodtogo.com.myquotes.model.LoveModel;

/**
 * Created by Sopheap on 6/14/2017.
 */
public class AllAdapter extends ArrayAdapter<AllModel>  {

    public AllAdapter(Context context, List<AllModel> allModelList) {
        super(context, 0, allModelList);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
                getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rootView =  convertView;
        if (convertView == null) {
            rootView = inflater.inflate(R.layout.item_all, parent, false);
        }
        TextView main_text = (TextView) rootView.findViewById(R.id.show_listitem);
        TextView count_text = (TextView) rootView.findViewById(R.id.textView);
        AllModel allModel = getItem(position);

        main_text.setText(allModel.getItemText());
        count_text.setText(allModel.getCountText());

        return rootView;
    }


}
