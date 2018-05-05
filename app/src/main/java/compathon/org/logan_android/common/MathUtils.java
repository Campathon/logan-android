package compathon.org.logan_android.common;

import android.util.Log;

/**
 * Created by Andy on 5/5/2018.
 */

public class MathUtils {

    private static final String TAG = "MathUtils";

    public static int parseInt(String v, int df) {
        try {
            return Integer.parseInt(v);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return df;
    }
}
