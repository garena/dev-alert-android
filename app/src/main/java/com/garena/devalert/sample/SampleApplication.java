package com.garena.devalert.sample;

import android.app.Application;

import com.garena.devalert.library.DevAlert;
import com.garena.devalert.library.DevAlertConfig;

/**
 * @author amulya
 * @since 20 Feb 2017, 11:23 AM.
 */

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DevAlertConfig config = new DevAlertConfig.Builder()
                .showErrors(true)
                .showWarnings(true)
                .build();
        DevAlert.init(this, BuildConfig.DEBUG, config);
    }
}
