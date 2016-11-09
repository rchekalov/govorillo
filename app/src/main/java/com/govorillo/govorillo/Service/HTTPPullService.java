package com.govorillo.govorillo.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.govorillo.govorillo.HTTPHelpers.SyncGet;
import com.govorillo.govorillo.Recognition.GovorilloRecognitionService;
import com.govorillo.govorillo.Singleton;

import java.util.Locale;


public class HTTPPullService extends Service {
    private String LOG_TAG = "govorillo_debug";
    private boolean isRunning  = false;
    private int secondsOfSleep;
    private String url = "";
    TextToSpeech textToSpeech;
    public boolean isRecording = false;
    public boolean isPlaying = false;

    public HTTPPullService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        url = Singleton.getInstance().getUrl();
        int editSec = Singleton.getInstance().getSeconds();
        secondsOfSleep = 1000 * editSec;

        final Intent RecognitionServiceIntent = new Intent(this, GovorilloRecognitionService.class);
        final Intent MediaPlayerIntent = new Intent(this, MediaPlayerService.class);

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    Locale locale = new Locale("ru");
                    int result = textToSpeech.setLanguage(locale);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Извините, этот язык не поддерживается");
                    }

                } else {
                    Log.e("TTS", "Ошибка!");
                }
            }
        });

        Log.d(LOG_TAG, "Start Service");

        if (isRunning == false) {
            isRunning = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (isRunning) {
                        try {
                            Thread.sleep(secondsOfSleep);
                        } catch (Exception e) {
                            Log.d(LOG_TAG, e.toString());
                        }
                        if (isRunning) {
                            try {
                                String response = SyncGet.sendGet(url);
                                runCommand(response, RecognitionServiceIntent, MediaPlayerIntent);
                                Log.d(LOG_TAG, response);

                            } catch (Exception e) {
                                Log.d(LOG_TAG, e.toString());
                            }
                            Log.d(LOG_TAG, "get http");
                        }
                    }
                    stopSelf();
                }
            }).start();
        } else {
            Log.d(LOG_TAG, "already started");
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public void runCommand(String response, Intent GovorilloListenerService, Intent MediaPlayerIntent) {
        String resUpper = response.toUpperCase();
        if (resUpper.contains("SAY")) {
            resUpper = resUpper.replace("SAY ", "");
            textToSpeech.speak(resUpper, TextToSpeech.QUEUE_FLUSH, null);
            Log.d(LOG_TAG, "I say");
        } else if (resUpper.contains("LISTEN")) {
            if (isRecording == false) {
                Log.d(LOG_TAG, "I listen");
                startService(GovorilloListenerService);
                isRecording = true;
            }
        } else if (resUpper.contains("STOP")) {
            if (isRecording) {
                Log.d(LOG_TAG, "I stop");
                stopService(GovorilloListenerService);
            }
            if (isPlaying) {
                Log.d(LOG_TAG, "I stop");
                stopService(MediaPlayerIntent);
            }
        } else if (resUpper.contains("PLAY")) {
            String audio_url = response.replace("PLAY ", "");
            if (isPlaying == false) {
                Log.d(LOG_TAG, "I playing");
                Singleton.getInstance().setPlayUrl(audio_url);
                startService(MediaPlayerIntent);
                isPlaying = true;
            }
            Log.d(LOG_TAG, "I play");
        } else if (resUpper.contains("NOP")) {
            Log.d(LOG_TAG, "I do nothing");
        } else {
            Log.d(LOG_TAG, "error:(");
        }
    }

    @Override
    public void onDestroy() {
        Log.d(LOG_TAG, "End Service");
        isRunning = false;
    }

}