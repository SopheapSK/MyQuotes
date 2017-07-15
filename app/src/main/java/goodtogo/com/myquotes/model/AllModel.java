package goodtogo.com.myquotes.model;

/**
 * Created by Sopheap on 6/14/2017.
 */
public class AllModel {
    public AllModel(){

    }
    public AllModel(String itemText, String countText){
        this.itemText = itemText;
        this.countText = countText;
    }
    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    private String itemText;

    public String getCountText() {
        return countText;
    }

    public void setCountText(String countText) {
        this.countText = countText;
    }

    private String countText;
}
