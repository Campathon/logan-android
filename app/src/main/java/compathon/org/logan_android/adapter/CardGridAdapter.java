package compathon.org.logan_android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import compathon.org.logan_android.R;
import compathon.org.logan_android.model.CardItem;

/**
 * Created by Andy on 5/6/2018.
 */

public class CardGridAdapter extends RecyclerView.Adapter<CardGridAdapter.CardItemHolder> {

    private List<CardItem> cardItemList;
    private Context mContext;
    private boolean isHost;

    public CardGridAdapter(Context context, List<CardItem> cardItemList, boolean isHost) {
        this.cardItemList = cardItemList;
        this.mContext = context;
        this.isHost = isHost;
    }

    @NonNull
    @Override
    public CardGridAdapter.CardItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_grid, parent, false);
        return new CardGridAdapter.CardItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardGridAdapter.CardItemHolder holder, final int position) {
        final CardItem cardItem = cardItemList.get(position);

        holder.tvName.setText(cardItem.name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDialogNumber(position);
                Toast.makeText(mContext, cardItem.name, Toast.LENGTH_SHORT).show();
            }
        });

        updateQuantity(cardItem, holder);

//        if (cardItemList.get(position).quantity != 0) {
//            holder.tvQuantity.setText(String.valueOf(cardItem.quantity));
//        } else {
//            holder.tvQuantity.setText("");
//        }
//
//        holder.tvAlias.setText(cardItem.alias);

        Picasso.with(mContext).load(cardItem.image)
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

        if (isHost) {
            holder.tvDecrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cardItem.quantity--;
                    if (cardItem.quantity < 0) cardItem.quantity = 0;
                    updateQuantity(cardItem, holder);
                    cardItemList.get(position).quantity = cardItem.quantity;
                }
            });
            holder.tvIncrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cardItem.quantity++;
                    updateQuantity(cardItem, holder);
                    cardItemList.get(position).quantity = cardItem.quantity;
                }
            });
        } else {
            holder.tvIncrease.setVisibility(View.GONE);
            holder.tvDecrease.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return cardItemList == null ? 0 : cardItemList.size();
    }

    private void updateQuantity(CardItem cardItem, CardItemHolder holder) {
        if (cardItem.quantity > 0) {
            holder.tvTotal.setText(String.valueOf(cardItem.quantity));
            holder.tvTotal.setVisibility(View.VISIBLE);
        } else {
            holder.tvTotal.setVisibility(View.INVISIBLE);
        }
    }

    public List<CardItem> getCardItemList() {
        return cardItemList;
    }

//    private void showDialogNumber(final int position) {
//
//        final Dialog dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_choose_quantity);
//        dialog.setCanceledOnTouchOutside(true);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.getWindow().setLayout(MathUtils.ScreenSize(context)[0] * 7 / 8, WindowManager.LayoutParams.WRAP_CONTENT);
//
//
//        dialog.findViewById(R.id.tvR0).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cardItemList.get(position).quantity = 0;
//                dialog.dismiss();
//                notifyDataSetChanged();
//            }
//        });
//
//        dialog.findViewById(R.id.tvR1).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cardItemList.get(position).quantity = 1;
//                dialog.dismiss();
//                notifyDataSetChanged();
//            }
//        });
//
//        dialog.findViewById(R.id.tvR2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cardItemList.get(position).quantity = 2;
//                dialog.dismiss();
//                notifyDataSetChanged();
//            }
//        });
//
//        dialog.findViewById(R.id.tvR3).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cardItemList.get(position).quantity = 3;
//                dialog.dismiss();
//                notifyDataSetChanged();
//            }
//        });
//
//        dialog.findViewById(R.id.tvR4).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cardItemList.get(position).quantity = 4;
//                dialog.dismiss();
//                notifyDataSetChanged();
//            }
//        });
//
//        dialog.findViewById(R.id.tvR5).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cardItemList.get(position).quantity = 5;
//                dialog.dismiss();
//                notifyDataSetChanged();
//            }
//        });
//
//        dialog.show();
//    }

    class CardItemHolder extends RecyclerView.ViewHolder {

        View itemView;

        @BindView(R.id.tvName)
        TextView tvName;
        //@BindView(R.id.tvQuantity)
        //TextView tvQuantity;
        @BindView(R.id.tv_total)
        TextView tvTotal;
        @BindView(R.id.tv_decrease)
        TextView tvDecrease;
        @BindView(R.id.tv_increase)
        TextView tvIncrease;
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
