package com.garena.devalert.library.enabled;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.garena.devalert.library.DevAlert;
import com.garena.devalert.library.R;

/**
 * @author amulya
 * @since 08 Sep 2016, 11:27 AM.
 */
class DevAlertHintView extends FrameLayout {

    private final DevAlertData mData;
    private final DevAlertEnabled mManager;
    private TextView mTextView;
    private TextView mDismissBtn;
    private TextView mSeeMore;

    public DevAlertHintView(Context context, DevAlertData data, DevAlertEnabled manager) {
        super(context);
        mData = data;
        mManager = manager;
        initView(context);
    }

    private void initView(final Context context) {
        inflate(context, R.layout.devalert_support_view, this);
        mTextView = (TextView) findViewById(R.id.title);

        // dismiss
        mDismissBtn = (TextView) findViewById(R.id.dismiss_btn);
        mDismissBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ViewGroup) getParent()).removeView(DevAlertHintView.this);
            }
        });

        // see more
        mSeeMore = (TextView) findViewById(R.id.see_btn);
        mSeeMore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DevAlertDialog dialog = new DevAlertDialog(context, mData, mManager);
                dialog.show();
            }
        });

        // color
        switch (mData.getType()) {
            case DevAlert.ERROR:
                setBackgroundColor(Color.parseColor("#E80000"));
                break;
            case DevAlert.WARNING:
                setBackgroundColor(Color.parseColor("#E6B300"));
                break;
        }

        String message = mData.getMessage();
        Throwable throwable = mData.getThrowable();

        if (TextUtils.isEmpty(message)) {
            mTextView.setText(throwable.getMessage());
        } else if (TextUtils.isEmpty(throwable.getMessage())) {
            mTextView.setText(message);
        } else {
            mTextView.setText(
                    message + "\n\n" + throwable.getMessage()
            );
        }
    }

    public void attach(FrameLayout root) {
        if (root != null && getParent() == null) {
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.BOTTOM;
            params.setMargins(4, 4, 4, 4);
            root.addView(this, params);
            Animation rotationAni = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_from_bottom);
            startAnimation(rotationAni);
        }
    }
}
