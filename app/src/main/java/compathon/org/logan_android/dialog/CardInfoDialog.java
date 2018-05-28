package compathon.org.logan_android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import compathon.org.logan_android.R;
import compathon.org.logan_android.model.CardItem;

public class CardInfoDialog extends Dialog {

    private Context mContext;
    private CardItem cardItem;

    public CardInfoDialog(Context context, CardItem cardItem) {
//        super(context, R.style.PauseDialog);
        super(context);
        this.mContext = context;
        this.cardItem = cardItem;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_card_info);

        initViews();
    }

    private void initViews() {
        ImageView ivCard = findViewById(R.id.iv_card);
        TextView tvCardName = findViewById(R.id.tv_card_name);
        TextView tvCardDescription = findViewById(R.id.tv_card_description);
        View container = findViewById(R.id.dl_card_info);

        tvCardName.setText(cardItem.name);
        tvCardDescription.setText(cardItem.description);

        Picasso.with(mContext).load(cardItem.image)
                .error(R.mipmap.ic_launcher_round)
                .into(ivCard);

        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardInfoDialog.this.dismiss();
            }
        });
    }

}