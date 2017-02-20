package com.garena.devalert.library.enabled;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

/**
 * @author amulya
 * @since 07 Sep 2016, 7:54 PM.
 */
class DevAlertDialog extends Dialog {

    DevAlertDialog(Context context, DevAlertData data, DevAlertEnabled manager) {
        super(context, android.R.style.Theme_Black);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        DevAlertFullView view = new DevAlertFullView(context, data, manager);
        view.setDialog(this);
        setContentView(view);
    }
}
