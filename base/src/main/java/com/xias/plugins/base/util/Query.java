package com.xias.plugins.base.util;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by XIAS on 2018/9/17.
 */

public class Query {
    private View contentView;
    private View currentView;
    private SparseArray<View> viewArray;

    private Query() {
    }

    private Query(View contentView) {
        this.contentView = contentView;
        viewArray = new SparseArray<>();
    }

    public static Query with(View contentView) {
        return new Query(contentView);
    }

    public Query id(@IdRes int resId) {
        currentView = viewArray.get(resId);
        if (currentView == null) {
            currentView = contentView.findViewById(resId);
            viewArray.put(resId, currentView);
        }
        return this;
    }

    public <T extends View> T view() {
        return (T) currentView;
    }

    public <T> T view(Class<T> c) {
        if (currentView == null) {
            return null;
        }

        if (c == null || !c.getName().equals(currentView.getClass().getName())) {
            throw new IllegalStateException();
        }
        return (T) currentView;
    }

    public Query image(@DrawableRes int resId) {
        if (currentView != null && currentView instanceof ImageView) {
            ((ImageView) currentView).setImageResource(resId);
        }
        return this;
    }

    public Query background(@DrawableRes int resId) {
        if (currentView != null) {
            currentView.setBackgroundResource(resId);
        }
        return this;
    }

    public Query background(Drawable drawable) {
        if (currentView != null) {
            currentView.setBackgroundDrawable(drawable);
        }
        return this;
    }

    public Query clicked(View.OnClickListener handler) {
        if (currentView != null) {
            currentView.setOnClickListener(handler);
        }
        return this;
    }

    public Query text(CharSequence text) {
        if (currentView != null && currentView instanceof TextView) {
            ((TextView) currentView).setText(text);
        }
        return this;
    }

    public Query textColor(@ColorInt int color) {
        if (currentView != null && currentView instanceof TextView) {
            ((TextView) currentView).setTextColor(color);
        }
        return this;
    }

    public Query textSize(int size, int... unit) {
        if (currentView != null && currentView instanceof TextView) {
            if (unit == null) {
                ((TextView) currentView).setTextSize(size);
            } else {
                ((TextView) currentView).setTextSize(unit[0], size);
            }
        }
        return this;
    }

    public Query visible() {
        if (currentView != null && currentView.getVisibility() != View.VISIBLE) {
            currentView.setVisibility(View.VISIBLE);
        }
        return this;
    }

    public Query gone() {
        if (currentView != null && currentView.getVisibility() != View.GONE) {
            currentView.setVisibility(View.GONE);
        }
        return this;
    }

    public Query invisible() {
        if (currentView != null && currentView.getVisibility() != View.INVISIBLE) {
            currentView.setVisibility(View.INVISIBLE);
        }
        return this;
    }

    public Query visibility(int visible) {
        if (currentView != null && currentView.getVisibility() != visible) {
            currentView.setVisibility(visible);
        }
        return this;
    }

    public Query enable(boolean enable) {
        if (currentView != null && currentView.isEnabled() != enable) {
            currentView.setEnabled(enable);
        }
        return this;
    }

    public void release() {
        currentView = null;
        contentView = null;
        viewArray.clear();
    }

    public boolean isVisibility() {
        return currentView != null && currentView.getVisibility() == View.VISIBLE;
    }
}
