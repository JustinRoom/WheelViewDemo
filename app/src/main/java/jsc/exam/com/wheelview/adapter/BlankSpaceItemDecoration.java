package jsc.exam.com.wheelview.adapter;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Black space line {@link android.support.v7.widget.RecyclerView.ItemDecoration} for {@link RecyclerView}.
 * <p>
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/WheelViewDemo" target="_blank">https://github.com/JustinRoom/WheelViewDemo</a>
 *
 * @author jiangshicheng
 */
public class BlankSpaceItemDecoration extends RecyclerView.ItemDecoration {

    int leftSpace;
    int topSpace;
    int rightSpace;
    int bottomSpace;
    boolean showFirstTop;
    boolean showLastBottom;

    public BlankSpaceItemDecoration(int leftSpace, int topSpace, int rightSpace, int bottomSpace) {
        this.leftSpace = leftSpace;
        this.topSpace = topSpace;
        this.rightSpace = rightSpace;
        this.bottomSpace = bottomSpace;
    }

    public void showFFTLB(boolean showFirstTop, boolean showLastBottom){
        this.showFirstTop = showFirstTop;
        this.showLastBottom = showLastBottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = leftSpace;
        outRect.top = topSpace;
        outRect.right = rightSpace;
        outRect.bottom = bottomSpace;
        RecyclerView.Adapter adapter = parent.getAdapter();
        if (adapter != null && adapter.getItemCount() > 1) {
            if (!showFirstTop && parent.getChildAdapterPosition(view) == 0)
                outRect.top = 0;

            if (!showLastBottom && parent.getChildAdapterPosition(view) == adapter.getItemCount() - 1)
                outRect.bottom = 0;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }
}
