package com.garena.devalert.library.enabled;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.garena.devalert.library.DevAlert;
import com.garena.devalert.library.R;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author amulya
 * @since 08 Sep 2016, 12:54 PM.
 */
class DevAlertFullView extends FrameLayout {

    private DevAlertData mData;
    private DevAlertEnabled mManager;
    private ListView mTraceListView;
    private TextView mDismissBtn;
    private TextView mHideForeverBtn;
    private TextView mCopyBtn;
    private Dialog mDialog;
    private TextView mText;
    private ClipboardManager mClipboard;

    public DevAlertFullView(Context context, DevAlertData data, DevAlertEnabled manager) {
        super(context);
        mData = data;
        mManager = manager;
        initView(context);
    }

    public void setDialog(Dialog dialog) {
        mDialog = dialog;
    }

    private void initView(final Context context) {
        inflate(context, R.layout.devalert_support_layout, this);
        mClipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

        mText = (TextView) findViewById(R.id.title);
        String message = mData.getMessage();
        Throwable throwable = mData.getThrowable();

        if (TextUtils.isEmpty(message)) {
            mText.setText(throwable.getMessage());
        } else if (TextUtils.isEmpty(throwable.getMessage())) {
            mText.setText(message);
        } else {
            mText.setText(
                    message + "\n\n" + throwable.getMessage()
            );
        }

        // dismiss
        mDismissBtn = (TextView) findViewById(R.id.dismiss_btn);
        mDismissBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        // hide forever
        mHideForeverBtn = (TextView) findViewById(R.id.hide_btn);
        mHideForeverBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mManager.addToIgnoreList(getContext(), mData.getTag());
                mDialog.dismiss();
            }
        });

        // copy
        mCopyBtn = (TextView) findViewById(R.id.copy_btn);
        mCopyBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipData clipData = ClipData.newPlainText("Stack Trace", traceToString(mData));
                mClipboard.setPrimaryClip(clipData);
                Toast.makeText(getContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        mTraceListView = (ListView) findViewById(R.id.trace_list);
        TraceAdapter adapter = new TraceAdapter();
        adapter.setData(Arrays.asList(mData.getThrowable().getStackTrace()));
        mTraceListView.setAdapter(adapter);

        // color
        switch (mData.getType()) {
            case DevAlert.ERROR:
                setBackgroundColor(Color.parseColor("#E80000"));
                break;
            case DevAlert.WARNING:
                setBackgroundColor(Color.parseColor("#E6B300"));
                break;
        }
    }

    private static String traceToString(DevAlertData data) {
        String exported = data.getMessage() + "\n";
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        data.getThrowable().printStackTrace(pw);
        exported = exported + sw.toString();
        return exported;
    }

    public static class TraceAdapter extends BaseAdapter {

        private List<StackTraceElement> mDataSource = new ArrayList<>();

        public void setData(List<StackTraceElement> data) {
            mDataSource = data;
        }

        @Override
        public int getCount() {
            return mDataSource.size();
        }

        @Override
        public StackTraceElement getItem(int position) {
            return mDataSource.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflate(parent.getContext(), R.layout.devalert_trace_list_item, null);
            }
            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView subTitle = (TextView) convertView.findViewById(R.id.sub_title);

            // bind
            StackTraceElement trace = getItem(position);
            title.setText(trace.getMethodName());
            subTitle.setText(trace.getFileName() + ":" + trace.getLineNumber());

            return convertView;
        }
    }
}
