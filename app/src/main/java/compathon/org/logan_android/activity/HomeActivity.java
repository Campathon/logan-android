package compathon.org.logan_android.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import compathon.org.logan_android.R;
import compathon.org.logan_android.common.DialogUtils;
import compathon.org.logan_android.common.MathUtils;

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
                Intent intentCreateRoom = new Intent(HomeActivity.this, CardListActivity.class);
                startActivity(intentCreateRoom);
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
