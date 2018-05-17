package compathon.org.logan_android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import compathon.org.logan_android.R;
import compathon.org.logan_android.model.CardItem;
import compathon.org.logan_android.model.PlayingUserItem;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;


public class PlayingUserAdapter extends RecyclerView.Adapter<PlayingUserAdapter.PlayingUserItemHolder> {

    private List<PlayingUserItem> playingUserItemList;
    private Context context;

    public PlayingUserAdapter(Context context, List<PlayingUserItem> playingUserItemList) {
        this.playingUserItemList = playingUserItemList;
        this.context = context;
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
        CardItem cardItem = playingUserItem.getCardItem();

        holder.tvUserShorName.setText(playingUserItem.getShortName());
        holder.tvUserName.setText(playingUserItem.getName());
        holder.tvUserRole.setText(cardItem.name);
        Picasso.with(context).load(cardItem.image)
                .error(R.mipmap.ic_launcher_round)
                .into(holder.ivCard);

        Log.i(TAG, playingUserItem.getName());
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
        TextView tvUserShorName;
        @BindView(R.id.tv_user_name)
        TextView tvUserName;
        @BindView(R.id.tv_user_role)
        TextView tvUserRole;
        @BindView(R.id.iv_card)
        CircleImageView ivCard;

        public PlayingUserItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }
}
