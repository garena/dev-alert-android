package com.garena.devalert.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.garena.devalert.library.DevAlert;

public class SampleActivity extends AppCompatActivity {

    private static final String TAG_WARNING = "warning";
    private static final String TAG_ERROR = "error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
    }

    public void showError(View view) {
        DevAlert.reportError(TAG_ERROR, "There was a problem. This method should not be called.");
    }

    public void showWarning(View view) {
        DevAlert.reportWarning(TAG_WARNING, "This is a code warning. There was a problem parsing the data.");
    }

    public void runTaskWithException(View view) {
        new TaskWithException(this).execute();
    }

    public void runTaskWithSleep(View view) {
        new TaskWithSleep(this).execute();
    }
}
