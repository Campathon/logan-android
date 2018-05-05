package compathon.org.logan_android.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import compathon.org.logan_android.R;
import compathon.org.logan_android.adapter.CardItemAdapter;
import compathon.org.logan_android.common.Constants;
import compathon.org.logan_android.model.CardItem;
import compathon.org.logan_android.view.SpacingItemDecoration;

/**
 * Created by Andy on 5/5/2018.
 */

public class CardListActivity extends AppCompatActivity {

    @BindView(R.id.toolbarCardListActivity)
    Toolbar toolbarCardListActivity;

    @BindView(R.id.lvCardList)
    RecyclerView lvCardList;
    LinearLayoutManager layoutManagerCardList;
    CardItemAdapter cardItemAdapter;

    private String roomId;
    private int roomCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);
        ButterKnife.bind(CardListActivity.this);

        passData();

        initView();
    }

    private void passData() {
        roomId = getIntent().getStringExtra(Constants.kRoomId);
        roomCode = getIntent().getIntExtra(Constants.kRoomCode, 0);
    }

    private void initView() {

        setSupportActionBar(toolbarCardListActivity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_white);
        getSupportActionBar().setTitle("Phòng chơi số: " + String.format("%02d", roomCode));
        layoutManagerCardList = new LinearLayoutManager(this);
        layoutManagerCardList.setOrientation(LinearLayoutManager.VERTICAL);
        lvCardList.setLayoutManager(layoutManagerCardList);
        lvCardList.setFocusable(false);
        lvCardList.setNestedScrollingEnabled(false);
        lvCardList.addItemDecoration(new SpacingItemDecoration(this, Constants.verticalType, (int) getResources().getDimension(R.dimen.padding_half_dp)));

        List<CardItem> cardItemList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            cardItemList.add(new CardItem("Card Item " + i));
        }

        cardItemAdapter = new CardItemAdapter(this, cardItemList);
        lvCardList.setAdapter(cardItemAdapter);
    }

    @OnClick({R.id.btnConfirm})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConfirm:

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
