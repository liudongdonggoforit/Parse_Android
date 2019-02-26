package com.ss.base.ui;

import android.os.Bundle;
import android.view.View;

public interface IView<P> {
    void bindUI(View rootView);

    void initData(Bundle savedInstanceState);

    int getOptionsMenuId();

    int getLayoutId();

    boolean useEventBus();

    P newP();
}
