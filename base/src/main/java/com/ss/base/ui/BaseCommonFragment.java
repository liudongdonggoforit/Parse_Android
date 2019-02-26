package com.ss.base.ui;

public abstract class BaseCommonFragment<P extends BasePresent> extends BaseFragment<P> {
    /**
     * 首次初始化
     */
    private boolean mFirstInit = true;

    /**
     * 是否可见
     */
    private boolean mVisible;

    @Override
    public void onResume() {
        super.onResume();
        //若首次初始化,默认可见并开启友盟统计
        if (mFirstInit) {
            mVisible = true;
            mFirstInit = false;
//            if (!hasFragment()) MobclickAgent.onPageStart(getClass().getName()); //统计页面
            return;
        }

        //若当前界面可见,调用友盟开启跳转统计
        if (mVisible) {
//            if (!hasFragment()) MobclickAgent.onPageStart(getClass().getName()); //统计页面
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            mVisible = false;
//            if (!hasFragment()) MobclickAgent.onPageEnd(getClass().getName()); //统计页面
        }
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mVisible = true;
//            if (!hasFragment()) MobclickAgent.onPageStart(getClass().getName()); //统计页面
        }
    }

    @Override
    public void onPause() {
        //若当前界面可见,调用友盟结束跳转统计
        if (mVisible) {
//            if (!hasFragment()) MobclickAgent.onPageEnd(getClass().getName()); //统计页面
        }
        super.onPause();
    }

    protected boolean hasFragment() {
        return false;
    }
}
