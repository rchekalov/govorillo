package com.govorillo.govorillo.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        final Switch onOff = (Switch) findViewById(R.id.onOff);

        onOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
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
