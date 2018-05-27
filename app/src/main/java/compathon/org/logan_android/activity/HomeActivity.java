package compathon.org.logan_android.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.loopj.android.http.RequestParams;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import compathon.org.logan_android.R;
import compathon.org.logan_android.callback.RequestComplete;
import compathon.org.logan_android.common.Constants;
import compathon.org.logan_android.common.DialogUtils;
import compathon.org.logan_android.common.ListAPI;
import compathon.org.logan_android.common.MathUtils;
import compathon.org.logan_android.model.Room;
import compathon.org.logan_android.model.User;
import compathon.org.logan_android.service.CrashlyticsService;
import compathon.org.logan_android.service.RequestService;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.edtNumberRoom)
    EditText edtNumberRoom;

    //@BindView(R.id.toolbarHomeActivity)
    //Toolbar toolbarHomeActivity;

    @BindView(R.id.edtUsername)
    EditText edtUsername;

    @BindView(R.id.tv_introduce)
    TextView tvIntroduce;

    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(HomeActivity.this);
        initView();
    }

    private void initView() {

        //setSupportActionBar(toolbarHomeActivity);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_white);

    }

    @OnClick({R.id.btnJoinNow, R.id.btnCreateRoom, R.id.tv_introduce})
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnJoinNow:
                final String username = edtUsername.getText().toString();
                final int numberRoom = MathUtils.parseInt(edtNumberRoom.getText().toString(), 0);
                if (numberRoom == 0) {
                    DialogUtils.showDialog(this, getString(R.string.invalidNumberRoom));
                } else if (!StringUtils.isNotBlank(username)) {
                    DialogUtils.showDialog(this, getString(R.string.invalidUsername));
                } else {

                    JSONObject params = new JSONObject();
                    try {
                        params.put("name", username);
                        params.put("room", numberRoom);

                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                    }

                    StringEntity entity = new StringEntity(params.toString(), "UTF-8");
                    ProgressDialog dialog = ProgressDialog.show(this, "", getString(R.string.loadingMessage), true);
                    RequestService.post(this, ListAPI.JOIN_ROOM, entity, dialog, new RequestComplete() {
                        @Override
                        public void onComplete(boolean success, int status, String message, JsonElement data) {
                            if (success) {
                                User user = new Gson().fromJson(data, User.class);

                                Intent intentJoinNow = new Intent(HomeActivity.this, WaitingRoomActivity.class);
                                intentJoinNow.putExtra(Constants.kRoomCode, numberRoom);
                                intentJoinNow.putExtra(Constants.kHostRoom, false);
                                //String jsonUsers = new Gson().toJson(room.users);
                                intentJoinNow.putExtra(Constants.kUserId, user._id);
                                intentJoinNow.putExtra(Constants.kUsername, username);
                                startActivity(intentJoinNow);
                            }
                        }
                    });
                }

                break;
            case R.id.btnCreateRoom:
                ProgressDialog dialog = ProgressDialog.show(this, "", getString(R.string.loadingMessage), true);
                RequestService.post(this, ListAPI.CREATE_ROOM, new RequestParams(), dialog, new RequestComplete() {
                    @Override
                    public void onComplete(boolean success, int status, String message, JsonElement data) {
                        if (success) {
                            Room room = new Gson().fromJson(data, Room.class);

                            Intent intentCreateRoom = new Intent(HomeActivity.this, WaitingRoomActivity.class);
                            intentCreateRoom.putExtra(Constants.kRoomId, room._id);
                            intentCreateRoom.putExtra(Constants.kRoomCode, room.code);
                            intentCreateRoom.putExtra(Constants.kHostRoom, true);
                            startActivity(intentCreateRoom);

                            CrashlyticsService.onCreateRoom();
                        }
                    }
                });

                break;
            case R.id.tv_introduce:
                Intent startIntroduce = new Intent();
                startIntroduce.setClass(HomeActivity.this, IntroduceActivity.class);
                HomeActivity.this.startActivity(startIntroduce);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
