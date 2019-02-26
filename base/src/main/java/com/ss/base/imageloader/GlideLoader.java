package com.ss.base.imageloader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.ss.sdk.GlideApp;
import com.ss.sdk.GlideRequest;
import com.ss.sdk.GlideRequests;

import java.io.File;

public class GlideLoader implements ILoader {
    private static final int RES_NONE = -1;
    private ImageProvider mImageProvider;

    @Override
    public void init(ImageProvider imageProvider) {
        mImageProvider = imageProvider;
    }

    public ImageProvider getImageProvider() {
        return mImageProvider;
    }

    @Override
    public void loadNet(ImageView target, String url, RequestOptions options) {
        Object model = mImageProvider == null || mImageProvider.configHeader() == null ? url : new GlideUrl(url, mImageProvider.configHeader());
        load(getRequestManager(target.getContext()).load(model), target, options);
    }

    @Override
    public void loadNet(Context context, String url, RequestOptions options, SimpleTarget target) {
        final GlideRequest request = getRequestManager(context).asFile().load(mImageProvider == null || mImageProvider.configHeader() == null ? url : new GlideUrl(url, mImageProvider.configHeader()));
        if (options == null) {
            options = new RequestOptions().placeholder(mImageProvider == null ? RES_NONE : mImageProvider.getLoadingResId())
                    .error(mImageProvider == null ? RES_NONE : mImageProvider.getLoadErrorResId());
        }

        if (options.getErrorId() == 0 && mImageProvider != null && mImageProvider.getLoadErrorResId() > 0) {
            options.error(mImageProvider.getLoadErrorResId());
        }
        if (options.getPlaceholderId() == 0 && mImageProvider != null && mImageProvider.getLoadingResId() > 0) {
            options.placeholder(mImageProvider.getLoadingResId());
        }
        request.apply(options)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(target);
    }

    @Override
    public void loadBitmap(Context context, String url, SimpleTarget<Bitmap> target) {
        getRequestManager(context)
                .asBitmap()
                .load(mImageProvider == null || mImageProvider.configHeader() == null ? url : new GlideUrl(url, mImageProvider.configHeader()))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(target);
    }

    @Override
    public File down(Context context, String url, RequestOptions options) {
        try {
            return getRequestManager(context).asFile()
                    .load(mImageProvider == null || mImageProvider.configHeader() == null ? url : new GlideUrl(url, mImageProvider.configHeader()))
                    .apply(options)
                    .submit().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void loadResource(ImageView target, int resId, RequestOptions options) {
        load(getRequestManager(target.getContext()).load(resId), target, options);
    }

    @Override
    public void loadAssets(ImageView target, String assetName, RequestOptions options) {
        load(getRequestManager(target.getContext()).load("file:///android_asset/" + assetName), target, options);
    }

    @Override
    public void loadFile(ImageView target, File file, RequestOptions options) {
        load(getRequestManager(target.getContext()).load(file), target, options);
    }

    @Override
    public void clearMemoryCache(Context context) {
        Glide.get(context).clearMemory();
    }

    @Override
    public void clearDiskCache(Context context) {
        Glide.get(context).clearDiskCache();
    }

    @Override
    public void resume(Context context) {
        getRequestManager(context).resumeRequests();
    }

    @Override
    public void pause(Context context) {
        getRequestManager(context).pauseRequests();
    }

    private GlideRequests getRequestManager(Context context) {
        if (context instanceof Activity) {
            return GlideApp.with((Activity) context);
        }
        return GlideApp.with(context);
    }

    private void load(GlideRequest<Drawable> request, ImageView target, RequestOptions options) {
        if (options == null) {
            options = new RequestOptions().placeholder(mImageProvider == null ? RES_NONE : mImageProvider.getLoadingResId())
                    .error(mImageProvider == null ? RES_NONE : mImageProvider.getLoadErrorResId());
        }
        if (options.getErrorId() == 0 && mImageProvider != null && mImageProvider.getLoadErrorResId() > 0) {
            options.error(mImageProvider.getLoadErrorResId());
        }
        if (options.getPlaceholderId() == 0 && mImageProvider != null && mImageProvider.getLoadingResId() > 0) {
            options.placeholder(mImageProvider.getLoadingResId());
        }
        request.apply(options)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(target);
    }
}
