package compathon.org.logan_android.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

        if (cardItem != null) {
            final WaitingRoomActivity activity = (WaitingRoomActivity) getActivity();
            tvCardName.setText(activity.cardItem.name);
            Picasso.with(activity).load(activity.cardItem.image)
                    .into(imvCard);
            tvCardDescription.setText(activity.cardItem.description);
        }


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
