package com.ss.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;

import com.ss.base.R;

import java.util.WeakHashMap;

public class CustomProgressDialog {
    private static WeakHashMap<Context, Dialog> map;

    /**
     * basic progressDialog
     */
    public static Dialog showBasicProgress(Context context) {
        if (map == null) {
            map = new WeakHashMap<>();
        }
        if (context == null)
            return null;
        Dialog progressDialog = map.get(context);
        if (progressDialog == null) {
            progressDialog = new Dialog(context, R.style.base_CustomDialog);
            progressDialog.setContentView(R.layout.base_progress_dialog);
            WindowManager.LayoutParams params = progressDialog.getWindow().getAttributes();
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            progressDialog.getWindow().setAttributes(params);
//            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            map.put(context, progressDialog);
        }
        if (!progressDialog.isShowing())
            progressDialog.show();
        return progressDialog;
    }

    public static void hideBasicProgress(Dialog progressDialog) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
