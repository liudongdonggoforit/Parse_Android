package com.ss.base.imageloader;

import com.bumptech.glide.load.model.LazyHeaders;

public interface ImageProvider {
    int getLoadingResId();

    int getLoadErrorResId();

    LazyHeaders configHeader();
}
