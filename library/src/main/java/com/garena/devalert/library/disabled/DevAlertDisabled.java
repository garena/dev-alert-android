package com.garena.devalert.library.disabled;

import android.app.Application;
import android.content.Context;

import com.garena.devalert.library.DevAlertManager;

/**
 * @author amulya
 * @since 03 Feb 2017, 1:06 PM.
 */

public class DevAlertDisabled implements DevAlertManager {

    @Override
    public void registerLifecycleCallback(Application application) {
        // do nothing
    }

    @Override
    public void addWarning(String tag, String message, Throwable ex) {
        // do nothing
    }

    @Override
    public void addError(String tag, String message, Throwable ex) {
        // do nothing
    }

    @Override
    public void clearData(Context context) {

    }
}
