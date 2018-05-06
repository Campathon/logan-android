package compathon.org.logan_android.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import compathon.org.logan_android.R;
import compathon.org.logan_android.adapter.CardGridAdapter;
import compathon.org.logan_android.adapter.UserRoomAdapter;
import compathon.org.logan_android.callback.RequestComplete;
import compathon.org.logan_android.common.Constants;
import compathon.org.logan_android.common.DialogUtils;
import compathon.org.logan_android.common.ListAPI;
import compathon.org.logan_android.model.CardItem;
import compathon.org.logan_android.model.User;
import compathon.org.logan_android.service.RequestService;
import compathon.org.logan_android.view.GridSpacingItemDecoration;
import compathon.org.logan_android.view.SpacingItemDecoration;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by Andy on 5/5/2018.
 */

public class WaitingRoomActivity extends AppCompatActivity{

    private static final String TAG = "WaitingRoomActivity";
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

    @BindView(R.id.lvGridCard)
    RecyclerView lvGridCard;
    CardGridAdapter cardGridAdapter;
    GridLayoutManager layoutManagerCard;

    @BindView(R.id.toolbarInRoomActivity)
    LinearLayout toolbarInRoomActivity;

    @BindView(R.id.layoutStartView)
    LinearLayout layoutStartView;

    @BindView(R.id.tvUserJoinCount)
    TextView tvUserJoinCount;


    private List<User> userList;
    private List<CardItem> cardItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_room);
        ButterKnife.bind(WaitingRoomActivity.this);
        userList = new ArrayList<>();
        cardItemList = new ArrayList<>();
        initViews();

        try {
            IO.Options opts = new IO.Options();
            opts.query = "room=" + roomCode;
            socket = IO.socket(ListAPI.BASE_URL, opts);

            if (isHost) {
                layoutStartView.setVisibility(View.VISIBLE);
            } else {
                layoutStartView.setVisibility(View.GONE);
            }

            socket.on("usersChanged", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.e(TAG, StringUtils.join(args));
                    if (args.length >= 1) {
                        User user = new Gson().fromJson(args[0].toString(), User.class);
                        if (user != null) {
                            userList.add(user);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    userRoomAdapter.notifyDataSetChanged();
                                    tvUserJoinCount.setText(userList.size() + " người tham gia");
                                }
                            });
                        }
                    }
                }
            });

            socket.on("roomClosed", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.e(TAG, StringUtils.join(args));
                }
            });

            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        getCardList();
    }

    @OnClick({R.id.tvExit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvExit:
                DialogUtils.showOkDialog(this, getString(R.string.confirmExit), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isHost) {
                            closeRoom();
                        } else {
                            leaveRoom();
                        }
                    }
                });
                break;
            default:
                break;
        }
    }

    private void getCardList() {

        RequestService.get(this, ListAPI.LIST_CARD, null, null, new RequestComplete() {
            @Override
            public void onComplete(boolean success, int status, String message, JsonElement data) {
                if (success) {
                    List<CardItem> results = new Gson().fromJson(data, new TypeToken<List<CardItem>>(){}.getType());
                    cardItemList.addAll(results);
                    cardGridAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    private void initViews() {

        roomCode = getIntent().getIntExtra(Constants.kRoomCode, 0);

        tvRoomNumber.setText("Phòng " + roomCode);
        isHost = getIntent().getBooleanExtra(Constants.kHostRoom, false);
        String jsonUsers = getIntent().getStringExtra(Constants.kUserList);
        if (StringUtils.isNotBlank(jsonUsers)) {
            Log.e(TAG, jsonUsers);
            List<User> arrUsers = new Gson().fromJson(jsonUsers, new TypeToken<List<User>>(){}.getType());
            if (arrUsers != null) {
                userList.addAll(arrUsers);
            }
        }
        //Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //image.eraseColor(android.graphics.Color.GREEN);
        //toolbarInRoomActivity.setBackground(Draimage);

        layoutManagerUser = new LinearLayoutManager(this);
        layoutManagerUser.setOrientation(LinearLayoutManager.HORIZONTAL);
        lvUsersRoom.setLayoutManager(layoutManagerUser);
        lvUsersRoom.setFocusable(false);
        lvUsersRoom.setNestedScrollingEnabled(false);
        lvUsersRoom.addItemDecoration(new SpacingItemDecoration(this, 1, (int) getResources().getDimension(R.dimen.padding_small)));

        userRoomAdapter = new UserRoomAdapter(this, userList);
        lvUsersRoom.setAdapter(userRoomAdapter);

        layoutManagerCard = new GridLayoutManager(this, 3);
        layoutManagerCard.setOrientation(LinearLayoutManager.VERTICAL);
        lvGridCard.setLayoutManager(layoutManagerCard);
        lvGridCard.addItemDecoration(new GridSpacingItemDecoration(3, (int) 0, true));
        cardGridAdapter = new CardGridAdapter(this, cardItemList);
        lvGridCard.setAdapter(cardGridAdapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }

    @Override
    public void onBackPressed()
    {
        //super.onBackPressed();
        DialogUtils.showOkDialog(this, getString(R.string.confirmExit), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isHost) {
                    closeRoom();
                } else {
                    leaveRoom();
                }
            }
        });
    }

    public void closeRoom() {

        JSONObject params = new JSONObject();
        try {
            params.put("room", roomCode);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        ProgressDialog dialog = ProgressDialog.show(this, "", getString(R.string.loadingMessage), true);
        StringEntity entity = new StringEntity(params.toString(), "UTF-8");
        RequestService.post(this, ListAPI.CLOSE_ROOM, entity, dialog, new RequestComplete() {
            @Override
            public void onComplete(boolean success, int status, String message, JsonElement data) {
                finish();
            }
        });

    }

    public void leaveRoom() {

        JSONObject params = new JSONObject();
        try {
            params.put("room", roomCode);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        ProgressDialog dialog = ProgressDialog.show(this, "", getString(R.string.loadingMessage), true);
        StringEntity entity = new StringEntity(params.toString(), "UTF-8");
        RequestService.post(this, ListAPI.LEAVE_ROOM, entity, dialog, new RequestComplete() {
            @Override
            public void onComplete(boolean success, int status, String message, JsonElement data) {
                finish();
            }
        });
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
