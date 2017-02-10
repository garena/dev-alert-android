package com.garena.devalert.sample;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.garena.devalert.library.DevAlert;

/**
 * @author amulya
 * @since 10 Feb 2017, 3:30 PM.
 */

public class TaskWithSleep extends AsyncTask<Void, Void, Void> {

    private static final String TAG = "TaskWithSleep";
    private static final int THRESHOLD = 2000; // 2 sec

    private Context context;

    public TaskWithSleep(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(context, "Task Started", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        long timeStart = System.currentTimeMillis();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long timeElapsed = System.currentTimeMillis() - timeStart;
        if (timeElapsed > THRESHOLD) {
            DevAlert.reportWarning(TAG, "Loading data from DB took more than " + THRESHOLD + "ms. This might be a potential issue.");
        }
        return null;
    }
}
