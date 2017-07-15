package goodtogo.com.myquotes.model;

/**
 * Created by Sopheap on 6/14/2017.
 */
public class LoveModel {
    public LoveModel(){

    }
    public LoveModel(String itemText){
        this.itemText = itemText;
    }
    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    private String itemText;
}
