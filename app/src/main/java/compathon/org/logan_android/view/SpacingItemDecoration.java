package compathon.org.logan_android.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;


public class SpacingItemDecoration extends RecyclerView.ItemDecoration {
    private int spacing;
    private int type;

    public SpacingItemDecoration(Context context, int type, int padding) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        spacing = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, padding, metrics);
        this.type = type;
    }

    @Override
    public final void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        if (position != state.getItemCount() - 1) {
            if (type == 0) {
                outRect.bottom = spacing;
            } else {
                outRect.right = spacing;
            }
        }
    }
}
