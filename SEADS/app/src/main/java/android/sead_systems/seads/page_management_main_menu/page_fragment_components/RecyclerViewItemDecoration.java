package android.sead_systems.seads.page_management_main_menu.page_fragment_components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.sead_systems.seads.R;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RecyclerViewItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDivider;

    public RecyclerViewItemDecoration(Context context) {
        mDivider = ContextCompat.getDrawable(context,R.drawable.recycler_view_divider);
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        int right = recyclerView.getWidth();
        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            View curView = recyclerView.getChildAt(i);

            RecyclerView.LayoutParams layoutParams =
                    (RecyclerView.LayoutParams) curView.getLayoutParams();

            int top = curView.getBottom() + layoutParams.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(0, top, right, bottom);
            mDivider.draw(canvas);
        }
    }
}
