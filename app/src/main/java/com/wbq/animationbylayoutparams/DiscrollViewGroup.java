package com.wbq.animationbylayoutparams;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 作者：${wbq} on 2017/1/10 10:51
 * 邮箱：wangbaiqiang@heigo.com.cn
 */

public class DiscrollViewGroup extends FrameLayout implements DiscrollInterface{
    private static final int TRANSLATION_FROM_TOP = 0x01;
    private static final int TRANSLATION_FROM_BOTTOM = 0x02;
    private static final int TRANSLATION_FROM_LEFT = 0x04;;
    private static final int TRANSLATION_FROM_RIGHT = 0x08;

    private static ArgbEvaluator sArgbEvaluator = new ArgbEvaluator();

    private float mDiscrollveThreshold;
    private int mDiscrollveFromBgColor;//背景颜色起始值
    private int mDiscrollveToBgColor;//背景颜色的结束值
    private boolean mDiscrollveAlpha;//是否需要 透明度变化
    private int mDiscrollveTranslation;//平移值
    private boolean mDiscrollveScaleX;//是否需要x轴方向的缩放
    private boolean mDiscrollveScaleY;//是否需要y轴方向的缩放

    private int mWidth;
    private int mHeight;

    public DiscrollViewGroup(Context context) {
        super(context);
    }

    public DiscrollViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DiscrollViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
            mHeight = h;
        //一进来就重置我们的动画
        onResetDiscroll();
    }

    public void setmDiscrollveFromBgColor(int mDiscrollveFromBgColor) {
        this.mDiscrollveFromBgColor = mDiscrollveFromBgColor;
    }

    public void setmDiscrollveToBgColor(int mDiscrollveToBgColor) {
        this.mDiscrollveToBgColor = mDiscrollveToBgColor;
    }

    public void setmDiscrollveAlpha(boolean mDiscrollveAlpha) {
        this.mDiscrollveAlpha = mDiscrollveAlpha;
    }

    public void setmDiscrollveTranslation(int mDiscrollveTranslation) {
        this.mDiscrollveTranslation = mDiscrollveTranslation;
        /**考虑到误操作让控件从上和下同时平移是错误的*/
        if(isDiscrollTranslationFrom(TRANSLATION_FROM_BOTTOM) && isDiscrollTranslationFrom(TRANSLATION_FROM_TOP)) {
            throw new IllegalArgumentException("cannot translate from bottom and top");
        }
        /**考虑到误操作让控件从左和右同时平移是错误的*/
        if(isDiscrollTranslationFrom(TRANSLATION_FROM_LEFT) && isDiscrollTranslationFrom(TRANSLATION_FROM_RIGHT)) {
            throw new IllegalArgumentException("cannot translate from left and right");
        }
    }

    public void setmDiscrollveScaleX(boolean mDiscrollveScaleX) {
        this.mDiscrollveScaleX = mDiscrollveScaleX;
    }

    public void setmDiscrollveScaleY(boolean mDiscrollveScaleY) {
        this.mDiscrollveScaleY = mDiscrollveScaleY;
    }

    public void setmDiscrollveThreshold(float mDiscrollveThreshold) {
        if(mDiscrollveThreshold < 0.0f || mDiscrollveThreshold > 1.0f) {
            throw new IllegalArgumentException("threshold must be >= 0.0f and <= 1.0f");
        }
        this.mDiscrollveThreshold = mDiscrollveThreshold;
    }

    @Override
    public void onDiscroll(float ratio) {
        //判断当前控件是否有alpha动画
        if(mDiscrollveAlpha) {
            setAlpha(ratio);
        }
        if(mDiscrollveScaleX) {
            setScaleX(ratio);
        }
        if(mDiscrollveScaleY) {
            setScaleY(ratio);
        }
        //判断平移
        if(isDiscrollTranslationFrom(TRANSLATION_FROM_BOTTOM)) {
            setTranslationY(mHeight*(1-ratio));//向上滑动
        }
        if(isDiscrollTranslationFrom(TRANSLATION_FROM_TOP)) {
            setTranslationY(-mHeight*(1-ratio));
        }
        if(isDiscrollTranslationFrom(TRANSLATION_FROM_LEFT)) {
            setTranslationX(-mWidth*(1-ratio));
        }
        if(isDiscrollTranslationFrom(TRANSLATION_FROM_RIGHT)) {
            setTranslationX(mWidth*(1-ratio));
        }

    }

    /**
     * 如果给的属性是left|top这种形式的就可以判断是否包含了top left bottom right
     * @param translationMask
     * @return
     */
    private boolean isDiscrollTranslationFrom(int translationMask){
        if(mDiscrollveTranslation==-1) {
            return false;
        }
        return (mDiscrollveTranslation&translationMask)==translationMask;
    }

    @Override
    public void onResetDiscroll() {
        if(mDiscrollveAlpha) {
            setAlpha(0);
        }
        if(mDiscrollveScaleX) {
            setScaleX(0);
        }
        if(mDiscrollveScaleY) {
            setScaleY(0);
        }
        //判断平移
        if(isDiscrollTranslationFrom(TRANSLATION_FROM_BOTTOM)) {
            setTranslationY(mHeight);//向上滑动
        }
        if(isDiscrollTranslationFrom(TRANSLATION_FROM_TOP)) {
            setTranslationY(-mHeight);
        }
        if(isDiscrollTranslationFrom(TRANSLATION_FROM_LEFT)) {
            setTranslationX(-mWidth);
        }
        if(isDiscrollTranslationFrom(TRANSLATION_FROM_RIGHT)) {
            setTranslationX(mWidth);
        }
    }
}
