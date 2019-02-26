package com.ss.base.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.ss.base.dialog.CustomProgressDialog;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<P extends IPresent> extends RxAppCompatActivity implements IView<P> {
    protected BaseActivity context;
    private P p;
    private Unbinder unbinder;
    private Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
            bindUI(null);
        }
//        该方法是【友盟+】Push后台进行日活统计及多维度推送的必调用方法
//        PushAgent.getInstance(context).onAppStart();
        initData(savedInstanceState);
    }

    @Override
    public void bindUI(View rootView) {
        unbinder = ButterKnife.bind(this);
    }

    protected P getP() {
        if (p == null) {
            p = newP();
            if (p != null) {
                p.attachV(this);
            }
        }
        return p;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (useEventBus() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        if (useEventBus() && EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
        if (unbinder != null) unbinder.unbind();
        if (p != null)
            p.detachV();
        p = null;
        super.onDestroy();
    }

    @Override
    public boolean useEventBus() {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getOptionsMenuId() > 0) {
            getMenuInflater().inflate(getOptionsMenuId(), menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

//    public void requestPermission(@NonNull String permission, int requestCode) {
//        int permissionCheck = ContextCompat.checkSelfPermission(this, permission);
//        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
//                //请求过此权限但用户拒绝了请求
//                LogUtil.e("请求过此权限但用户拒绝了请求");
//            } else
//                ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
//        } else {
//            permissionsResult(requestCode, true);
//        }
//    }
//
//    protected void permissionsResult(int requestCode, boolean granted) {
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//            permissionsResult(requestCode, true);
//        } else {
//            permissionsResult(requestCode, false);
//        }
//    }

    @Override
    public int getOptionsMenuId() {
        return 0;
    }

    public void showProgress() {
        dialog = CustomProgressDialog.showBasicProgress(this);
    }

    public void hideProgress() {
        CustomProgressDialog.hideBasicProgress(dialog);
    }

    public void onResume() {
        super.onResume();
//        if (!hasFragment()) {
//            MobclickAgent.onPageStart(getClass().getName()); //手动统计页面
//        }
//        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
//        if (!hasFragment()) {
//            MobclickAgent.onPageEnd(getClass().getName()); //手动统计页面
//        }
//        MobclickAgent.onPause(this);
        super.onPause();
    }

    public boolean hasFragment() {
        return false;
    }
}
