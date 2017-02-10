package com.garena.devalert.library;

import android.app.Application;
import android.content.Context;

/**
 * @author amulya
 * @since 03 Feb 2017, 1:01 PM.
 */

public interface DevAlertManager {
    void registerLifecycleCallback(Application application);
    void addWarning(String tag, String message, Throwable ex);
    void addError(String tag, String message, Throwable ex);
    void clearData(Context context);
}
