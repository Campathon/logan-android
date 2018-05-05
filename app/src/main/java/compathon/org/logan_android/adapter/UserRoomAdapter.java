package compathon.org.logan_android.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import compathon.org.logan_android.R;
import compathon.org.logan_android.common.MathUtils;
import compathon.org.logan_android.model.User;

/**
 * Created by Andy on 5/5/2018.
 */

public class UserRoomAdapter extends RecyclerView.Adapter<UserRoomAdapter.UserHolder> {

    private List<User> userList;
    private Context context;

    public UserRoomAdapter(Context context, List<User> userList) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_room, parent, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, final int position) {
        User user = userList.get(position);

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getRandomColor();
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(String.valueOf(user.username.charAt(0)),color); // radius in px

        holder.tvUsername.setText(user.username);
        holder.imvAvatar.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return userList == null ? 0 : userList.size();
    }


    class UserHolder extends RecyclerView.ViewHolder {

        View itemView;

        @BindView(R.id.tvUsername)
        TextView tvUsername;

        @BindView(R.id.imvUserAvatar)
        ImageView imvAvatar;

        public UserHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }
}
