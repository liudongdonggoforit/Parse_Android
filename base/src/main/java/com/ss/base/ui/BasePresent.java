package com.ss.base.ui;

public class BasePresent<V extends IView> implements IPresent<V> {
    private V v;

    @Override
    public void attachV(V view) {
        v = view;
    }

    @Override
    public void detachV() {
        v = null;
    }

    public V getV() {
        return v;
    }
}
