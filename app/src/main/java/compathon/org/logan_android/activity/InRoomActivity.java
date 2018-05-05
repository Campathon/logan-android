package compathon.org.logan_android.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import compathon.org.logan_android.R;

/**
 * Created by Andy on 5/5/2018.
 */

public class InRoomActivity extends AppCompatActivity{

    @BindView(R.id.toolbarInRoomActivity)
    Toolbar toolbarInRoomActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_room);
        ButterKnife.bind(InRoomActivity.this);
        initViews();
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
