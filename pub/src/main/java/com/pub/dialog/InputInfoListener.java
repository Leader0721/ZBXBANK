package com.pub.dialog;

import android.app.Dialog;

/**
 * Created by Leader on 2017/5/22.
 *
 */
public interface InputInfoListener {

    public void onDialogOk(Dialog dlg, Object param);

    public void onDialogCancel(Dialog dlg, Object param);
}
