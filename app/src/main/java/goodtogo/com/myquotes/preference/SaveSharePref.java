package goodtogo.com.myquotes.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Sopheap on 6/16/2017.
 */
public class SaveSharePref {


    public void saveToPreference(Context context, int position){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("pos",position);
        editor.apply();
    }
    public int getPreference(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        int position = preferences.getInt("pos", 0);
        return position;
    }
    public void saveToPreference(Context context, String text){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("key",text);
        editor.apply();
    }
    public String getKeyPreference(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String position = preferences.getString("key", "Error");
        return position;
    }
}
