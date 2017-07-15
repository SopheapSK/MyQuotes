package goodtogo.com.myquotes;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import goodtogo.com.myquotes.model.AllModel;
import goodtogo.com.myquotes.model.LoveModel;

/**
 * Created by Sopheap on 6/14/2017.
 */
public class ReadJson {
    List<LoveModel> list;
    List<AllModel> list_all;
    Context context;
    String jsonName;
    public ReadJson(Context context, String jsonName){
        this.context = context;
        this.jsonName = jsonName;

    }
    public ReadJson(){

    }
    public String getJsonFile() {
        return jsonFile;
    }

    public void setJsonFile(String jsonFile) {
        this.jsonFile = jsonFile;
    }

    String jsonFile;

    public List<LoveModel> passJson(){
        String getJson = loadJSONFromAsset();
        String item = "";
        if (getJson == null){
            return  null;
        }
        list = new ArrayList<LoveModel>();
        try {
            //JSONObject jsonObject = new JSONObject(getJson);
            JSONArray jsonArray = new JSONArray(getJson);
            for (int i = 0 ; i < jsonArray.length(); i++){
                JSONObject jsonObject =  jsonArray.getJSONObject(i);
                item = jsonObject.getString("a");
                list.add(new LoveModel(item));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }
    public List<AllModel> passJsonForAllFr(){
        String getJson = loadJSONFromAsset();
        String title = "";
        String amount = "";
        if (getJson == null){
            return  null;
        }
        list_all = new ArrayList<AllModel>();
        try {
            //JSONObject jsonObject = new JSONObject(getJson);
            JSONArray jsonArray = new JSONArray(getJson);
            for (int i = 0 ; i < jsonArray.length(); i++){
                JSONObject jsonObject =  jsonArray.getJSONObject(i);
                title = jsonObject.getString("title");
                amount = jsonObject.getString("amt");
                // list.add(new LoveModel(item));
                list_all.add(new AllModel(title, amount));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return list_all;
    }
    private String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = context.getAssets().open(jsonName);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}
