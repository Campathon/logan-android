package compathon.org.logan_android.common;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by Andy on 5/5/2018.
 */

public class MathUtils {

    private static final String TAG = "MathUtils";

    public static int[] ScreenSize(Context context) {
        int[] size = new int[2];
        DisplayMetrics displaymetrics = context.getResources()
                .getDisplayMetrics();
        size[0] = displaymetrics.widthPixels;
        size[1] = displaymetrics.heightPixels;

        return size;
    }

    public static int parseInt(String v, int df) {
        try {
            return Integer.parseInt(v);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return df;
    }
}
