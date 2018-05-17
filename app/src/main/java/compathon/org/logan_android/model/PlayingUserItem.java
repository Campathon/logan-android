package compathon.org.logan_android.model;

import org.json.JSONException;
import org.json.JSONObject;

public class PlayingUserItem {

    private String name;
    private String shortName;
    private String status;
    private String _id;
    private CardItem cardItem;

    public PlayingUserItem(JSONObject jsonUser) throws JSONException {
        this.status = jsonUser.has("status") ? jsonUser.getString("status") : "";
        this._id = jsonUser.has("_id") ? jsonUser.getString("_id") : "";
        this.name = jsonUser.has("name") ? jsonUser.getString("name") : "";
        this.cardItem = jsonUser.has("card") ? new CardItem(jsonUser.getJSONObject("card")) : new CardItem();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public CardItem getCardItem() {
        return cardItem;
    }

    public void setCardItem(CardItem cardItem) {
        this.cardItem = cardItem;
    }
}
