package com.a0000.showmore;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2015/5/9.
 * RecyclerView 分隔线
 */
public class RecyclerViewDivider extends RecyclerView.ItemDecoration {
    Paint paint = new Paint();

    public RecyclerViewDivider(Context context) {
        setDividerColor(context, R.color.gray);
    }

    public void setDividerColor(Context context, int colorId) {
        paint.setColor(context.getResources().getColor(colorId));
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        for (int i = 0, size = parent.getChildCount(); i < size; i++) {
            View child = parent.getChildAt(i);
            c.drawLine(child.getLeft(), child.getBottom(), child.getRight(), child.getBottom(), paint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
    }
}
