package compathon.org.logan_android.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import compathon.org.logan_android.R;
import compathon.org.logan_android.model.CardItem;
import compathon.org.logan_android.model.PlayingUserItem;
import de.hdodenhof.circleimageview.CircleImageView;



public class PlayingUserAdapter extends RecyclerView.Adapter<PlayingUserAdapter.PlayingUserItemHolder> {

    private List<PlayingUserItem> playingUserItemList;
    private Context mContext;

    public PlayingUserAdapter(Context context, List<PlayingUserItem> playingUserItemList) {
        this.playingUserItemList = playingUserItemList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public PlayingUserAdapter.PlayingUserItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playing_user, parent, false);
        return new PlayingUserItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PlayingUserAdapter.PlayingUserItemHolder holder, final int position) {
        final PlayingUserItem playingUserItem = playingUserItemList.get(position);
        final CardItem cardItem = playingUserItem.getCardItem();

//        ConstraintLayout.LayoutParams layout = (ConstraintLayout.LayoutParams)holder.container.getLayoutParams();
//        if (cardItem.name.equals("Sói đen")) {
//            layout.setMarginStart(240);
//            layout.setMarginEnd(12);
//        } else {
//            layout.setMarginStart(12);
//            layout.setMarginEnd(240);
//        }

        holder.tvUserName.setText(playingUserItem.getName());
        holder.tvUserRole.setText(cardItem.name);
        Picasso.with(mContext).load(cardItem.image)
                .error(R.mipmap.ic_launcher_round)
                .into(holder.ivCard);

        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getRandomColor();
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(String.valueOf(playingUserItem.getShortName()),color);
        holder.tvUserShortName.setImageDrawable(drawable);

//        holder.ivCard.setOnClickListener(new View.OnClickListener() {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!playingUserItem.isDied()) {
                    String msg = String.format("%1$s (%2$s) %3$s",
                            playingUserItem.getName(),
                            cardItem.name.toLowerCase(),
                            mContext.getString(R.string.isDied));
                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                }
                YoYo.with(Techniques.FlipInX)
                        .duration(700)
                        .onStart(new YoYo.AnimatorCallback() {
                            @Override
                            public void call(Animator animator) {
                                if (!playingUserItem.isDied()) {
                                    holder.itemView.setAlpha(0.3f);
                                    holder.container.setScaleX(0.8f);
                                    holder.container.setScaleY(0.8f);
                                    playingUserItem.setDied(true);
                                } else {
                                    holder.itemView.setAlpha(1f);
                                    holder.container.setScaleX(1f);
                                    holder.container.setScaleY(1f);
                                    playingUserItem.setDied(false);
                                }
                            }
                        })
                        .playOn(holder.container);
            }
        });
    }

    @Override
    public int getItemCount() {
        return playingUserItemList == null ? 0 : playingUserItemList.size();
    }


    public List<PlayingUserItem> getCardItemList() {
        return playingUserItemList;
    }


    class PlayingUserItemHolder extends RecyclerView.ViewHolder {

        View itemView;

        @BindView(R.id.tv_user_short_name)
        ImageView tvUserShortName;
        @BindView(R.id.tv_user_name)
        TextView tvUserName;
        @BindView(R.id.tv_user_role)
        TextView tvUserRole;
        @BindView(R.id.iv_card)
        CircleImageView ivCard;
        @BindView(R.id.v_item_user_container)
        View container;

        public PlayingUserItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }
}
