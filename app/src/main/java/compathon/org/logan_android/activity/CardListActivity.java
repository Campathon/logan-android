package compathon.org.logan_android.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    @BindView(R.id.lvCardList)
    RecyclerView lvCardList;
    LinearLayoutManager layoutManagerCardList;
    CardItemAdapter cardItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);
        ButterKnife.bind(CardListActivity.this);

        initView();
    }

    private void initView() {
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

}
