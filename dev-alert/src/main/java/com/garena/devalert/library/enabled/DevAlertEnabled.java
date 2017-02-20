package com.garena.devalert.library.enabled;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.garena.devalert.library.DevAlert;
import com.garena.devalert.library.DevAlertConfig;
import com.garena.devalert.library.DevAlertManager;

import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import java.util.HashSet;

/**
 * @author amulya
 * @since 03 Feb 2017, 1:05 PM.
 */

public class DevAlertEnabled implements DevAlertManager, Application.ActivityLifecycleCallbacks {

    private final DevAlertConfig mConfig;
    private final HashSet<String> mIgnoredTags;

    private ArrayDeque<DevAlertData> mErrorQueue;
    private ArrayDeque<DevAlertData> mWarningQueue;
    private WeakReference<Activity> mCurrentActivity;

    public DevAlertEnabled(Context context, DevAlertConfig config) {
        mConfig = config;
        mErrorQueue = new ArrayDeque<>();
        mWarningQueue = new ArrayDeque<>();
        mIgnoredTags = new HashSet<>(mConfig.getIgnoredTags());
        mCurrentActivity = new WeakReference<>(null);
        loadIgnoreList(context);
    }

    @Override
    public void registerLifecycleCallback(Application application) {
        application.registerActivityLifecycleCallbacks(this);
    }

    @Override
    public synchronized void addWarning(String tag, String message, Throwable ex) {
        if (mConfig.isShowWarnings()) {
            if (!mIgnoredTags.contains(tag)) {
                mWarningQueue.add(new DevAlertData(DevAlert.WARNING, tag, message, ex));
                notify(mCurrentActivity.get());
            }
        }
    }

    @Override
    public synchronized void addError(String tag, String message, Throwable ex) {
        if (mConfig.isShowErrors()) {
            if (!mIgnoredTags.contains(tag)) {
                mErrorQueue.add(new DevAlertData(DevAlert.ERROR, tag, message, ex));
                notify(mCurrentActivity.get());
            }
        }
    }

    @Override
    public synchronized void clearData(Context context) {
        mIgnoredTags.clear();
        mIgnoredTags.addAll(mConfig.getIgnoredTags());
        context.getSharedPreferences(mConfig.getSharedPrefName(), Context.MODE_PRIVATE)
                .edit().putStringSet("ignored_tags", new HashSet<String>())
                .apply();
    }

    private void notify(final Activity activity) {
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    notifyUI(activity);
                }
            });
        }
    }

    private void notifyUI(Activity activity) {
        FrameLayout root = (FrameLayout) (activity).findViewById(android.R.id.content);
        if (root != null) {
            DevAlertData data = null;
            synchronized (DevAlertEnabled.this) {
                if (!mErrorQueue.isEmpty()) {
                    data = mErrorQueue.pop();
                } else if (!mWarningQueue.isEmpty()) {
                    data = mWarningQueue.pop();
                }
            }
            if (data != null) {
                DevAlertHintView hintView = new DevAlertHintView(activity, data, this);
                hintView.attach(root);
            }
        }
    }

    private void saveIgnoreList(Context context) {
        context.getSharedPreferences(mConfig.getSharedPrefName(), Context.MODE_PRIVATE)
                .edit().putStringSet("ignored_tags", mIgnoredTags)
                .apply();

    }

    private void loadIgnoreList(Context context) {
        mIgnoredTags.addAll(context.getSharedPreferences(mConfig.getSharedPrefName(), Context.MODE_PRIVATE)
                .getStringSet("ignored_tags", new HashSet<String>()));
    }

    void addToIgnoreList(Context context, String tag) {
        mIgnoredTags.add(tag);
        saveIgnoreList(context);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        mCurrentActivity = new WeakReference<>(activity);
        notify(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        mCurrentActivity = new WeakReference<>(null);
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
