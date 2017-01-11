package com.wbq.animationbylayoutparams;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * 作者：${wbq} on 2017/1/10 10:30
 * 邮箱：wangbaiqiang@heigo.com.cn
 */

public class DiscrollLinearLayout extends LinearLayout {
    public DiscrollLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(asDiscrollvable(child,(MyLayoutParams)params), index, params);
    }

    /**
     * 加入我们的viewgroup 然后再把child加入到我们的viewgroup中
     * @param child
     * @param params
     * @return
     */
    private View asDiscrollvable(View child, MyLayoutParams params) {
        if(!isDiscrollvable(params)){
            return child;
        }
        DiscrollViewGroup discrollViewGroup=new DiscrollViewGroup(getContext());
        discrollViewGroup.setmDiscrollveAlpha(params.mDiscrollAlpha);
        discrollViewGroup.setmDiscrollveTranslation(params.mDiscrollTranslation);
        discrollViewGroup.setmDiscrollveScaleX(params.mDiscrollScaleX);
        discrollViewGroup.setmDiscrollveScaleY(params.mDiscrollScaleY);
        discrollViewGroup.setmDiscrollveThreshold(params.mDiscrollThreshold);
        discrollViewGroup.setmDiscrollveFromBgColor(params.mDiscrollFromBgColor);
        discrollViewGroup.setmDiscrollveToBgColor(params.mDiscrollToBgColor);
        discrollViewGroup.addView(child);
        return discrollViewGroup;
    }
    /**
     * 重写checkLayoutParams
     * @param p
     * @return
     */
    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof MyLayoutParams;
    }

    /**
     * 重写generateDefaultLayoutParams
     * @return
     */
    @Override
    protected LinearLayout.LayoutParams generateDefaultLayoutParams() {
        return new MyLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }
    /**
     * 重写generateLayoutParams
     * @param p
     * @return
     */
    @Override
    protected LinearLayout.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new MyLayoutParams(p.width, p.height);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MyLayoutParams(getContext(),attrs);
    }

    /**
     * 判断是否是我们定义的LayoutParams
     * @param lp
     * @return
     */
    private boolean isDiscrollvable(MyLayoutParams lp) {
        return lp.mDiscrollAlpha ||
                lp.mDiscrollTranslation != -1 ||
                lp.mDiscrollScaleX ||
                lp.mDiscrollScaleY ||
                (lp.mDiscrollFromBgColor != -1 && lp.mDiscrollToBgColor != -1);
    }
    public static class MyLayoutParams extends LinearLayout.LayoutParams{
        private int mDiscrollFromBgColor;
        private int mDiscrollToBgColor;
        private float mDiscrollThreshold;
        public boolean mDiscrollAlpha;
        public boolean mDiscrollScaleX;
        public boolean mDiscrollScaleY;
        private int mDiscrollTranslation;
        public MyLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.DiscrollView_LayoutParams);
            try {
                mDiscrollAlpha = a.getBoolean(R.styleable.DiscrollView_LayoutParams_discrollve_alpha, false);
                mDiscrollScaleX = a.getBoolean(R.styleable.DiscrollView_LayoutParams_discrollve_scaleX, false);
                mDiscrollScaleY = a.getBoolean(R.styleable.DiscrollView_LayoutParams_discrollve_scaleY, false);
                mDiscrollTranslation = a.getInt(R.styleable.DiscrollView_LayoutParams_discrollve_translation, -1);
                mDiscrollThreshold = a.getFloat(R.styleable.DiscrollView_LayoutParams_discrollve_threshold, 0.0f);
                mDiscrollFromBgColor = a.getColor(R.styleable.DiscrollView_LayoutParams_discrollve_fromBgColor, -1);
                mDiscrollToBgColor = a.getColor(R.styleable.DiscrollView_LayoutParams_discrollve_toBgColor, -1);
            } finally {
                a.recycle();
            }
        }
        public MyLayoutParams(int width, int height) {
            super(width, height);
        }
    }
}
