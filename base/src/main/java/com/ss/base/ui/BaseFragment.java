package com.ss.base.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ss.base.dialog.CustomProgressDialog;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<P extends IPresent> extends RxFragment implements IView<P> {
    private P p;
    private Unbinder unbinder;
    private Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        if (getLayoutId() > 0) {
            view = inflater.inflate(getLayoutId(), container, false);
            bindUI(view);
        }
        return view == null ? super.onCreateView(inflater, container, savedInstanceState) : view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }

//    public void requestPermission(@NonNull String permission, int requestCode) {
//        int permissionCheck = ContextCompat.checkSelfPermission(getContext(), permission);
//        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
//                //请求过此权限但用户拒绝了请求
//                LogUtil.e("请求过此权限但用户拒绝了请求");
//            } else
//                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
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
//        permissionsResult(requestCode, (grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED));
//    }

    public BaseActivity getBaseActivity() {
        if (getActivity() instanceof BaseActivity)
            return (BaseActivity) getActivity();
        return null;
    }

    public P getP() {
        if (p == null) {
            p = newP();
            if (p != null)
                p.attachV(this);
        }
        return p;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (useEventBus() && !EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        if (useEventBus() && EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
        if (unbinder != null) unbinder.unbind();
        if (p != null)
            p.detachV();
        p = null;
        super.onDestroy();
    }

    @Override
    public void bindUI(View rootView) {
        unbinder = ButterKnife.bind(this, rootView);
    }

    @Override
    public int getOptionsMenuId() {
        return 0;
    }

    @Override
    public boolean useEventBus() {
        return false;
    }

    public void showProgress() {
        dialog = CustomProgressDialog.showBasicProgress(getBaseActivity());
    }

    public void hideProgress() {
        CustomProgressDialog.hideBasicProgress(dialog);
    }
}
