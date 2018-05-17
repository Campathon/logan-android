package compathon.org.logan_android.model;

import org.json.JSONException;
import org.json.JSONObject;

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

    public CardItem(JSONObject jsonCard) throws JSONException {
        this._id = jsonCard.has("_id") ? jsonCard.getString("_id") : "";
        this.name = jsonCard.has("name") ? jsonCard.getString("name") : "";
        this.image = jsonCard.has("image") ? jsonCard.getString("image") : "";
        this.description = jsonCard.has("description") ? jsonCard.getString("description") : "";
        this.alias = jsonCard.has("alias") ? jsonCard.getString("alias") : "";
    }

    public CardItem(String name) {
        this.name = name;
        this.quantity = 0;
    }

    public CardItem(String name, String image, String description) {
        this.description = description;
        this.image = name;
        this.image = image;
    }

}
