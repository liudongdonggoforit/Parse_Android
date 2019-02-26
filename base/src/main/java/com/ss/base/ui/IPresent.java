package com.ss.base.ui;

public interface IPresent<V> {
    void attachV(V view);

    void detachV();
}
