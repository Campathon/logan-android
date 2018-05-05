package compathon.org.logan_android.service;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import compathon.org.logan_android.R;
import compathon.org.logan_android.callback.RequestComplete;
import compathon.org.logan_android.common.DialogUtils;

/**
 * Created by Andy on 5/5/2018.
 */

public class RequestService {

    private static final String TAG = "RequestService";

    public static void post(final Context context, String url, final RequestParams params, final ProgressDialog dialog, final RequestComplete requestComplete) {

        if (DialogUtils.checkNetworkWithAlert(context)) {
            AsyncHttpClient client = new AsyncHttpClient();

            Log.e(TAG, "Request: " + url);

            client.post(url, params, new TextHttpResponseHandler() {
                @Override
                public void onStart() {
                    if (dialog != null) {
                        dialog.show();
                    } else {
                        super.onStart();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    DialogUtils.showDialog(context, context.getString(R.string.serverInternalError));
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(responseString, JsonObject.class);
                    Log.e(TAG, "onSuccess: " + responseString);

                    if (jsonObject.get("success").getAsBoolean()) {
                        if (requestComplete != null) {
                            requestComplete.onComplete(true, 200,  "", jsonObject.get("data"));
                        }
                    }
                }

                @Override
                public void onFinish() {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    } else {
                        super.onFinish();
                    }
                }
            });
        }

    }


    public static void get(final Context context, String url, final RequestParams params, final ProgressDialog dialog, final RequestComplete requestComplete) {
        if (DialogUtils.checkNetworkWithAlert(context)) {
            AsyncHttpClient client = new AsyncHttpClient();

            client.post(url, params, new TextHttpResponseHandler() {
                @Override
                public void onStart() {
                    if (dialog != null) {
                        dialog.show();
                    } else {
                        super.onStart();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    DialogUtils.showDialog(context, context.getString(R.string.serverInternalError));
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(responseString, JsonObject.class);
                    Log.e(TAG, "onSuccess: " + responseString);

                    if (jsonObject.get("success").getAsBoolean()) {
                        if (requestComplete != null) {
                            requestComplete.onComplete(true, 200, !jsonObject.get("message").isJsonNull() ? jsonObject.get("message").getAsString() : "", jsonObject.get("data"));
                        }
                    }
                }

                @Override
                public void onFinish() {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    } else {
                        super.onFinish();
                    }
                }
            });
        }

    }

}
