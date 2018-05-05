package compathon.org.logan_android.model;

/**
 * Created by Andy on 5/5/2018.
 */

public class CardItem {

    public String _id;
    public String name;
    public String image;
    public int quantity;
    public String alias;
    public String description;

    public CardItem() {}

    public CardItem(String name) {
        this.name = name;
        this.quantity = 0;
    }

}
