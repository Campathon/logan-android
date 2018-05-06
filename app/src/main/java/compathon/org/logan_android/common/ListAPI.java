package compathon.org.logan_android.common;

/**
 * Created by Andy on 5/5/2018.
 */

public class ListAPI {

    public static final String BASE_URL = "http://logan.blogk.xyz";

    public static final String CREATE_ROOM = BASE_URL + "/rooms";
    public static final String JOIN_ROOM = BASE_URL + "/rooms/join";
    public static final String LIST_CARD = BASE_URL + "/cards";
    public static final String CLOSE_ROOM = BASE_URL + "/rooms/close";
    public static final String LEAVE_ROOM = BASE_URL + "/rooms/leave";
    public static final String START_GAME = BASE_URL + "/rooms/play";
}
