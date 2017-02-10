package com.garena.devalert.library;

import android.app.Application;
import android.content.Context;

import com.garena.devalert.library.disabled.DevAlertDisabled;
import com.garena.devalert.library.enabled.DevAlertEnabled;

/**
 * @author amulya
 * @since 03 Feb 2017, 12:26 PM.
 */

public class DevAlert {

    public static final int ERROR = 1;
    public static final int WARNING = 2;

    private static DevAlertManager mInstance;

    public static synchronized void init(Application context, boolean isEnabled) {
        if (mInstance == null) {
            mInstance = isEnabled ? new DevAlertEnabled(context, new DevAlertConfig.Builder().build()) : new DevAlertDisabled();
            mInstance.registerLifecycleCallback(context);
        }
    }

    public static synchronized void init(Application context, boolean isEnabled, DevAlertConfig config) {
        if (mInstance == null) {
            mInstance = isEnabled ? new DevAlertEnabled(context, config) : new DevAlertDisabled();
            mInstance.registerLifecycleCallback(context);
        }
    }

    public static synchronized void clearData(Context context) {
        if (mInstance != null) {
            mInstance.clearData(context);
        }
    }

    public static synchronized void reportWarning(String tag, String message, Throwable ex) {
        if (mInstance != null) {
            mInstance.addWarning(tag, message, ex);
        }
    }

    public static synchronized void reportWarning(String tag, String message) {
        if (mInstance != null) {
            mInstance.addWarning(tag, message, new RuntimeException());
        }
    }

    public static synchronized void reportWarning(String tag, Throwable ex) {
        if (mInstance != null) {
            mInstance.addWarning(tag, "", ex);
        }
    }

    public static synchronized void reportError(String tag, String message, Throwable ex) {
        if (mInstance != null) {
            mInstance.addError(tag, message, ex);
        }
    }

    public static synchronized void reportError(String tag, String message) {
        if (mInstance != null) {
            mInstance.addError(tag, message, new RuntimeException());
        }
    }

    public static synchronized void reportError(String tag, Throwable ex) {
        if (mInstance != null) {
            mInstance.addError(tag, "", ex);
        }
    }
}
