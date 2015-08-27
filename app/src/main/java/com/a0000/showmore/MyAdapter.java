package com.a0000.showmore;

import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Description> datas = new ArrayList<Description>();

    public List<Description> getDatas() {
        return datas;
    }

    private Map<ViewHolder, Runnable> runnableMap = new HashMap<ViewHolder, Runnable>();

    private Handler mHandler = new Handler(Looper.myLooper());
    private Map<Description, List<TextView>> descViewMaps = new HashMap<Description, List<TextView>>();
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (int) view.getTag(R.layout.item);
            Description desc = datas.get(position);
            desc.setShowMore(!desc.isShowMore());
            notifyItemChanged(position);
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTvItemPosition;
        private final LinearLayout mLlDescs;
        private final View mLlShowMore;
        private final TextView mTvDescCount;

        public ViewHolder(View v) {
            super(v);
            mTvItemPosition = (TextView) v.findViewById(R.id.item_position);
            mLlDescs = (LinearLayout) v.findViewById(R.id.ll_descs);
            mLlShowMore = v.findViewById(R.id.ll_show_more);
            mTvDescCount = (TextView) v.findViewById(R.id.tv_desc_count);
        }
    }

    public MyAdapter() {
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Description desc = datas.get(position);
        holder.mTvItemPosition.setText("第" + position + "个条目");
        List<String> descList = desc.getDescList();
        if (descList==null) {
            holder.mLlShowMore.setVisibility(View.GONE);
            holder.mLlDescs.setVisibility(View.GONE);
        } else {
            holder.mLlShowMore.setVisibility(View.GONE);
            holder.mLlDescs.setVisibility(View.VISIBLE);
            int size = descList.size();
            if (size > SHOW_SIZE) {
                holder.mLlShowMore.setVisibility(View.VISIBLE);
                holder.mTvDescCount.setText(size+"个描述");
            }
            holder.mLlShowMore.setTag(R.layout.item, position);
            holder.mLlShowMore.setOnClickListener(onClickListener);

            setDescView(holder, desc);

            if (descList.size()>SHOW_SIZE) {
                setShowMoreView(holder);
            }
        }
    }


    @Override
    public int getItemCount() {
        return datas.size();
    }

    private void setShowMoreView(final ViewHolder holder) {
        Runnable runnable = runnableMap.get(holder);
        if (runnable==null) {
            runnable = new Runnable() {
                @Override
                public void run() {

                    int childCount = holder.mLlDescs.getChildCount();
                    if (childCount > 0) {
                        View v = holder.mLlDescs.getChildAt(childCount - 1);
                        if (v instanceof TextView) {

                            TextView descView = (TextView) v;

                            int lineCount = descView.getLineCount();
                            if (lineCount > 0) {
                                String text = descView.getText().toString();
                                Layout layout = descView.getLayout();
                                int start = 0;
                                int end;
                                String[] line = new String[lineCount];
                                if (lineCount > 1) {
                                    start = layout.getLineEnd(lineCount - 2);
                                }
                                end = layout.getLineEnd(lineCount - 1);

                                Rect bounds2 = new Rect();
                                Paint textPaint = descView.getPaint();

                                textPaint.getTextBounds(text, start, end, bounds2);
                                int width3 = bounds2.width();

                                int left = descView.getLeft();

                                View parent = (View) descView.getParent();
                                int width = parent.getWidth();

                                int width1 = holder.mLlShowMore.getWidth();

                                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.mLlShowMore.getLayoutParams();
                                if ((width - left - width3) > width1) {
                                    params.addRule(RelativeLayout.BELOW, 0);
                                } else {
                                    params.addRule(RelativeLayout.BELOW, R.id.ll_descs);
                                }
                                holder.mLlShowMore.setLayoutParams(params);
                            }
                        }
                    }
                }
            };
            runnableMap.put(holder, runnable);
        }
        mHandler.postDelayed(runnable, 20);
    }

    // 默认显示描述数量
    private final static int SHOW_SIZE = 2;
    private void setDescView(ViewHolder holder, Description desc) {
        holder.mLlDescs.removeAllViews();
        List<String> descList = desc.getDescList();
        if (descList!=null) {
            int size = descList.size();
            holder.mLlDescs.setVisibility(View.VISIBLE);
            holder.mTvDescCount.setText(size + "个描述");
            if (size > SHOW_SIZE) {
                holder.mLlShowMore.setVisibility(View.VISIBLE);
            } else {
                holder.mLlShowMore.setVisibility(View.GONE);
            }

            List<TextView> descViews = descViewMaps.get(desc);
            if (descViews==null) {
                descViews = new ArrayList<TextView>();
                int i = 1;
                for (String descStr : descList) {
                    TextView descView = new TextView(holder.itemView.getContext());
                    descView.setText(i++ + "__" + descStr);
                    descViews.add(descView);
                }
                descViewMaps.put(desc, descViews);
            }
            for (int i=0; i<descViews.size(); i++) {
                if (!desc.isShowMore() && i>=SHOW_SIZE) {
                    return;
                }
                TextView descView = descViews.get(i);
                ViewGroup parent = (ViewGroup) descView.getParent();
                if (parent!=null) {
                    parent.removeView(descView);
                }
                holder.mLlDescs.addView(descView);
            }
        } else {
            holder.mLlDescs.setVisibility(View.GONE);
        }
    }
}