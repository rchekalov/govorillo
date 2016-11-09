package com.govorillo.govorillo.Recognition;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.govorillo.govorillo.Recognition.GovorilloListener;

import ru.yandex.speechkit.Recognizer;
import ru.yandex.speechkit.SpeechKit;

public class GovorilloRecognitionService extends Service {
    private String LOG_TAG = "govorillo_debug";
    private static final String API_KEY = "";
    Recognizer recognizer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        SpeechKit.getInstance().configure(getApplicationContext(), API_KEY);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        recognizer = Recognizer.create(Recognizer.Language.RUSSIAN, Recognizer.Model.NOTES, new GovorilloListener(), true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return START_NOT_STICKY;
        }
        recognizer.start();
        recognizer.setVADEnabled(false);
        return START_NOT_STICKY;
    }
    @Override
    public void onDestroy() {
        Log.d(LOG_TAG, "End listening");
        recognizer.finishRecording();
    }
}

