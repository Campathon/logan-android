package compathon.org.logan_android.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import compathon.org.logan_android.R;

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

    public static void showOkDialogForce(Context context, String msg, DialogInterface.OnClickListener clickListener) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage(msg);
        dialog.setCancelable(false);
        dialog.setPositiveButton("OK", clickListener);
        dialog.show();
    }

    public static boolean checkNetworkWithAlert(final Context context) {
        if (!checkNetwork(context)) {
            showOkDialog(context, context.getString(R.string.noInternetConnection), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ((Activity)context).finish();
                    dialog.dismiss();
                }
            });

            return false;
        }
        return true;
    }

    public static boolean checkNetwork(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null) {
            if (networkInfo.isConnectedOrConnecting()) {
                return true;
            }
        }
        return false;
    }
}
