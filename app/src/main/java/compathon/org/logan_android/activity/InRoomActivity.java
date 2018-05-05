package compathon.org.logan_android.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.net.URISyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;
import compathon.org.logan_android.R;
import compathon.org.logan_android.common.ListAPI;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by Andy on 5/5/2018.
 */

public class InRoomActivity extends AppCompatActivity{

    @BindView(R.id.toolbarInRoomActivity)
    Toolbar toolbarInRoomActivity;

    private static final String TAG = "InRoomActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_room);
        ButterKnife.bind(InRoomActivity.this);
        initViews();

        try {
            final Socket socket = IO.socket(ListAPI.BASE_URL);
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    Log.e(TAG, "Connected");
                    //socket.emit("foo", "hi");
                    //socket.disconnect();
                }

            }).on("event", new Emitter.Listener() {

                @Override
                public void call(Object... args) {}

            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {

                }

            });

            socket.connect();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    private void initViews() {
        setSupportActionBar(toolbarInRoomActivity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_white);
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
