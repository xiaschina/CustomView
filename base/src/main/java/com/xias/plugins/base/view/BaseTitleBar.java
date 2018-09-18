package com.xias.plugins.base.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xias.plugins.base.R;

/**
 * Created by XIAS on 2018/8/14.
 */

public class BaseTitleBar extends FrameLayout implements View.OnClickListener {

    private ImageView back;

    private TextView title;

    public OnBackClickListener listener;

    private ProgressBar progressBar;

    public void setOnBackClickListener(OnBackClickListener listener) {
        this.listener = listener;
    }

    public BaseTitleBar(Context context) {
        this(context, null);
    }

    public BaseTitleBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_title_bar, null);
        back = view.findViewById(R.id.view_title_bar_iv);
        title = view.findViewById(R.id.view_title_bar_title);
        progressBar = view.findViewById(R.id.view_title_bar_progressbar);
        back.setOnClickListener(this);
        addView(view);
    }

    public void isShowProgressbar(boolean visible) {
        if (visible) {
            if (progressBar.getVisibility() == View.VISIBLE)
                return;
            progressBar.setVisibility(View.VISIBLE);
        } else {
            if (progressBar.getVisibility() != View.VISIBLE)
                return;
            progressBar.setVisibility(View.GONE);
        }
    }

    public void isShowImage(boolean visible) {
        if (visible) {
            if (back.getVisibility() == View.VISIBLE)
                return;
            back.setVisibility(View.VISIBLE);
        } else {
            if (back.getVisibility() != View.VISIBLE)
                return;
            back.setVisibility(View.GONE);
        }
    }

    public void setDrawable(int resId) {
        back.setImageDrawable(getContext().getResources().getDrawable(resId));
    }

    public void setText(String text) {
        title.setText(text);
    }

    public void setTextColor(int color) {
        title.setTextColor(color);
    }

    public void setTextSize(float size) {
        title.setTextSize(size);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.view_title_bar_iv) {
            if (listener != null)
                listener.onClickListener();
            else
                ((Activity) getContext()).finish();
        }
    }

    public interface OnBackClickListener {
        void onClickListener();
    }
}
