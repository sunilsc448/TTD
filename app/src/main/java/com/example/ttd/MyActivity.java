package com.example.ttd;

import android.app.Activity;

public class MyActivity extends Activity {

    private int mCount;

    @Override
    protected void onStart() {
        super.onStart();
        mCount++;
    }

    public int getCount() {
        return mCount;
    }
}
