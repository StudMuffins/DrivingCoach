package com.studmuffins.application;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Switch;

import java.util.Locale;

/**
 * Created by Pooria on 15/05/15.
 */

public class TTSManager {
    private TextToSpeech tts = null;
    private boolean isLoaded = false;


    public void init(Context context) {
        try {
            tts = new TextToSpeech(context, onInitListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TextToSpeech.OnInitListener onInitListener = new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
            if (status == TextToSpeech.SUCCESS) {
                int result = tts.setLanguage(Locale.UK);
                tts.setSpeechRate(1 / 2);
                isLoaded = true;

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("error", "This Language is not supported");
                }
            } else {
                Log.e("error", "Initialization Failed!");
            }
        }
    };

    public void shutDown() {
        tts.shutdown();
    }

    public void addQueue(String speech) {
        if (isLoaded) {
            tts.speak(speech, TextToSpeech.QUEUE_ADD, null);
        } else {
            Log.e("error", "TTS Not Initialized");
        }
    }

    public void initQueue(String speech) {
        if (isLoaded) {
            tts.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
        } else {
            Log.e("error", "TTS Not Initialized");
        }
    }



}