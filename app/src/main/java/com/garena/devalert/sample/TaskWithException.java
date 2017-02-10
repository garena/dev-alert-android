package com.garena.devalert.sample;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.garena.devalert.library.DevAlert;

/**
 * @author amulya
 * @since 10 Feb 2017, 3:30 PM.
 */

public class TaskWithException extends AsyncTask<Void, Void, Void> {

    private static final String TAG = "TaskWithException";

    private Context context;

    public TaskWithException(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(context, "Task Started", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            Thread.sleep(1000);
            Object a = null;
            a.toString();
        } catch (Exception e) {
            DevAlert.reportError(TAG, e);
        }
        return null;
    }
}
