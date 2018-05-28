package compathon.org.logan_android.fragment;

import android.animation.Animator;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import compathon.org.logan_android.R;
import compathon.org.logan_android.activity.WaitingRoomActivity;
import compathon.org.logan_android.model.CardItem;

/**
 * Created by Andy on 5/10/2018.
 */

public class PlayerFragment extends Fragment {

    private static final String TAG = "PlayerFragment";

    @BindView(R.id.imvCard)
    ImageView imvCard;
    @BindView(R.id.tvCardName)
    TextView tvCardName;
    @BindView(R.id.tvCardDescription)
    TextView tvCardDescription;

    boolean isHideCard = true;

    CardItem cardItem;

    public static final PlayerFragment newInstance(CardItem cardItem) {
        PlayerFragment f = new PlayerFragment();
        f.cardItem = cardItem;
        return f;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_player, container, false);
        ButterKnife.bind(this, view);

        initViews();

        return view;
    }

    private void initViews() {
        if (cardItem != null) {
            final WaitingRoomActivity activity = (WaitingRoomActivity) getActivity();
            imvCard.setImageResource(R.mipmap.ic_launcher);
            tvCardName.setText(activity.getText(R.string.toundToOpendCard));
            tvCardDescription.setText(activity.cardItem.description);

            imvCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    YoYo.with(Techniques.FlipInY)
                            .duration(700)
                            .onStart(new YoYo.AnimatorCallback() {
                                @Override
                                public void call(Animator animator) {
                                    if (!isHideCard) {
                                        imvCard.setImageResource(R.mipmap.ic_launcher);
                                        tvCardName.setText(activity.getText(R.string.toundToOpendCard));
                                        tvCardDescription.setVisibility(View.INVISIBLE);
                                    } else {
                                        Picasso.with(activity).load(activity.cardItem.image)
                                                .into(imvCard);
                                        tvCardName.setText(activity.cardItem.name);
                                        tvCardName.setVisibility(View.VISIBLE);
                                        tvCardDescription.setVisibility(View.VISIBLE);
                                    }
                                    isHideCard = !isHideCard;
                                }
                            })
                            .playOn(imvCard);
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
