package com.ss.base.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;

public interface ILoader {

    void init(ImageProvider imageProvider);

    void loadNet(ImageView target, String url, RequestOptions options);

    void loadNet(Context context, String url, RequestOptions options, SimpleTarget target);

    void loadResource(ImageView target, int resId, RequestOptions options);

    void loadAssets(ImageView target, String assetName, RequestOptions options);

    void loadFile(ImageView target, File file, RequestOptions options);

    void loadBitmap(Context context, String url, SimpleTarget<Bitmap> target);

    File down(Context context, String url, RequestOptions options);

    void clearMemoryCache(Context context);

    void clearDiskCache(Context context);

    void resume(Context context);

    void pause(Context context);

}
