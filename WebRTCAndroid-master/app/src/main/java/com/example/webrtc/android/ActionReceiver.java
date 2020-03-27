package com.example.webrtc.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class ActionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectActivity.getInstance().connectRoom();
    }
}