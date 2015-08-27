package com.a0000.showmore;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by Administrator on 2015/8/27.
 */
public class LabelAdapter extends RecyclerView.Adapter<LabelAdapter.ViewHolder> {

    private Random rand = new Random();
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTvLabel;

        public ViewHolder(View v) {
            super(v);
            mTvLabel = (TextView) v.findViewById(R.id.tv_label);
        }
    }

    @Override
    public LabelAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_label, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LabelAdapter.ViewHolder holder, int position) {
        TextView tv = holder.mTvLabel;
        Context context = tv.getContext();
        tv.setBackgroundColor(Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
        tv.setText("精品");
        tv.setGravity(Gravity.BOTTOM);
        int width = 150;
        int height = 40;
        int padding = 40;
        int translationX = 88;
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(dp2px(width, context), dp2px(height, context));
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        tv.setPadding(dp2px(padding, context), 0, 0, 0);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        tv.setLayoutParams(lp);

        tv.setRotation(45);
        tv.setTranslationX(dp2px(translationX, context));
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public static int dp2px(float dp, Context context) {
        final float scale = getDisplayMetrics(context).density;
        return (int) (dp * scale + 0.5f);
    }
    private static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }
}
