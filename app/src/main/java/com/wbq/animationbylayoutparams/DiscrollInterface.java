package com.wbq.animationbylayoutparams;

/**
 * 作者：${wbq} on 2017/1/10 11:34
 * 邮箱：wangbaiqiang@heigo.com.cn
 */

public interface DiscrollInterface {
    /**
     *  滑动的时候调用  控制动画的执行过程
     * @param ratio 0~1 动画执行的百分比
     */
    public void onDiscroll(float ratio);

    /**
     * 重置动画
     */
    public void onResetDiscroll();
}
