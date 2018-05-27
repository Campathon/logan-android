package compathon.org.logan_android.service;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;

public class CrashlyticsService {

    private static final String CREATE_ROOM_EVENT = "Creating Room";
    private static final String START_GAME_EVENT = "Start game";

    public static void onCreateRoom() {
        Answers.getInstance().logCustom(new CustomEvent(CREATE_ROOM_EVENT));
    }

    public static void onStartGame(int numberOfPlayer) {
        Answers.getInstance().logCustom(new CustomEvent(START_GAME_EVENT)
                .putCustomAttribute("Number Players", String.valueOf(numberOfPlayer))
        );
    }
}
