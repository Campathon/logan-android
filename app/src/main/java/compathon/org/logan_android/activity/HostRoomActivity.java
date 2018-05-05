package compathon.org.logan_android.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import compathon.org.logan_android.R;

/**
 * Created by Andy on 5/5/2018
 */

public class HostRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_room);
        ButterKnife.bind(HostRoomActivity.this);
    }

}
