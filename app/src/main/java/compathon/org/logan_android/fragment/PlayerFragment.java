package compathon.org.logan_android.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import compathon.org.logan_android.R;

/**
 * Created by Andy on 5/10/2018.
 */

public class PlayerFragment extends Fragment {

    @BindView(R.id.imvCard)
    ImageView imvCard;
    @BindView(R.id.tvCardName)
    TextView tvCardName;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_player, container, false);
        ButterKnife.bind(this, view);

//        final WaitingRoomActivity activity = (WaitingRoomActivity) getActivity();
//        tvCardName.setText(activity.cardItem.name);
//        Picasso.with(activity).load(activity.cardItem.image)
//                .into(imvCard);


        return view;
    }


}
