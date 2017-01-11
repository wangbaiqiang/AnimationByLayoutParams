package com.wbq.animationbylayoutparams;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * 作者：${wbq} on 2017/1/9 18:15
 * 邮箱：wangbaiqiang@heigo.com.cn
 */

public class DiscrollView extends ScrollView {
    private DiscrollLinearLayout mContent;

    public DiscrollView(Context context) {
        this(context,null);
    }

    public DiscrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DiscrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setupFirstView();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(getChildCount() != 1) {
            throw new IllegalStateException("Discrollview must host one child.");
        }
        View content = getChildAt(0);
        if(!(content instanceof DiscrollLinearLayout)) {
            throw new IllegalStateException("Discrollview must host a DiscrollLinearLayout.");
        }
        mContent = (DiscrollLinearLayout) content;
        if(mContent.getChildCount() < 2) {
            throw new IllegalStateException("Discrollview must have at least 2 children.");
        }
    }

    private void setupFirstView() {
        View first = mContent.getChildAt(0);
        if(first!=null){
            first.getLayoutParams().height = getHeight();
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        int scrollViewHeight = getHeight();
        int scrollViewBottom = getAbsoluteBottom();
        int scrollViewHalfHeight = scrollViewHeight / 2;
        for(int index = 1;index<mContent.getChildCount();index++){
            View child = mContent.getChildAt(index);
            if(!(child instanceof DiscrollInterface)){
                continue;
            }
            DiscrollInterface discrollvable = (DiscrollInterface) child;
            int discrollvableTop = child.getTop();
            int discrollvableHeight = child.getHeight();
            int discrollvableAbsoluteTop = discrollvableTop - t;
            //这个view的下半部分
            if(scrollViewBottom - child.getBottom() < discrollvableHeight+scrollViewHalfHeight){
                //子view显示的时候执行
                if(discrollvableAbsoluteTop <= scrollViewHeight){
                    int visibleGap = scrollViewHeight - discrollvableAbsoluteTop;
                    discrollvable.onDiscroll( clamp(visibleGap / (float)discrollvableHeight,0.0f,1.0f));
                }else {
                    //子view还没显示的时候
                    discrollvable.onResetDiscroll();
                }
            }else{
                if(discrollvableAbsoluteTop <= scrollViewHalfHeight){
                    int visibleGap = scrollViewHalfHeight - discrollvableAbsoluteTop;
                    discrollvable.onDiscroll(clamp(visibleGap / (float)discrollvableHeight,0.0f,1.0f));
                }else{
                    discrollvable.onResetDiscroll();
                }
            }
        }
        //然后遍历里面所有的子控件控制动画的执行

    }
    private float clamp(float value, float max, float min) {
        return Math.max(Math.min(value,min),max);
    }

    public int getAbsoluteBottom() {
        View last = getChildAt(getChildCount()-1);
        if(last == null){
            return 0;
        }
        return last.getBottom();
    }
}
