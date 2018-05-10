package compathon.org.logan_android.model;

/**
 * Created by Andy on 5/6/2018.
 */

public class User {

    public String _id;
    public String name;
    public String status;
    public Object card;

    public User() {}

    public User(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + _id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", card='" + card + '\'' +
                '}';
    }
}
