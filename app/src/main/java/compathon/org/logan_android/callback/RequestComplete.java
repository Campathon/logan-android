package compathon.org.logan_android.callback;

import com.google.gson.JsonElement;

public interface RequestComplete {

    void onComplete(boolean success, int status, String message, JsonElement data);
}
