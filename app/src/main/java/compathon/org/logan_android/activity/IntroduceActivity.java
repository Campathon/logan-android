package compathon.org.logan_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import compathon.org.logan_android.R;

public class IntroduceActivity extends AppCompatActivity {

    @BindView(R.id.btn_share_now)
    Button btnShareNow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        ButterKnife.bind(IntroduceActivity.this);
        initViews();
    }

    private void initViews() {
        btnShareNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                share.putExtra(Intent.EXTRA_SUBJECT, "[Chia sẻ] Game Chơi bài ma sói");
                share.putExtra(Intent.EXTRA_TEXT,
                        "Chơi bài ma sói:\n" +
                                "- [Android] https://play.google.com/store/apps/details?id=compathon.org.logan_android" +
                                "\n" +
                                "- [iOS] https://itunes.apple.com/us/app/chia-b%C3%A0i-ma-s%C3%B3i/id1386220375" +
                                "\n" +
                                "- [Web] http://lo.netlify.com"
                );
                startActivity(Intent.createChooser(share, "Gửi link chia sẻ tới bạn bè"));
            }
        });
    }
}
