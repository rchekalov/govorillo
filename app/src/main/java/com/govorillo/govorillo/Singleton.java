package com.govorillo.govorillo;


public class Singleton {
    private static Singleton mInstance = null;

    private String url;
    private String mp3Url;
    private int seconds;
    private String deviceId;
    private String SpeechKitToken;
    private boolean isSpeakBlocking = true;

    private Singleton(){
        url = "";
    }

    public static Singleton getInstance(){
        if(mInstance == null)
        {
            mInstance = new Singleton();
        }
        return mInstance;
    }

    public String getUrl(){
        return this.url;
    }

    public void setUrl(String value){
        url = value;
    }

    public int getSeconds(){
        return this.seconds;
    }

    public void setSeconds(int value){
        seconds = value;
    }

    public String getPlayUrl() {
        return mp3Url;
    }

    public void setPlayUrl(String value) {
        mp3Url = value;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String value) {
        deviceId = value;
    }

    public String getSpeechKitToken() {
        return SpeechKitToken;
    }

    public void setSpeechKitToken(String value) {
        SpeechKitToken = value;
    }

    public boolean getSpeakBlocking() {
        return isSpeakBlocking;
    }

    public void setSpeakBlocking(boolean value) {
        isSpeakBlocking = value;
    }
}