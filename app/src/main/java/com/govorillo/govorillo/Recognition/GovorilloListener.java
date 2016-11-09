package com.govorillo.govorillo.Recognition;

import android.os.AsyncTask;
import android.util.Log;

import com.govorillo.govorillo.HTTPHelpers.AsyncPost;
import com.govorillo.govorillo.Singleton;

import ru.yandex.speechkit.Error;
import ru.yandex.speechkit.Recognition;
import ru.yandex.speechkit.Recognizer;
import ru.yandex.speechkit.RecognizerListener;

public class GovorilloListener implements RecognizerListener {
    private String LOG_TAG = "govorillo_debug_listener";

    @Override
    public void onRecordingBegin(Recognizer recognizer) {
        Log.d(LOG_TAG, "onRecordingBegin");
    }

    @Override
    public void onSpeechDetected(Recognizer recognizer) {
        Log.d(LOG_TAG, "onSpeechDetected");
    }

    @Override
    public void onSpeechEnds(Recognizer recognizer) {
        Log.d(LOG_TAG, "onSpeechEnds");
    }

    @Override
    public void onRecordingDone(Recognizer recognizer) {
        Log.d(LOG_TAG, "onRecordingDone");
    }

    @Override
    public void onSoundDataRecorded(Recognizer recognizer, byte[] bytes) {
        Log.d(LOG_TAG, "onSoundDataRecorded");
    }

    @Override
    public void onPowerUpdated(Recognizer recognizer, float v) {
        Log.d(LOG_TAG, "onPowerUpdated");
    }

    @Override
    public void onPartialResults(Recognizer recognizer, Recognition recognition, boolean b) {
        Log.d(LOG_TAG, "PartialRESULT");
        Log.d(LOG_TAG, recognition.getBestResultText());
        AsyncTask<String, Void, String> execute = new AsyncPost(this).execute(Singleton.getInstance().getUrl()+ "listen", recognition.getBestResultText());
    }

    @Override
    public void onRecognitionDone(Recognizer recognizer, Recognition recognition) {
        Log.d(LOG_TAG, "RESULT");
        Log.d(LOG_TAG, recognition.getBestResultText());
        AsyncTask<String, Void, String> execute = new AsyncPost(this).execute(Singleton.getInstance().getUrl()+ "listen", recognition.getBestResultText());
    }

    @Override
    public void onError(Recognizer recognizer, Error error) {
        Log.d(LOG_TAG, "onError");
        Log.d(LOG_TAG, error.getString());
    }
}