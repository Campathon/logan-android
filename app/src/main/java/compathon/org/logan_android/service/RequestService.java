package compathon.org.logan_android.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import compathon.org.logan_android.R;
import compathon.org.logan_android.callback.RequestComplete;
import compathon.org.logan_android.common.DialogUtils;

/**
 * Created by Andy on 5/5/2018.
 */

public class RequestService {

    private static final String TAG = "RequestService";

    public static void post(final Context context, String url, final StringEntity entity, final ProgressDialog dialog, final RequestComplete requestComplete) {

        if (DialogUtils.checkNetworkWithAlert(context)) {
            AsyncHttpClient client = new AsyncHttpClient();


            entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            client.addHeader("Content-Type", "application/json");

            client.post(context, url, entity, "application/json", new TextHttpResponseHandler() {

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
                    Log.e(TAG, "onSuccess: " + responseString);
                    JsonObject jsonObject = gson.fromJson(responseString, JsonObject.class);

                    if (jsonObject.get("success").getAsBoolean()) {
                        if (requestComplete != null) {
                            requestComplete.onComplete(true, 200,  "", jsonObject.get("data"));
                        }
                    } else {
                        if (jsonObject.has("message")) {
                            DialogUtils.showDialog(context, jsonObject.get("message").getAsString());
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
                    } else {
                        if (jsonObject.has("message")) {
                            DialogUtils.showDialog(context, jsonObject.get("message").getAsString());
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

            client.get(url, params, new TextHttpResponseHandler() {
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
                    Log.e(TAG, "responseString:" + responseString);
                    DialogUtils.showDialog(context, context.getString(R.string.serverInternalError));
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(responseString, JsonObject.class);
                    Log.e(TAG, "onSuccess: " + responseString);

                    if (jsonObject.get("success").getAsBoolean()) {
                        if (requestComplete != null) {
                            requestComplete.onComplete(true, 200, "", jsonObject.get("data"));
                        }
                    } else {
                        if (jsonObject.has("message")) {
                            DialogUtils.showDialog(context, jsonObject.get("message").getAsString());
                        }
//                        if (requestComplete != null) {
//                            requestComplete.onComplete(false, 200,  jsonObject.get("message").getAsString(), jsonObject.get("data"));
//                        }
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
