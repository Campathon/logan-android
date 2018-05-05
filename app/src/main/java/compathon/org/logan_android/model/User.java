package compathon.org.logan_android.model;

/**
 * Created by Andy on 5/6/2018.
 */

public class User {

    public int id;
    public String name;
    public String status;
    public String card;

    public User() {}

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
