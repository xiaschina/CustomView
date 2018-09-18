package com.xias.plugins.customview.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.xias.plugins.customview.R;

/**
 * Created by XIAS on 2018/9/18.
 */

public class CProgressBar extends View {
    private int height;
    private int width;

    /**
     * 进度相关参数
     */
    private Paint paint;
    private RectF rectF;
    private LinearGradient gradient;//线性颜色渐变

    /**
     * 带边框的圆角矩形相关参数
     */
    private Paint bgPaint;
    private RectF bgRectF;

    /**
     * 背景圆角矩形相关参数
     */
    private Paint bgPaintFill;
    private RectF bgRectFFill;

    private int max;//进度条最大值

    private float currentProgress = 0;//上一次更新进度条的值

    private int stokeWidth = 1;//边框宽度
    private int lineColor = Color.parseColor("#FFFFFF");//边框颜色
    private int stokesColor = Color.parseColor("#FFFFFF");//填充背景颜色
    private int corners = 48;//圆角角度
    private boolean isDrawLine = false;//是否绘制边框

    private ValueAnimator animator;//动画

    private int[] colors = {Color.parseColor("#FD7F13"),
            Color.parseColor("#51F09A"),
            Color.parseColor("#26A8D2")};

    public CProgressBar(Context context) {
        this(context, null);
    }

    public CProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CProgressBar);
        if (typedArray != null) {
            corners = typedArray.getDimensionPixelSize(R.styleable.CProgressBar_corners, corners);
            stokeWidth = typedArray.getDimensionPixelSize(R.styleable.CProgressBar_lineWidth, stokeWidth);
            lineColor = typedArray.getColor(R.styleable.CProgressBar_lineColor, lineColor);
            stokesColor = typedArray.getColor(R.styleable.CProgressBar_stokesColor, stokesColor);
            isDrawLine = typedArray.getBoolean(R.styleable.CProgressBar_isDrawLine, isDrawLine);
            typedArray.recycle();
        }
        init();
    }

    private void init() {

        rectF = new RectF(0, 0, 0, 0);

        if (isDrawLine) {
            bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            bgPaint.setStyle(Paint.Style.STROKE);
            bgPaint.setAntiAlias(true);
            bgPaint.setStrokeWidth(stokeWidth);
            bgPaint.setColor(lineColor);
        }

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);

        bgPaintFill = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaintFill.setStyle(Paint.Style.FILL);
        bgPaintFill.setColor(stokesColor);

        gradient = new LinearGradient(width, height, 0, 0, colors, null, Shader.TileMode.MIRROR);
        paint.setShader(gradient);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        width = w;
        if (isDrawLine)
            bgRectF = new RectF(stokeWidth, stokeWidth, width - stokeWidth, height - stokeWidth);
        bgRectFFill = new RectF(stokeWidth, stokeWidth, width - stokeWidth, height - stokeWidth);
        start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
        drawProgress(canvas);
    }

    //开始动画
    private void start() {
        ValueAnimator animator = ValueAnimator.ofInt(0, height - 2 * stokeWidth).setDuration(1500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (isDrawLine)
                    bgRectF = new RectF(stokeWidth, height - stokeWidth - (int) valueAnimator.getAnimatedValue(), width - stokeWidth, height - stokeWidth);
                bgRectFFill = new RectF(stokeWidth, height - stokeWidth - (int) valueAnimator.getAnimatedValue(), width - stokeWidth, height - stokeWidth);
                invalidate();
            }
        });
        animator.start();
    }

    //画统一的背景，便于执行动画
    private void drawBackground(final Canvas canvas) {
        if (isDrawLine)
            drawBg(canvas);
        drawBgFill(canvas);
    }

    //画边框
    private void drawBg(Canvas canvas) {
        canvas.drawRoundRect(bgRectF, corners, corners, bgPaint);
    }

    //画背景
    private void drawBgFill(Canvas canvas) {
        canvas.drawRoundRect(bgRectFFill, corners, corners, bgPaintFill);
    }

    //画进度
    private void drawProgress(Canvas canvas) {
        canvas.drawRoundRect(rectF, corners, corners, paint);
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setProgress(final int progress) {
        //若上次动画未执行完则取消执行
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
        animator = ValueAnimator.ofFloat(0, progress * (height - 4 * stokeWidth) * 1.0f / max).setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (currentProgress < (float) valueAnimator.getAnimatedValue()) {
                    rectF = new RectF(2 * stokeWidth, height - 2 * stokeWidth - (float) valueAnimator.getAnimatedValue(), width - 2 * stokeWidth, height - 2 * stokeWidth);
                    gradient = new LinearGradient(width - 2 * stokeWidth, height - 2 * stokeWidth, 2 * stokeWidth, height - 2 * stokeWidth - progress, colors, null, Shader.TileMode.MIRROR);
                    paint.setShader(gradient);
                    invalidate();
                }
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                currentProgress = progress * height * 1.0f / max;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();

    }

    public void setColors(int[] colors) {
        if (colors == null)
            return;
        this.colors = colors;
    }

}
