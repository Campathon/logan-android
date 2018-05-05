package compathon.org.logan_android.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

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
import compathon.org.logan_android.service.RequestService;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.edtNumberRoom)
    EditText edtNumberRoom;

    @BindView(R.id.toolbarHomeActivity)
    Toolbar toolbarHomeActivity;

    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(HomeActivity.this);
        initView();
    }

    private void initView() {

        setSupportActionBar(toolbarHomeActivity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_white);

    }

    @OnClick({R.id.btnJoinNow, R.id.btnCreateRoom})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnJoinNow:
                Intent intentJoinNow = new Intent(HomeActivity.this, InRoomActivity.class);

                int numberRoom = MathUtils.parseInt(edtNumberRoom.getText().toString(), 0);
                if (numberRoom > 0) {
                    startActivity(intentJoinNow);
                } else {
                    DialogUtils.showDialog(this, getString(R.string.invalidNumberRoom));
                }

                break;
            case R.id.btnCreateRoom:

                ProgressDialog dialog = ProgressDialog.show(this, "", getString(R.string.loadingMessage), true);

                RequestService.post(this, ListAPI.CREATE_ROOM, null, dialog, new RequestComplete() {
                    @Override
                    public void onComplete(boolean success, int status, String message, JsonElement data) {
                        Log.e(TAG, data.getAsJsonObject().toString());
                        if (success) {
                            Room room = new Gson().fromJson(data, Room.class);

                            Intent intentCreateRoom = new Intent(HomeActivity.this, CardListActivity.class);
                            intentCreateRoom.putExtra(Constants.kRoomId, room._id);
                            intentCreateRoom.putExtra(Constants.kRoomCode, room.code);
                            startActivity(intentCreateRoom);
                        }
                    }
                });

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
