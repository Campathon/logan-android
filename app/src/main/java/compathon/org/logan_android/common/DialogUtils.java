package compathon.org.logan_android.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Andy on 5/5/2018.
 */

public class DialogUtils {

    public static void showDialog(Context context, String msg) {
        showOkDialog(context, msg, null);
    }

    public static void showOkDialog(Context context, String msg, DialogInterface.OnClickListener clickListener) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage(msg);
        dialog.setCancelable(true);
        dialog.setPositiveButton("OK", clickListener);
        dialog.show();
    }
}
