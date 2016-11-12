package com.govorillo.govorillo.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.govorillo.govorillo.Service.HTTPPullService;
import com.govorillo.govorillo.R;
import com.govorillo.govorillo.Singleton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent httpPullIntent = new Intent(this, HTTPPullService.class);
        final EditText editAdress = (EditText) findViewById(R.id.editAdress);
        final EditText editSec = (EditText) findViewById(R.id.editSec);
        final EditText editToken = (EditText) findViewById(R.id.editToken);
        final Switch onOff = (Switch) findViewById(R.id.onOff);
        final Switch onOffBlockingSpeak = (Switch) findViewById(R.id.blockingSwitcher);

        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        final String deviceId = telephonyManager.getDeviceId();

        onOffBlockingSpeak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Singleton.getInstance().setSpeakBlocking(isChecked);
            }
        });

        onOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Singleton.getInstance().setDeviceId(deviceId);
                    Singleton.getInstance().setSpeakBlocking(Singleton.getInstance().getSpeakBlocking());
                    Singleton.getInstance().setSpeechKitToken(String.valueOf(editToken.getText()));
                    Singleton.getInstance().setUrl(String.valueOf(editAdress.getText()));
                    Singleton.getInstance().setSeconds(Integer.valueOf(String.valueOf(editSec.getText())));
                    startService(httpPullIntent);
                } else {
                    stopService(httpPullIntent);
                }

            }
        });
    }
}