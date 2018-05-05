package compathon.org.logan_android.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(HomeActivity.this);
    }

    @OnClick({R.id.btnJoinNow})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnJoinNow:
                Intent intentJoinNow = new Intent(HomeActivity.this, InRoomActivity.class);

                int numberRoom = MathUtils.parseInt(edtNumberRoom.getText().toString(), 0);
                if (numberRoom > 0) {
                    DialogUtils.showDialog(this, getString(R.string.invalidNumberRoom));
                } else {
                    startActivity(intentJoinNow);
                }

                break;
            case R.id.btnCreateRoom:
                Intent intentCreateRoom = new Intent(HomeActivity.this, CreateRoomActivity.class);
                startActivity(intentCreateRoom);
                break;
            default:
                break;
        }
    }
}
