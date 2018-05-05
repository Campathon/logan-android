package compathon.org.logan_android.activity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import compathon.org.logan_android.R;
import compathon.org.logan_android.adapter.UserRoomAdapter;
import compathon.org.logan_android.common.Constants;
import compathon.org.logan_android.common.DialogUtils;
import compathon.org.logan_android.common.ListAPI;
import compathon.org.logan_android.model.User;
import compathon.org.logan_android.view.SpacingItemDecoration;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by Andy on 5/5/2018.
 */

public class InRoomActivity extends AppCompatActivity{

    private static final String TAG = "InRoomActivity";
    private String roomId;
    private int roomCode;

    @BindView(R.id.tvRoomNumber)
    TextView tvRoomNumber;

    @BindView(R.id.btnStart)
    Button btnStart;

    private Socket socket;
    private boolean isHost = false;

    @BindView(R.id.lvUsersRoom)
    RecyclerView lvUsersRoom;
    UserRoomAdapter userRoomAdapter;
    LinearLayoutManager layoutManagerUser;

    @BindView(R.id.toolbarInRoomActivity)
    LinearLayout toolbarInRoomActivity;

    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_room);
        ButterKnife.bind(InRoomActivity.this);
        userList = new ArrayList<>();
        initViews();

        try {
            socket = IO.socket(ListAPI.BASE_URL);

            if (isHost) {
                btnStart.setVisibility(View.VISIBLE);
                socket.emit("room",  roomCode);
                socket.on("newUser", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        Log.e(TAG, StringUtils.join(args));
                    }
                });
            } else {
                btnStart.setVisibility(View.GONE);
                socket.emit("room",  roomCode);
            }

            socket.connect();
            //Log.e(TAG, "" + socket.hasListeners(event));
            //Log.e(TAG, "" + socket.hasListeners("newUser"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    @OnClick({R.id.tvExit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvExit:
                DialogUtils.showOkDialog(this, getString(R.string.confirmExit), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                break;
            default:
                break;
        }
    }

    private void initViews() {

        roomCode = getIntent().getIntExtra(Constants.kRoomCode, 0);

        tvRoomNumber.setText("Phòng " + roomCode);
        isHost = getIntent().getBooleanExtra(Constants.kHostRoom, false);

        //Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //image.eraseColor(android.graphics.Color.GREEN);
        //toolbarInRoomActivity.setBackground(Draimage);

        layoutManagerUser = new LinearLayoutManager(this);
        layoutManagerUser.setOrientation(LinearLayoutManager.HORIZONTAL);
        lvUsersRoom.setLayoutManager(layoutManagerUser);
        lvUsersRoom.setFocusable(false);
        lvUsersRoom.setNestedScrollingEnabled(false);
        lvUsersRoom.addItemDecoration(new SpacingItemDecoration(this, 1, (int) getResources().getDimension(R.dimen.padding_small)));


        for (int i = 0; i < 10; i++) {
            userList.add(new User(i, "Tè tè " + i));
        }

        userRoomAdapter = new UserRoomAdapter(this, userList);
        lvUsersRoom.setAdapter(userRoomAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.disconnect();
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
