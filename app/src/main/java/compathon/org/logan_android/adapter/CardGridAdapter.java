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

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import compathon.org.logan_android.R;
import compathon.org.logan_android.common.MathUtils;
import compathon.org.logan_android.model.CardItem;

/**
 * Created by Andy on 5/6/2018.
 */

public class CardGridAdapter extends RecyclerView.Adapter<CardGridAdapter.CardItemHolder> {

    private List<CardItem> cardItemList;
    private Context context;

    public CardGridAdapter(Context context, List<CardItem> cardItemList) {
        this.cardItemList = cardItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public CardGridAdapter.CardItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_grid, parent, false);
        return new CardGridAdapter.CardItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardGridAdapter.CardItemHolder holder, final int position) {
        CardItem cardItem = cardItemList.get(position);

        holder.tvName.setText(cardItem.name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogNumber(position);
            }
        });

//        if (cardItemList.get(position).quantity != 0) {
//            holder.tvQuantity.setText(String.valueOf(cardItem.quantity));
//        } else {
//            holder.tvQuantity.setText("");
//        }
//
//        holder.tvAlias.setText(cardItem.alias);

        Picasso.with(context).load(cardItem.image)
                .error(R.mipmap.ic_launcher_round)
                .into(holder.imvThumbnail);

        holder.imvThumbnail.post(new Runnable() {
            @Override
            public void run() {
                int width = holder.imvThumbnail.getWidth();
                holder.imvThumbnail.getLayoutParams().height = width;
                holder.imvThumbnail.requestLayout();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardItemList == null ? 0 : cardItemList.size();
    }

    private void showDialogNumber(final int position) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_choose_quantity);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(MathUtils.ScreenSize(context)[0] * 7 / 8, WindowManager.LayoutParams.WRAP_CONTENT);


        dialog.findViewById(R.id.tvR0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardItemList.get(position).quantity = 0;
                dialog.dismiss();
                notifyDataSetChanged();
            }
        });

        dialog.findViewById(R.id.tvR1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardItemList.get(position).quantity = 1;
                dialog.dismiss();
                notifyDataSetChanged();
            }
        });

        dialog.findViewById(R.id.tvR2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardItemList.get(position).quantity = 2;
                dialog.dismiss();
                notifyDataSetChanged();
            }
        });

        dialog.findViewById(R.id.tvR3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardItemList.get(position).quantity = 3;
                dialog.dismiss();
                notifyDataSetChanged();
            }
        });

        dialog.findViewById(R.id.tvR4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardItemList.get(position).quantity = 4;
                dialog.dismiss();
                notifyDataSetChanged();
            }
        });

        dialog.findViewById(R.id.tvR5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardItemList.get(position).quantity = 5;
                dialog.dismiss();
                notifyDataSetChanged();
            }
        });

        dialog.show();
    }

    class CardItemHolder extends RecyclerView.ViewHolder {

        View itemView;

        @BindView(R.id.tvName)
        TextView tvName;
        //@BindView(R.id.tvQuantity)
        //TextView tvQuantity;
        @BindView(R.id.imvThumbnail)
        ImageView imvThumbnail;
        //@BindView(R.id.tvAlias)
        //TextView tvAlias;

        public CardItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }
}
