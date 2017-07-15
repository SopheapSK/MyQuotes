package goodtogo.com.myquotes.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import goodtogo.com.myquotes.R;
import goodtogo.com.myquotes.model.LoveModel;

/**
 * Created by Sopheap on 6/14/2017.
 */
public class LoveAdapter extends ArrayAdapter<LoveModel>  {
    ArrayList<LoveModel> mStringFilterList;
    List<LoveModel> loveList;
    ValueFilter valueFilter;
    public LoveAdapter(Context context, List<LoveModel> loveModelList) {
        super(context, 0, loveModelList);
        loveList = loveModelList;
        mStringFilterList = (ArrayList<LoveModel>) loveModelList;
    }

    @Override
    public int getCount() {
        return loveList.size();
    }

    @Override
    public LoveModel getItem(int position) {
        return loveList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return loveList.indexOf(getItem(position));
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
                getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rootView = convertView;
        if (convertView == null) {
            rootView = inflater.inflate(R.layout.item, parent, false);
        }
        TextView texto = (TextView) rootView.findViewById(R.id.show_listitem);
        LoveModel loveModel = loveList.get(position);

        texto.setText(loveModel.getItemText());
        return rootView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }
    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<LoveModel> filterList = new ArrayList<LoveModel>();
                for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ( (mStringFilterList.get(i).getItemText().toUpperCase().trim() )
                            .contains(constraint.toString().toUpperCase().trim())) {

                        LoveModel loveModel = new LoveModel(mStringFilterList.get(i).getItemText());
                                //,  mStringFilterList.get(i));
                               // .getIso_code() ,  mStringFilterList.get(i)
                                //.getFlag());

                        filterList.add(loveModel);
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            loveList = (ArrayList<LoveModel>) results.values;
            notifyDataSetChanged();
        }

    }

}
